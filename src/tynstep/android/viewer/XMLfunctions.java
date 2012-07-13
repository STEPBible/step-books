package tynstep.android.viewer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

public class XMLfunctions {

	private static final String TAG = "XML";
	
	public final static Document XMLfromString(String xml){
		
		Document doc = null;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			InputSource is = new InputSource();
			StringReader sr = new StringReader(xml);
//			Log.d("wacky12",""+ sr.toString());
	        is.setCharacterStream(new StringReader(xml));
//	        is.setEncoding("UTF-8");
//	        Log.d("wacky13",""+ is.toString());
	        doc = db.parse(is);
//	        NodeList nl = doc.getElementsByTagName("info2");
//	        int tc = nl.getLength();
//	        for (int i = 0; i < nl.getLength(); i++) {
//	        	Element e= (Element)nl.item(i);
//	        	Log.d(TAG,""+e.getTextContent());
//	        }
//	        Log.d(TAG,""+tc);
	        
		} catch (ParserConfigurationException e) {
			System.out.println("XML parse error: " + e.getMessage());
			return null;
		} catch (SAXException e) {
			System.out.println("Wrong XML file structure: " + e.getMessage());
            return null;
		} catch (IOException e) {
			System.out.println("I/O exeption: " + e.getMessage());
			return null;
		}

        return doc;
        
	}
	
	/** Returns element value
	  * @param elem element (it is XML tag)
	  * @return Element value otherwise empty String
	  */
	 public final static String getElementValue( Node elem ) {
//	     Node kid;
	     if( elem != null){
//	    	 Log.d("wacky16",""+elem.getNodeType());
	    	 return elem.getTextContent();
//	         if (elem.hasChildNodes()){
//	        	 for( kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
//	                 if( kid.getNodeType() == Node.TEXT_NODE  ){
//	                	 return kid.getNodeValue();
//	                 }
//	             }
//	         }
	     }
	     return "";
	 }
		
	 private static String readFileAsString(String filePath) throws java.io.IOException{
		    byte[] buffer = new byte[(int) new File(filePath).length()];
		    BufferedInputStream f = null;
		    try {
		        f = new BufferedInputStream(new FileInputStream(filePath));
		        f.read(buffer);
		    } finally {
		        if (f != null) try { f.close(); } catch (IOException ignored) { }
		    }
		    return new String(buffer);
		}
	 
	 
	 public static String getXML(String path){	 
			String line = null;
			
			try {
				File mFile = new File(path);
				String filePath = mFile.toString();
				line = readFileAsString(filePath);
				
//				FileInputStream fstream = new FileInputStream(mFile);
//				DataInputStream in = new DataInputStream(fstream);
//				BufferedReader br = new BufferedReader(new InputStreamReader(in));
//				line = br.readLine();
			}
			catch (Exception e){
				System.err.println("Error: " + e.getMessage());
			}
			
			//line = mFile.toString();
			
//			try {
//				DefaultHttpClient httpClient = new DefaultHttpClient();
//				HttpPost httpPost = new HttpPost("http://dl.dropbox.com/u/23178416/test.xml");
//
//				HttpResponse httpResponse = httpClient.execute(httpPost);
//				HttpEntity httpEntity = httpResponse.getEntity();
//				line = EntityUtils.toString(httpEntity);
//			} 
//			catch (UnsupportedEncodingException e) {
//				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
//			} 
//			catch (MalformedURLException e) {
//				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
//			} 
//			catch (IOException e) {
//				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
//			}
//	        Log.d(TAG,""+ line);
			return line;

	}
	 
//	public static int numResults(Document doc){		
//		Node results = doc.getDocumentElement();
//		int res = -1;
//		
//		try{
//			res = Integer.valueOf(results.getAttributes().getNamedItem("count").getNodeValue());
//		}
//		catch(Exception e ){
//			res = -1;
//		}
//			
//		return res;
//	}
	

	public static String getValue(Element item, String str) {
		NodeList n = item.getElementsByTagName(str);
//		Log.d("wacky14",n.item(0).getTextContent());
		return XMLfunctions.getElementValue(n.item(0));
	}
}
