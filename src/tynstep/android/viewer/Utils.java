package tynstep.android.viewer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Utils {

	public static List<String> getBooks(String dirpath) {
		List<String> books = new ArrayList<String>();
		File dir = new File(dirpath);
		if (dir.exists()) {
			books = Arrays.asList(dir.list());
		}
		else {
			books = null;
		}
		return books;
	}
	
	public static String pageFileName(int pagenumber, String bname) {
		String bookname;
		
		// Deal with oddity in Lane file names (to be removed!)
//		if (bname.equals("Lane")) {
//			bookname = bname.concat("-");
//		}
//		else{
//			bookname = bname;
//		}
//		
		bookname = bname;
		int digits = 5;
		String format = String.format("%%0%dd", digits);
		String result = String.format(format, pagenumber);
		String fname = (bookname+'_'+result+".gif");
		return fname;
	}
	
	public static String pageURL(String bookname, String pfilename){
		String url = "http://dl.dropbox.com/u/23178416/tyndale_step/"+bookname+"/"+pfilename;
		return url;
	}
	
	public static boolean pageExists(String path, String pfilename, String bookname){	
		File file = new File(path+bookname+"/"+pfilename);
		if (file.exists()){
			return true;
		}
		return false;
	}
	
	public static List<Integer> getPageNumbers(String info, ArrayList<HashMap<String, String>> data){
		List<Integer> pageNumbers = new ArrayList<Integer>();
		
		for (int i = 0; i < data.toArray().length; i++) {
			String entry = data.get(i).get("info2");
			
			if (entry.equals(info)){
				pageNumbers.add(Integer.parseInt(data.get(i).get("page")));
				Log.d("pages",""+data.get(i).get("page"));
			}
		}
		return pageNumbers;
	}
	
	public static List<String> getInfoList(ArrayList<HashMap<String,String>> data, String tag) {
		List<String> infoList = new ArrayList<String>();
		
		for (int i = 0; i < data.toArray().length; i++) {
			String entry = data.get(i).get(tag);
			infoList.add(entry);
		}
		return infoList;
	}
	
	public static ArrayList<HashMap<String, String>> getEntryList(String info1, ArrayList<HashMap<String, String>> indexdata){
		ArrayList<HashMap<String, String>> refinedentries = new ArrayList<HashMap<String, String>>();
		
		for (int i = 0; i < indexdata.toArray().length; i++) {
			String entry = indexdata.get(i).get("info1");
			
			if (entry.equals(info1)){

				HashMap<String, String> map = indexdata.get(i);
				refinedentries.add(map);
			}
		}
		return refinedentries;
	}

	public static ArrayList<HashMap<String, String>> getFrontMatter(Document doc) {
		
		ArrayList<HashMap<String, String>> frontmatter = new ArrayList<HashMap<String, String>>();

		NodeList nodes = doc.getElementsByTagName("frontmatter");

		for (int i = 0; i < nodes.getLength(); i++) {
			HashMap<String, String> map = new HashMap<String, String>();
				
			Element e = (Element)nodes.item(i);
			
	    	map.put("title", XMLfunctions.getValue(e, "title"));
	    	map.put("author", XMLfunctions.getValue(e, "author"));
	    	map.put("year", XMLfunctions.getValue(e, "year"));
	    	map.put("blurb", XMLfunctions.getValue(e, "blurb"));
	    	map.put("font", XMLfunctions.getValue(e, "font"));
	    	map.put("type", XMLfunctions.getValue(e, "type"));
	    	frontmatter.add(map);
	    }
		return frontmatter;
	}
	
	public static ArrayList<HashMap<String, String>> getindexdata(Context context, Document doc){
		ArrayList<HashMap<String, String>> index = new ArrayList<HashMap<String, String>>();
		
	    NodeList nodes = doc.getElementsByTagName("entry");

	    if(nodes.getLength() <= 0){
	    	Toast.makeText(context, "No index entries were found", Toast.LENGTH_LONG).show();  
//	    	finish();
	    }

		for (int i = 0; i < nodes.getLength(); i++) {
			HashMap<String, String> map = new HashMap<String, String>();
				
			Element e = (Element)nodes.item(i);
			
	    	map.put("info1", XMLfunctions.getValue(e, "info1"));
	    	map.put("info2", XMLfunctions.getValue(e, "info2"));
	    	map.put("page", XMLfunctions.getValue(e, "page"));
	    	index.add(map);
	    }
		return index;
	}


}