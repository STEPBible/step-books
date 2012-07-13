package tynstep.android.viewer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class OptionsActivity extends Activity implements OnClickListener {

	protected static String path;
	protected static String bookType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);

		View btnOpenLexLib = findViewById(R.id.button_open_lex_library);
		btnOpenLexLib.setOnClickListener(this);
		View btnOpenCommLib = findViewById(R.id.button_open_comm_library);
		btnOpenCommLib.setOnClickListener(this);
		View btnAbout = findViewById(R.id.button_about);
		btnAbout.setOnClickListener(this);
		View btnExit = findViewById(R.id.button_exit);
		btnExit.setOnClickListener(this);
		
		// Check storage options and set path
		StorageOptions.determineStorageOptions();
		String[] labels = StorageOptions.labels;
		String[] paths = StorageOptions.paths; 
		
		// Check if tyndale step directory already exists
		List<String> steppaths = new ArrayList<String>();
		List<String> steplabels = new ArrayList<String>();
		for (int i = 0; i < paths.length; i++) {
			String steppath = paths[i]+"/STEP";
			String steplabel = labels[i];
			File stepdir = new File(steppath);
			if ( stepdir.exists() ) {
				steppaths.add(steppath);
				steplabels.add(steplabel);
			}
		}
				
		if (steppaths.size()==0) {
			// Select volume for STEP
			selectVol(labels, paths, false);

		}
		else if (steppaths.size() > 1) {
			// More than one STEP folders exist - select path
			CharSequence[] labelscharseq = steplabels.toArray(new CharSequence[steplabels.size()]);
			String[] pathsarray = steppaths.toArray(new String[steppaths.size()]);
			selectDir(labelscharseq, pathsarray, true);
		}
		else {
			// Only one directory exists. Set it to path variable
			path = steppaths.get(0).concat("/");
		}
	}

	private void selectVol(final CharSequence[] volumes, final String[] paths, boolean existing){
		new AlertDialog.Builder(this)
		.setCancelable(false)
		.setTitle(R.string.select_vol_message)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setItems(volumes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// set path
				path = paths[which].concat("/STEP/");
				// Create new directory
				if ( path != null ) {
					new File(path).mkdir();
				}
			}
		})
		.show();
	}
	
	private void selectDir(final CharSequence[] volumes, final String[] paths, boolean existing){
		new AlertDialog.Builder(this)
		.setCancelable(false)
		.setTitle(R.string.select_existing_vol_message)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setItems(volumes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// set path
				path = paths[which].concat("/");
			}
		})
		.show();
	}
	
	private void quitApplication(){
			new AlertDialog.Builder(this)
			.setTitle(R.string.exit)
			.setMessage(R.string.quit_message)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					System.exit(0);
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			})
			.show();
	    }
    public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	    	case R.id.menu_exit:
	    		quitApplication();
	    		return true;
	    }
    	return false;
    }
	
	
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.button_open_lex_library:
			bookType = "lexicons";
			startActivity(new Intent(this, BooksActivity.class));
			break;
		case R.id.button_open_comm_library:
			bookType = "commentaries";
			startActivity(new Intent(this, BooksActivity.class));
//			Toast.makeText(OptionsActivity.this, R.string.coming_soon, Toast.LENGTH_LONG).show();
			break;
		case R.id.button_about:
			startActivity(new Intent(this, AboutActivity.class));
			break;
			
		case R.id.button_exit:
			{
				quitApplication();
			}
			break;
		}
		
	}
	
}