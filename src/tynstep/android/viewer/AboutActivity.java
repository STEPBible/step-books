package tynstep.android.viewer;

import android.app.Activity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class AboutActivity extends Activity {

	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		TextView tv = (TextView) findViewById(R.id.tv_about);
		
		Spannable WordtoSpan = new SpannableString(getString(R.string.about_message));
		StyleSpan bss = new StyleSpan(android.graphics.Typeface.ITALIC);
		WordtoSpan.setSpan(bss, 0, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.setText(WordtoSpan);
	}
}