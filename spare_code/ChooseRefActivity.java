package tynstep.android.viewer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseRefActivity extends Activity implements OnClickListener{ 

	private static String path = OptionsActivity.path; 
	private static String TAG = "TEST";
	
	// Instantiate spinners, array adapters and lists
	Spinner spinner1;// = (Spinner) findViewById(R.id.spinner1);
	Spinner spinner2;// = (Spinner) findViewById(R.id.spinner2);
	Spinner spinner3;// = (Spinner) findViewById(R.id.spinner3);
	private List<String> books = new ArrayList<String>();
	private List<String> info1list = new ArrayList<String>();
	private List<String> info2list = new ArrayList<String>();
	private ArrayAdapter<String> adapter1;// = new ArrayAdapter<String>(this, R.layout.my_spinner_textview, books);
//	private ArrayAdapter<String> adapter2;// = new ArrayAdapter<String>(this, R.layout.my_spinner_textview, info1list);
	private MyArrayAdapter adapter2;// = new ArrayAdapter<String>(this, R.layout.my_spinner_textview, info1list);
//	private ArrayAdapter<String> adapter3;// = new ArrayAdapter<String>(this, R.layout.my_spinner_textview, info2list);
	private MyArrayAdapter adapter3;// = new ArrayAdapter<String>(this, R.layout.my_spinner_textview, info2list);
	
	// Instantiate list selections
	public static String book;
	private String info1;
	private String info2;
	
	// XML data
	private ArrayList<HashMap<String,String>> indexdata = new ArrayList<HashMap<String,String>>(); 
	private ArrayList<HashMap<String,String>> refinedentries = new ArrayList<HashMap<String,String>>();
	
	// Instantiate page number(s)
	public List<Integer> pnumbers = new ArrayList<Integer>();
	public static int pnumber = 0;
	
	public Typeface tf; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.chooseref);
		
	    /*
	     * Book section
	     */
	    
	    // Get book list
	    books = Utils.getBooks(path);
//	    List<String> books = new ArrayList<String>(getBooks());
	    Collections.sort(books);

	    
	    // Book Spinner
	    spinner1 = (Spinner) findViewById(R.id.spinner1);
	    adapter1 = new ArrayAdapter<String>(this, R.layout.my_spinner_textview, books);
//	    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.my_spinner_textview, books);
	    adapter1.setDropDownViewResource(R.layout.my_spinner_textview);

	    spinner1.setAdapter(adapter1);
	    spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener1());
	    

	    // Start viewer button
	    View btnStartViewer = findViewById(R.id.button_Submit);
		btnStartViewer.setOnClickListener(this);
	}

	public class MyOnItemSelectedListener1 implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//	    	Toast.makeText(parent.getContext(), "The letter is " +
//	        parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();

	    	book = parent.getItemAtPosition(pos).toString();

	    	String xmlPath = path + book+"/"+book+".xml";
	    	
	    	File indexFile = new File(xmlPath);
	    	
	    	
	    	// Get xml index
     
	    	if (indexFile.exists()) {
	    		
	    		String xml = XMLfunctions.getXML(xmlPath);
	    		Document doc = XMLfunctions.XMLfromString(xml);
	    		
//	    		ArrayList<HashMap<String,String>> frontmatter = new ArrayList<HashMap<String,String>>(getFrontMatter(doc));
	    		
	    		// Get front matter
	    		Element efront = (Element)doc.getElementsByTagName("frontmatter").item(0);
	    		
	    		// Set Spinner fonts
	    		String font = XMLfunctions.getValue(efront, "font");
	    		if ( !font.equals("normal") ) {
	    			tf = Typeface.createFromAsset(getAssets(),"fonts/"+font);
	    		}
	    		else {
	    			tf = Typeface.DEFAULT;
	    		}
	    		
	    		// Set Toast with front matter
	    		
	    		String title = XMLfunctions.getValue(efront, "title");
	    		String author = XMLfunctions.getValue(efront, "author");
	    		String year = XMLfunctions.getValue(efront, "year");

//	    		String blurb = XMLfunctions.getValue(efront, "blurb");
	    		
	    		String toasttext = title+"\n"+ author + "\n" + year + "\n";// + blurb;
	    		
	    		LayoutInflater inflater = getLayoutInflater();
	    		View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

	    		// Get cover image
	    		ImageView image = (ImageView) layout.findViewById(R.id.image);
	    		File cover_file = new File(path+book+"/"+book+".jpg");
	    		if (cover_file.exists()) {
	    			Uri cover_uri = Uri.parse("file://"+cover_file.getAbsolutePath());
		    		image.setImageURI(cover_uri);
	    		}
	    		else {
	    			image.setImageResource(R.drawable.ic_menu_book);
	    		}
	    		
	    		TextView text = (TextView) layout.findViewById(R.id.text);
	    		text.setText(toasttext);

	    		Toast toast = new Toast(getApplicationContext());
	    		
//	    		Toast toast = Toast.makeText(parent.getContext(), toasttext, Toast.LENGTH_LONG);
	    		toast.setGravity(Gravity.TOP, 0, 20);
	    		toast.setView(layout);
	    		toast.setDuration(Toast.LENGTH_LONG);
	    		toast.show();
	    		
//	    		fireLongToast();

	    		efront = null;
	    		indexdata = Utils.getindexdata(ChooseRefActivity.this, doc);
	    		doc = null;
	    		info1list = Utils.getInfoList(indexdata,"info1");
	    		
	    		// Remove duplicates from info1list
				Set<String> hs = new LinkedHashSet<String>(info1list);
				info1list.clear();
				info1list.addAll(hs);
				
				spinner2 = (Spinner) findViewById(R.id.spinner2);
			    
//				adapter2 = new ArrayAdapter<String>(ChooseRefActivity.this, R.layout.my_spinner_textview, info1list);

				adapter2 = new MyArrayAdapter(ChooseRefActivity.this, R.layout.my_spinner_textview, info1list);
//				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ChooseRefActivity.this, R.layout.my_spinner_textview, info1list);
//			    adapter2.setDropDownViewResource(R.layout.my_spinner_textview);

			    spinner2.setAdapter(adapter2);
			    spinner2.setOnItemSelectedListener(new MyOnItemSelectedListener2());
			   
	    	}
	    	else {
	        	Toast.makeText(parent.getContext(), "Error: Index file missing", Toast.LENGTH_LONG).show();

	        	info1list.clear();
	        	info2list.clear();

//	        	spinner2.clearFocus();
//	        	spinner2.clearChildFocus(getCurrentFocus());
	        	
//	        	adapter2 = new ArrayAdapter<String>(ChooseRefActivity.this, R.layout.my_spinner_textview, info1list);
//	        	adapter3 = new ArrayAdapter<String>(ChooseRefActivity.this, R.layout.my_spinner_textview, info2list);
//	        	spinner2.setAdapter(adapter2);
//	        	spinner3.setAdapter(adapter3);
	        }
	
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}

	public class MyOnItemSelectedListener2 implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	    	info1 = parent.getItemAtPosition(pos).toString();
	    	refinedentries = Utils.getEntryList(info1,indexdata);
	    	info2list = Utils.getInfoList(refinedentries,"info2");
	    	
//	    	List<String> info2list = new ArrayList<String>(getInfoList(refinedentries,"info2"));
	    	
	    	// Set spinner 3
		    
		    spinner3 = (Spinner) findViewById(R.id.spinner3);
		    
//		    adapter3 = new ArrayAdapter<String>(ChooseRefActivity.this, R.layout.my_spinner_textview, info2list);
			adapter3 = new MyArrayAdapter(ChooseRefActivity.this, R.layout.my_spinner_textview, info2list);
//		    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(ChooseRefActivity.this, R.layout.my_spinner_textview, info2list);
		    adapter3.setDropDownViewResource(R.layout.my_spinner_textview);
	
		    spinner3.setAdapter(adapter3);
		    spinner3.setOnItemSelectedListener(new MyOnItemSelectedListener3());
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
	
	public class MyOnItemSelectedListener3 implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	    	info2 = parent.getItemAtPosition(pos).toString();
	    	pnumbers = Utils.getPageNumbers(info2,refinedentries); 
	    	pnumber = pnumbers.get(0);
	    }
	    
	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
	
		
	
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.button_Submit:
			
			if ( Utils.pageExists(path,Utils.pageFileName(pnumber,book),book) ) {
				startActivity(new Intent(this, ViewerActivity.class));
			}
			else{
				downloadPage();
				if ( Utils.pageExists(path,Utils.pageFileName(pnumber,book),book) ) {
					startActivity(new Intent(this, ViewerActivity.class));
				}
			}
			
			// clear memory
//			adapter1.clear();
//			adapter2.clear();
//			adapter3.clear();
//			spinner1.clearFocus();
//			spinner2.clearFocus();
//			spinner3.clearFocus();
			
			indexdata.clear();
			refinedentries.clear();
//			books.clear();
//			info1list.clear();
//			info2list.clear();
//			
//			info1 = null;
//			info2 = null;
			
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
//			String text = v.getText().toString();
//			int text_id = v.getId();
//			Log.d("textview",""+text_id);
			v.setTypeface(tf);
			
//			String entry = parent.getItemAtPosition(position).toString();
			if (position == 0) {
				v.setTextColor(Color.GRAY);
			}
			return v;
		}

		@Override
		public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
			TextView v = (TextView) super.getView(position, convertView, parent);
			String text = v.getText().toString();
			Log.d("textview2",""+text);
			
			v.setTypeface(tf);
			if (position == 0) {
				v.setTextColor(Color.GRAY);
			}
			return v;
		}
	}
	
//	private void fireLongToast() {
//
//		Thread t = new Thread() {
//			public void run() {
//				int count = 0;
//				try {
//					while (true && count < 10) {
//						toast.show();
//						sleep(1850);
//						count++;
//						// DO SOME LOGIC THAT BREAKS OUT OF THE WHILE LOOP
//					}
//				} 
//				catch (Exception e) {
//					Log.e("LongToast", "", e);
//				}
//			}
//		};
//		t.start();
//	}

	private void downloadPage(){
		new AlertDialog.Builder(this)
		.setTitle(R.string.download)
		.setMessage(R.string.download_message)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (DownloadUtils.isNetworkAvailable(ChooseRefActivity.this)) {
					// download all pages with belonging to reference
					for (int i = 0; i < pnumbers.size(); i++) {
						String fname = Utils.pageFileName(pnumbers.get(i),book);
						String url = Utils.pageURL(book,fname);
						String imagepath = (path+book+"/");
						DownloadUtils.downloadFromUrl(ChooseRefActivity.this,url,fname,imagepath);
					}
				}
				else{
					Log.d("net","i am not here");
					Toast.makeText(ChooseRefActivity.this, R.string.network_unavailable, Toast.LENGTH_LONG);
				}
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {}
		})
		.show();
    }
	
//	public boolean isNetworkAvailable() {
//	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
//	    // if no network is available networkInfo will be null
//	    // otherwise check if we are connected
//	    if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
//	        return true;
//	    }
//	    return false;
//	}
	
//    public void downloadFromUrl(String imageURL, String fileName, String path) {
//        try {
//        		// Set up new file
//        		File file = new File(path,fileName);
//
//                // Connect to the URL
//                URL myImageURL = new URL(imageURL);
//                
//                long startTime = System.currentTimeMillis();
//                
//                HttpURLConnection connection = (HttpURLConnection)myImageURL.openConnection();
//                connection.setDoInput(true);
//                connection.connect();
//                InputStream is = connection.getInputStream();
//                BufferedInputStream bis = new BufferedInputStream(is);
//
//                ByteArrayBuffer baf = new ByteArrayBuffer(5000);
//                int current = 0;
//                while ((current = bis.read()) != -1) {
//                	baf.append((byte) current);
//                }
//                
//                FileOutputStream fos = new FileOutputStream(file);
//                fos.write(baf.toByteArray());
//                fos.flush();
//                fos.close();
//                
////                // Get the bitmap
////                Bitmap myBitmap = BitmapFactory.decodeStream(is);
//
////                // Save the bitmap to the file
////                OutputStream fOut = null;
////                File file = new File(path, fileName);
////                fOut = new FileOutputStream(file);
////
////                myBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
////                fOut.flush();
////                fOut.close();
//            } catch (IOException e) {
//            	Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_LONG);
//            }
//    }
    
}

