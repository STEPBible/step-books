package tynstep.android.viewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ChooseBookActivity extends Activity implements OnClickListener{

	public static String book;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.choosebook);
	    
	    // Book Spinner
	    Spinner spinner = (Spinner) findViewById(R.id.letter_spinner);
	    
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.books, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	    spinner.setAdapter(adapter);
	    spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
	    
	    // Start viewer button
	    View btnStartViewer = findViewById(R.id.button_start_viewer);
		btnStartViewer.setOnClickListener(this);
	}
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//	    	Toast.makeText(parent.getContext(), "The letter is " +
//	        parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();

	    	book = parent.getItemAtPosition(pos).toString();
	    	
	    	
//	    	if ( book != null ){
//	    		startActivity(new Intent(ChooseBookActivity.this, ViewerActivity.class));
//	    	}
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
	
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.button_start_viewer:
			startActivity(new Intent(this, ViewerActivity.class));
			break;
		}
	}

}
