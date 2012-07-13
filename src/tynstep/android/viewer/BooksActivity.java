package tynstep.android.viewer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BooksActivity extends Activity implements OnClickListener{
	
	private static String dirpath = OptionsActivity.path;
	
	private String booktype = OptionsActivity.bookType;
	private String bookXMLFile = "step_"+booktype+".xml"; 
	
	protected static String bookname = null;
	protected static String font; 
	protected static String baseURL = "http://dl.dropbox.com/u/23178416/tyndale_step/";
	protected static Document doc;
	
	ArrayList<HashMap<String, String>> booknames = new ArrayList<HashMap<String, String>>();
	
	List<String> fullbooknames = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
	ListView listView;
	ImageView coverView;
	TextView textView;
	TextView textView2;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.books );
		
		listView = (ListView) findViewById(R.id.listview_books);
		
		View btnUpdate = findViewById(R.id.button_update_books);
		btnUpdate.setOnClickListener(this);
		View btnFindBook = findViewById(R.id.button_find_book);
		btnFindBook.setOnClickListener(this);
		
		// get book list
		getBookList();

	}
	
	private void downloadBookList() {
		
		// Check network availability
		if (DownloadUtils.isNetworkAvailable(BooksActivity.this)) {
			
			// Download list of books from Tyndale server (step_??.xml where ?? is lexicons or commentaries)
			String url = baseURL+bookXMLFile;
			DownloadUtils.downloadFromUrl(BooksActivity.this, url, bookXMLFile, dirpath);

			// Set book list

			getBookList();
		}
		else{
			Toast.makeText(BooksActivity.this, R.string.network_unavailable, Toast.LENGTH_LONG).show();
		}
			
			
//			// Parse xml document
//			doc = null;
//			doc = XMLfunctions.XMLfromString(booklist);
//			NodeList nodes = doc.getElementsByTagName("book");
//			
//			for (int i = 0; i < nodes.getLength(); i++) {
//				Element e = (Element)nodes.item(i);
//				webbooks.add(XMLfunctions.getValue(e, "fullname"));
//		    }
//			
//						
//			adapter = new ArrayAdapter<String>(BooksActivity.this,
//					android.R.layout.simple_list_item_1, android.R.id.text1, webbooks);
//
//			// Assign adapter to ListView
//			listView.setAdapter(adapter);
//			listView.setOnItemClickListener(new OnItemClickListener() {
//				
//				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//					bookname = parent.getItemAtPosition(position).toString();
//					findIndex();
//				}
//			});
//
//		}
//		else{
//			Toast.makeText(BooksActivity.this, R.string.network_unavailable, Toast.LENGTH_LONG).show();
//		}
		
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button_update_books: {
			updateBookList();
			}
		break;
		case R.id.button_find_book: {
			if ( bookname == null ) {
				//No book chosen
				Toast.makeText(BooksActivity.this, R.string.no_book, Toast.LENGTH_LONG).show();
			}
			else{
				// start viewer activity at first page

				startActivity(new Intent(this, ViewerActivity.class));
			}
			}
		break;
		}
	}
	
	private void updateBookList(){
		new AlertDialog.Builder(this)
		.setTitle(R.string.update_books)
		.setMessage(R.string.update_books_message)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				downloadBookList();
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {}
		})
		.show();
    }
	
	private void downloadFiles(){
		new AlertDialog.Builder(this)
		.setTitle(R.string.download_index)
		.setMessage(R.string.download_index_message)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// download index and cover files and first page
				String bookpath = dirpath+bookname+"/"; 
				
				String indexfname = bookname+".xml";
				String coverfname = bookname+".jpg";
				String pagefname = Utils.pageFileName(0, bookname);
				
				String indexURL = baseURL+"indexes/"+indexfname;
				String coverURL = baseURL+"cover_images/"+coverfname;
				String pageURL = baseURL+bookname+"/"+pagefname;
				
				DownloadUtils.downloadFromUrl(BooksActivity.this, indexURL, indexfname, bookpath);
				DownloadUtils.downloadFromUrl(BooksActivity.this, coverURL, coverfname, bookpath);
				DownloadUtils.downloadFromUrl(BooksActivity.this, pageURL, pagefname, bookpath);
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {}
		})
		.show();
    }
	
	private void findIndex(){
		File fulldirpath = new File(dirpath+bookname);
		File fullindexpath = new File(dirpath+bookname+"/"+bookname+".xml");
		if (!(fulldirpath.exists()) || !(fullindexpath.exists())) {
			// make new directory and download index file and cover image
			fulldirpath.mkdir();
			downloadFiles();
		}
		else{
			// Load thumbnail cover
			coverView = (ImageView) findViewById(R.id.imageView1);
			String coverPath = dirpath+bookname+"/"+bookname+".jpg";
			File coverFile = new File(coverPath);
			if (coverFile.exists()) {
				Uri coverURI = Uri.parse("file://" + coverFile);
				coverView.setImageURI(coverURI);
			}
			else {
				coverView.setImageResource(R.drawable.ic_menu_book);
			}
			
			// Load xml blurb
			String xmlPath = dirpath+bookname+"/"+bookname+".xml";
	    	File indexFile = new File(xmlPath);
	    	if (indexFile.exists()) {	
	    		String xml = XMLfunctions.getXML(xmlPath);
	    		doc = null;
	    		doc = XMLfunctions.XMLfromString(xml);
	    		
	    		// Get front matter
	    		Element efront = (Element)doc.getElementsByTagName("frontmatter").item(0);
	    		
	    		// Set font
	    		font = XMLfunctions.getValue(efront, "font");
	    		
	    		// Set Toast with front matter	    		
	    		String title = XMLfunctions.getValue(efront, "title");
	    		String author = XMLfunctions.getValue(efront, "author");
	    		String year = XMLfunctions.getValue(efront, "year");
	    		String blurb = XMLfunctions.getValue(efront, "blurb");
	    		
	    		String bookDetails = title+"\n"+ author + "\n" + year + "\n";
	    		    		
	    		textView = (TextView) findViewById(R.id.textView1);
	    		textView.setText(bookDetails);
	    		
	    		textView2 = (TextView) findViewById(R.id.textView2);
	    		textView2.setText(blurb);
	    	}
	    	else {
	        	Toast.makeText(BooksActivity.this, R.string.index_missing, Toast.LENGTH_LONG).show();
	        }
			
//			Toast.makeText(getApplicationContext(), R.string.index_available, Toast.LENGTH_LONG).show();
		}
	}
	
	public void getBookList() {
		// Get current books
		File booklistfile = new File(dirpath + bookXMLFile);
		if (booklistfile.exists()) {

			// Clear arrays
			booknames.clear();
			fullbooknames.clear();
			
			// Download list of books from Tyndale server (step_??.xml where ?? is lexicons or commentaries)
			String booklist = XMLfunctions.getXML(dirpath + bookXMLFile);
			
			// Parse xml document
			
			Document bookdoc = XMLfunctions.XMLfromString(booklist);
			NodeList nodes = bookdoc.getElementsByTagName("book");
						
			for (int i = 0; i < nodes.getLength(); i++) {
				Element e = (Element)nodes.item(i);
				HashMap<String,String> map = new HashMap<String, String>();
						
				map.put("fullname", XMLfunctions.getValue(e, "fullname"));
				map.put("shortname", XMLfunctions.getValue(e, "shortname"));
				
				booknames.add(map);
		    }
			
			// Get full book name list and sort into alphabetical order
			fullbooknames = Utils.getInfoList(booknames, "fullname");
			Collections.sort(fullbooknames);
			
			// make adapter for listview
			if (!(fullbooknames.size() == 0)) {
				adapter = new ArrayAdapter<String>(BooksActivity.this,
						android.R.layout.simple_list_item_1, android.R.id.text1, fullbooknames);

				// Assign adapter to ListView
				listView.setAdapter(adapter);
				
				listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
						String fullbookname = parent.getItemAtPosition(position).toString();
						
						for (int i = 0; i < booknames.toArray().length; i++) {
							String entry = booknames.get(i).get("fullname");
							if (entry.equals(fullbookname)) {
								bookname = booknames.get(i).get("shortname");
								break;
							}
						}
						findIndex();
					}
				});
				bookdoc = null;
			}
			else {
				Toast.makeText(BooksActivity.this, R.string.no_books, Toast.LENGTH_LONG).show();
			}

			
		}
		else {
			Toast.makeText(BooksActivity.this, R.string.no_books, Toast.LENGTH_LONG).show();
		}
		
	}
	
}