package tynstep.android.viewer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Help extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		View btOK = findViewById(R.id.button_help_ok);
		btOK.setOnClickListener(this);
//		View wikipedia = findViewById(R.id.button_lookup_wikipedia);
//		wikipedia.setOnClickListener(this);
//		View wikipediaWebView = findViewById(R.id.button_lookup_wikipedia_in_web_view);
//		wikipediaWebView.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.button_help_ok:
			finishFromChild(this);
			break;
//		case R.id.button_lookup_wikipedia:
//			if (hasNetworkConnection()){
//				LaunchBrowser("http://en.wikipedia.org/wiki/Tictactoe");
//			}else{
//				noNetworkConnectionNotify();
//			}
//			break;
//		case R.id.button_lookup_wikipedia_in_web_view:
//			if (hasNetworkConnection()){
//				LaunchWebView("http://en.wikipedia.org/wiki/Tictactoe");
//			}else{
//				noNetworkConnectionNotify();
//			}
//			break;
		}
	}	
//	private boolean hasNetworkConnection(){
//
//		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//		boolean isConnected = true;
//		boolean isWifiAvailable = networkInfo.isAvailable();
//		boolean isWifiConnected = networkInfo.isConnected();
//		networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//		boolean isMobileAvailable = networkInfo.isAvailable();
//		boolean isMobileConnnected = networkInfo.isConnected();
//	    isConnected = (isMobileAvailable&&isMobileConnnected)||(isWifiAvailable&&isWifiConnected);
//		return(isConnected);
//	}
	
//	private void LaunchBrowser(String URL){
//		Uri theUri = Uri.parse(URL);
//		Intent LaunchBrowserIntent = new Intent(Intent.ACTION_VIEW, theUri);
//		startActivity(LaunchBrowserIntent);
//	}
	
//	private void LaunchWebView(String URL){
//		Intent launchWebViewIntent = new Intent(this, HelpWithWebView.class);
//		launchWebViewIntent.putExtra("URL", URL);
//
//		startActivity(launchWebViewIntent);
//	}

//	private void noNetworkConnectionNotify(){
//		new AlertDialog.Builder(this)
//		.setTitle("Warning")
//		.setMessage("Cannot complete action: No network connectivity")
//		.setIcon(android.R.drawable.ic_dialog_alert)
//		.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {}
//		})
//		.show();
//    }
	
}

