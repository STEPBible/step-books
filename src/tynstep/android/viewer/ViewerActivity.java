package tynstep.android.viewer;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;

import tynstep.android.viewer.imagezoom.ImageViewTouch;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ViewerActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "ViewerActivity";
	
	// Set path, book and font
	private static String path = OptionsActivity.path;
	private String book = BooksActivity.bookname;
	private String fontstyle = BooksActivity.font;
	private int page = 0; // always start at first page
	
	// Get xml index document
	private Document doc = BooksActivity.doc;
	
	// Instantiate Gallery set orientation (in degrees) of images - leave as 0
	private int orientation = 0;
	private MyGallery g;
	
	// Image File and Uri arrays
    private Uri[] mUrls;
    private String[] mFiles = null;
    
	// Instantiate spinners, array adapters and lists
	Spinner spinner1;
	Spinner spinner2;
	private List<String> info1list = new ArrayList<String>();
	private List<String> info2list = new ArrayList<String>();
	private ArrayAdapter<String> adapter1;
	private MyArrayAdapter adapter2;
	
	// Instantiate list selections
	private String info1;
	private String info2;
	
	// XML data
	private ArrayList<HashMap<String,String>> indexdata = new ArrayList<HashMap<String,String>>(); 
	private ArrayList<HashMap<String,String>> refinedentries = new ArrayList<HashMap<String,String>>();
	
	// Instantiate page number(s)
	public List<Integer> pnumbers = new ArrayList<Integer>();
	
	// Instantiate typeface
	public Typeface tf; 

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );

		setContentView( R.layout.viewer );
		
		
		/** 
		 * Query image database to get images and return starting page 
		 */
	    int tindex = getTargetPage();
		
	    /** 
	     * Set Gallery 
	     */ 
	    g = (MyGallery) findViewById(R.id.gallery);
	    g.setAdapter(new ImageAdapter(this));
	    g.setSelection(tindex);
	    
	    /** 
	     * Set up click listener on Gallery 
	     */
//	    g.setOnItemClickListener(new OnItemClickListener() {
//	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//	            
//	        	if ( v != null ) {
//	        		Toast.makeText(ViewerActivity.this, "Page " + (position), Toast.LENGTH_SHORT).show();
//	        		selectImage(position);	            
//	        	}
//	        	
//	        }
//	    });
//	    
//	    // We also want to show context menu for longpressed items in the gallery
//	    registerForContextMenu(g);
	    
	    /**
	     * Set up first spinner in sliding drawer
	     */
	    
		indexdata.clear();
	    indexdata = Utils.getindexdata(ViewerActivity.this, doc);
	    doc = null;
	    
		info1list = Utils.getInfoList(indexdata,"info1");
		
		// Remove duplicates from info1list
		Set<String> hs = new LinkedHashSet<String>(info1list);
		info1list.clear();
		info1list.addAll(hs);
		
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		adapter1 = new MyArrayAdapter(ViewerActivity.this, R.layout.my_spinner_textview, info1list);
	    spinner1.setAdapter(adapter1);
	    spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener1());
	    
	    /**
	     * Set typeface
	     */
	    
	    if ( !fontstyle.equals("normal") ) {
			tf = Typeface.createFromAsset(getAssets(),"fonts/"+fontstyle);
		}
		else {
			tf = Typeface.DEFAULT;
		}
	    
	    /**
	     * Set up click listener on sliding drawer button
	     */
	    
		View btnFind = findViewById(R.id.button_Submit);
		btnFind.setOnClickListener(this);

	}
	
	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		private Context mContext;
    
		public ImageAdapter(Context context) {
			mContext = context;
			TypedArray attr = mContext.obtainStyledAttributes(R.styleable.ImageZoomActivity);
			mGalleryItemBackground = attr.getResourceId(R.styleable.ImageZoomActivity_android_galleryItemBackground, 0);
			attr.recycle();
		}

		public int getCount() {
			return mUrls.length;
		}

		public Object getItem(int position) {
			return position;
		}
		
		public long getItemId(int position) {
			return position;
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			
		   	ImageViewTouch imageView; // = (ImageViewTouch)findViewById(R.id.imageViewTouch);

		   	// if it's not recycled, initialize some attributes
		   	if (convertView == null) {
		   		imageView = new ImageViewTouch(mContext);
		   	}
		   	else {
		   		imageView = (ImageViewTouch) convertView;
		   	}

		   	// Obtain the image URI
			Uri uri = mUrls[position];

			Bitmap bitmap;
//			Bitmap bitmap = decodeFile(uri);
			try {
				if ( book.equals("Lane") ){
//					bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
					bitmap = ImageLoader.loadFromUri( mContext, uri.toString(), 2048, 2048 );//1024
				}
				else {
//					bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
					bitmap = ImageLoader.loadFromUri( mContext, uri.toString(), 2048, 2048 );//1024
				}
				imageView.setImageBitmapReset( bitmap, orientation, true ); // for ImageViewTouch
			}
			catch ( IOException e ) {
				Toast.makeText( ViewerActivity.this, e.toString(), Toast.LENGTH_LONG ).show();
				Log.d("error",e.toString());
			}

			EcoGallery.LayoutParams layoutParams = new EcoGallery.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		   	imageView.setLayoutParams(layoutParams);

		   	return imageView;
		}
	}
	
	//decode image and scales it to reduce memory consumption
	private Bitmap decodeFile(Uri uri) {
		try {
			//Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, o);
			
			//The new size we want to scale to
			final int REQUIRED_SIZE=70;
			
			//Find the correct scale value. It should be a power of 2.
			int scale=1;
			while (o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
				scale*=2;
			
			//Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize=scale;
			
			return BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, o2);
		}
		catch (IOException e) {
			Toast.makeText( ViewerActivity.this, e.toString(), Toast.LENGTH_LONG ).show();
			Log.d("error",e.toString());
		}
		return null;
	}
	
	public class MyOnItemSelectedListener1 implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	    	info1 = parent.getItemAtPosition(pos).toString();
	    	
			refinedentries.clear();			
	    	refinedentries = Utils.getEntryList(info1,indexdata);
	    	info2list = Utils.getInfoList(refinedentries,"info2");
	    	
	    	
	    	// Set spinner 2
		    
		    spinner2 = (Spinner) findViewById(R.id.spinner2);
		    
			adapter2 = new MyArrayAdapter(ViewerActivity.this, R.layout.my_spinner_textview, info2list);
		    adapter2.setDropDownViewResource(R.layout.my_spinner_textview);
	
		    spinner2.setAdapter(adapter2);
		    spinner2.setOnItemSelectedListener(new MyOnItemSelectedListener2());
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
	
	public class MyOnItemSelectedListener2 implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	    	info2 = parent.getItemAtPosition(pos).toString();
	    	pnumbers = Utils.getPageNumbers(info2,refinedentries); 
	    	page = pnumbers.get(0);
	    }
	    
	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
	
		
	
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.button_Submit:
			
			if ( Utils.pageExists(path,Utils.pageFileName(page,book),book) ) {
//				startActivity(new Intent(this, ViewerActivity.class));
				// Update Image Uris and set starting page
				int tindex = getTargetPage();
				// generate new ImageAdapter for gallery
			    g.setAdapter(new ImageAdapter(ViewerActivity.this));
			    g.setSelection(tindex);
			}
			else{
				downloadPage();
				if ( Utils.pageExists(path,Utils.pageFileName(page,book),book) ) {
					int tindex = getTargetPage();
					// generate new ImageAdapter for gallery
				    g.setAdapter(new ImageAdapter(ViewerActivity.this));
				    g.setSelection(tindex);
				}
			}
			break;
		}
	}
	
	
	public class MyArrayAdapter extends ArrayAdapter<String>{
		public MyArrayAdapter(Context context, int textViewResourceId, List<String> list) {
			super(context, textViewResourceId, list);
		}
				
		@Override
		public TextView getView(int position, View convertView, ViewGroup parent) {
			TextView v = (TextView) super.getView(position, convertView, parent);
			v.setTypeface(tf);
			
//			if (position == 0) {
//				v.setTextColor(Color.GRAY);
//			}
			return v;
		}

		@Override
		public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
			TextView v = (TextView) super.getView(position, convertView, parent);
			v.setTypeface(tf);

//			if (position == 0) {
//				v.setTextColor(Color.GRAY);
//			}
			return v;
		}
	}

	private void downloadPage(){
		new AlertDialog.Builder(this)
		.setTitle(R.string.download)
		.setMessage(R.string.download_message)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (DownloadUtils.isNetworkAvailable(ViewerActivity.this)) {
					// download all pages with belonging to reference
					for (int i = 0; i < pnumbers.size(); i++) {
						String fname = Utils.pageFileName(pnumbers.get(i),book);
						String url = Utils.pageURL(book,fname);
						String imagepath = (path+book+"/");
						DownloadUtils.downloadFromUrl(ViewerActivity.this,url,fname,imagepath);
					}
				}
				else{
					Toast.makeText(ViewerActivity.this, R.string.network_unavailable, Toast.LENGTH_LONG);
				}
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {}
		})
		.show();
    }

	private int getTargetPage() { 
	    File images = new File(path+book);
	    File[] imagelist = images.listFiles(new ImageFilter());
	    mFiles = new String[imagelist.length];
	    	    
	    for(int i= 0 ; i< imagelist.length; i++){
	        mFiles[i] = imagelist[i].getAbsolutePath();
	    }
	    // sort list into ascending order
	    java.util.Arrays.sort(mFiles);
	    
	    // Get images in correct order	    
	    // target starting image
	    String targetimage = path+book+"/"+Utils.pageFileName(page, book);
	    int targetindex = 0;
	    
	    // find target index
	    for (int i = 0 ; i< imagelist.length; i++){
	    	if ( mFiles[i].equals(targetimage) ) {
	    		targetindex =  i;
	    		break;
	    	}
	    }
	    
	    // Generate URIs
	    mUrls = new Uri[mFiles.length];	    
	    for(int i= 0 ; i< imagelist.length; i++){
	    	mUrls[i] = Uri.parse("file://"+mFiles[i]);
	    }
	
	    mFiles = null;
	    
	    return targetindex;
	}
	
    class ImageFilter implements FilenameFilter {
    	public boolean accept(File dir, String name) {
    		return (name.endsWith(".gif"));
    	}
    }

}