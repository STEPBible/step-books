package tynstep.android.viewer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;


public class DownloadUtils {
	
//	private static final String TAG = "DOWNLOAD";
	
	/**
     * Downloads the picture from the URL to the SD card
     * @param imageURL: the URL to download it from (absolute web URL)
     * @param fileName: the name of the file you want to save the picture to
     */
    public static void downloadFromUrl(Context context, String imageURL, String fileName, String path) {
        try {
        		// Set up new file
        		File file = new File(path,fileName);

                // Connect to the URL
                URL myImageURL = new URL(imageURL);
                
                long startTime = System.currentTimeMillis();
                
                HttpURLConnection connection = (HttpURLConnection)myImageURL.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);

                ByteArrayBuffer baf = new ByteArrayBuffer(5000);
                int current = 0;
                while ((current = bis.read()) != -1) {
                	baf.append((byte) current);
                }
                
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(baf.toByteArray());
                fos.flush();
                fos.close();
                
//                // Get the bitmap
//                Bitmap myBitmap = BitmapFactory.decodeStream(is);

//                // Save the bitmap to the file
//                OutputStream fOut = null;
//                File file = new File(path, fileName);
//                fOut = new FileOutputStream(file);
//
//                myBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
//                fOut.flush();
//                fOut.close();
            } catch (IOException e) {
//            	Log.d("error","I am here!");
            	Toast.makeText(context, "Error: "+e, Toast.LENGTH_LONG).show();
            }
    }
	
	public static boolean isNetworkAvailable(Context context) {
//		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    // if no network is available networkInfo will be null
	    // otherwise check if we are connected
	    if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
    
//	public static String getXML(String url){
//
//		String line = null;
//		
//		try {
//			DefaultHttpClient httpClient = new DefaultHttpClient();
//	
//			HttpPost httpPost = new HttpPost(url);
//			
//			HttpResponse httpResponse = httpClient.execute(httpPost);
//			HttpEntity httpEntity = httpResponse.getEntity();
//			line = EntityUtils.toString(httpEntity);
//		} 
//		catch (UnsupportedEncodingException e) {
//			line = "Error: Can't connect to server";
//		} 
//		catch (MalformedURLException e) {
//			line = "Error: Can't connect to server";
//		} 
//		catch (IOException e) {
//			line = "Error: Can't connect to server";
//		}
//		return line;
//	}
	
}