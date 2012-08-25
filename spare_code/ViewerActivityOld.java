package tynstep.android.viewer;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import tynstep.android.viewer.imagezoom.ImageViewTouch;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.Toast;

public class ViewerActivityOld extends Activity //implements ViewFactory 
{
	private static String path = OptionsActivity.path;
	
	private String book = BooksActivity.bookname;
	private int page = 0; // always start at first page
	
	// set orientation (in degrees) of images - leave as 0
	private int orientation = 0;
	private static final String TAG = "ViewerActivity";
//	private Gallery g;
	private MyGallery g;
	
//	private Cursor c;
//	private int columnIndex;

//	private GestureDetector mGestureDetector;
//	private PinchImageView mPinchImageView;
//	private ImageViewTouch mImageView;
//	private ImageViewTouch imageView;
//	private LayoutInflater inflater;
//	private ImageView imv;
//	private ImageSwitcher imageSwitcher;
	
	// Get starting page number
//	private int page = ChooseRefActivity.pnumber;
//	private String book = ChooseRefActivity.book;
	
    private Uri[] mUrls;
    
    String[] mFiles=null;
    
    class ImageFilter implements FilenameFilter {
    	public boolean accept(File dir, String name) {
    		return (name.endsWith(".gif"));
    	}
    }
    
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );

		setContentView( R.layout.viewer );
		
		/** 
		 * Query image database to get images 
		 */
		
//		if ( book == null) {
//			Toast.makeText(getApplicationContext(), "No book selected", Toast.LENGTH_LONG).show();
//		}
		
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
//	    // Convert to list and reorder
//	    List<String> mFilesList = new ArrayList<String>(Arrays.asList(mFiles));
//	    Collections.rotate(mFilesList, targetindex);
//	    // Convert back to array
//	    mFiles = mFilesList.toArray(new String[0]);
	    
	    // Generate URIs
	    mUrls = new Uri[mFiles.length];	    
	    for(int i= 0 ; i< imagelist.length; i++){
	    	mUrls[i] = Uri.parse("file://"+mFiles[i]);
	    }

	    Log.d("tst",""+mFiles[0].toString());
	    Log.d("tst",""+mFiles[1].toString());
	    Log.d("tst",""+mFiles[2].toString());
	    
	    mFiles = null;
	    
//		String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.ORIENTATION };
////		String SORT_ORDER = "CAST(" + MediaStore.Images.Media.DATA + " AS SIGNED) ASC";
//		String SORT_ORDER = MediaStore.Images.Media.DATA + " ASC";
//		
//		Log.d(TAG, SORT_ORDER);
//		
//		c = managedQuery( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
//						projection, 
//						MediaStore.Images.Media.DATA + " like ? ", 
//						new String[] { "%STEP/" + book + "%" }, 
//						SORT_ORDER );
//		columnIndex = c.getColumnIndexOrThrow( MediaStore.Images.Media._ID );
		
	    /** 
	     * Set Gallery 
	     */ 
//		g = (Gallery) findViewById(R.id.gallery);
	    g = (MyGallery) findViewById(R.id.gallery);

	    g.setAdapter(new ImageAdapter(this));
//	    g.setFadingEdgeLength(0);
//	    g.setSpacing(2);
	    
	    g.setAdapter(new ImageAdapter(this));
	    g.setSelection(targetindex);
	    
//	    imv = (ImageView)findViewById(R.id.imageView2);
	    
//        imageSwitcher = (ImageSwitcher) findViewById(R.id.switcher1);
//        imageSwitcher.setFactory(this);
//        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
//        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
 

	    /** 
	     * Set up click listener on Gallery 
	     */
//	    g.setOnItemClickListener(new OnItemClickListener() {
//	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//	            
//	        	if ( v != null ) {
//	        		Toast.makeText(ViewerActivity.this, "Page " + (position), Toast.LENGTH_SHORT).show();
//	        		selectImage(position);
	            
//	            // Move cursor to current position
//	 		   	c.moveToPosition(position);
//	 		   
//	 		   	// Get the current value for the requested column
//	 		   	int imageID = c.getInt(columnIndex);
//	 		   
//	 		   	// Obtain the image URI
//	 		   	Uri uri = Uri.withAppendedPath( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(imageID) );
//
//	 		   	imv.setImageURI(uri);

//	 		   	imageSwitcher.setImageURI(uri);
	 		   	
	 		   	
//	 		   	String imageUrl = "file:///mnt/sdcard/STEP/Wilson/Wilson01.gif"; // http://example.com/image.jpg
//	            WebView wv = (WebView) findViewById(R.id.yourwebview);
//	            wv.getSettings().setBuiltInZoomControls(true);
//	            wv.loadUrl(imageUrl);
	            
//	 		   	Intent i = new Intent(Intent.ACTION_VIEW);
//	            i.setDataAndType(uri, "image/*");
//	            startActivity(i);
	            
//	            Intent viewImageIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
//	            startActivity(viewImageIntent);
//	        	}
//	        	
//	        }
//	    });
	    
	    // We also want to show context menu for longpressed items in the gallery
//	    registerForContextMenu(g);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.menu_choosebook:
	        	startActivity(new Intent(this, ChooseBookActivity.class));
    			return true;
	        case R.id.menu_chooseref:
	        	startActivity(new Intent(this, ChooseRefActivity.class));
    			return true;
	        case R.id.menu_settings:
	        	return true;
	        case R.id.menu_help:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inviewer, menu);
//		menu.add("Find reference");
//        menu.add("Cancel");
    }
	
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
    		case R.id.menu_choose_book:
    			startActivity(new Intent(this, ChooseBookActivity.class));
    			return true;
        	case R.id.menu_choose_ref:
        		startActivity(new Intent(this, ChooseRefActivity.class));
        		return true;
        	case R.id.menu_help:
        		// Do nothing
        		return true;
        	case R.id.menu_cancel:
        		// Do nothing
        		return true;
        	default:
        		return super.onContextItemSelected(item);
        }
//        Toast.makeText(this, "Longpress: " + (info.position + 1), Toast.LENGTH_SHORT).show();
//        return true;
    }

//    @Override
//	public void onContentChanged()
//	{
//		super.onContentChanged();
//		mImageView = (ImageViewTouch)findViewById( R.id.imageView1 );
//	}
	
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
//			return c.getCount();
			return mUrls.length;
		}

		public Object getItem(int position) {
			return position;
		}
		
		public long getItemId(int position) {
			return position;
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			
//			if (convertView == null) {
//				LayoutInflater inflater = (LayoutInflater) mContext
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				convertView = inflater.inflate(R.layout.viewer, null, true);
//			}
//
//			ImageViewTouch img = (ImageViewTouch) convertView.findViewById(R.id.imagen);
			
//			if (convertView == null) {
//				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				convertView = inflater.inflate(R.layout.viewer, null, true);
//			}
//		   	ImageView imageView = new ImageView(mContext);
//		   	ZoomableImageView imageView = new ZoomableImageView(mContext);
//		   	PinchImageView imageView = new PinchImageView(mContext);
		   	
		   	ImageViewTouch imageView; // = (ImageViewTouch)findViewById(R.id.imageViewTouch);
		   	// if it's not recycled, initialize some attributes
		   	if (convertView == null) {
		   		imageView = new ImageViewTouch(mContext);
		   	}
		   	else {
		   		imageView = (ImageViewTouch) convertView;
		   	}

//		   	ImageView imageView = new ImageView( mContext );

//		   	ImageViewTouchBase imageView = new ImageViewTouchBase( mContext );
			
//		   	AttributeSet attrs = (AttributeSet) mContext.obtainStyledAttributes(R.styleable.ImageZoomActivity);
//		   	ImageViewTouch imageView = new ImageViewTouch( mContext, attrs );
//		   	ImageViewTouch imageView = new ImageViewTouch( mContext, null );
		   	
		   	
//			imageView = (ImageViewTouch)findViewById(R.id.imageView1);
//			ImageViewTouch imageView = (ImageViewTouch) convertView.findViewById(R.id.imageView1);
//			ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);

		   	// Move cursor to current position
			// c.moveToPosition(position);
			
			// Get the current value for the requested column
//			int imageID = c.getInt(columnIndex);
			
			//int orientation = c.getInt( c.getColumnIndex( MediaStore.Images.Media.ORIENTATION ) );
		   
			// Obtain the image URI
//			Uri uri = Uri.withAppendedPath( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(imageID) );
			Uri uri = mUrls[position];

//			BitmapFactory.Options opts = new BitmapFactory.Options();
//			opts.inSampleSize = 4;

			Bitmap bitmap;
//			Bitmap bitmap = decodeFile(uri);
//			imageView.setImageBitmapReset( bitmap, orientation, true );
						
			try {
				if ( book.equals("Lane") ){
//					bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
					bitmap = ImageLoader.loadFromUri( mContext, uri.toString(), 2048, 2048 );//1024
				}
				else {
//					bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
					bitmap = ImageLoader.loadFromUri( mContext, uri.toString(), 2048, 2048 );//1024
				}
//				bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
				imageView.setImageBitmapReset( bitmap, orientation, true ); // for ImageViewTouch
//				imageView.setBitmap(bitmap); // for ZoomableImageView
//				imageView.setImageBitmap(bitmap); // for PinchImageView
				
			}
			catch ( IOException e ) {
				Toast.makeText( ViewerActivityOld.this, e.toString(), Toast.LENGTH_LONG ).show();
				Log.d("error",e.toString());
			}

			EcoGallery.LayoutParams layoutParams = new EcoGallery.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		   	imageView.setLayoutParams(layoutParams);
		   	

			
//			imageView.setImageURI(uri);
//		   	imageView.offsetTopAndBottom(100);
//			imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
//			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//			imageView.setScaleType(ImageView.ScaleType.MATRIX);
//		   	imageView.setScaleType(ImageView.ScaleType.FIT_XY);

// 			imageView.setBackgroundResource(mGalleryItemBackground);

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
			Toast.makeText( ViewerActivityOld.this, e.toString(), Toast.LENGTH_LONG ).show();
			Log.d("error",e.toString());
		}
		return null;
	}
}