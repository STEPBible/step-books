package tynstep.android.viewer;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Settings extends PreferenceActivity {
	
	private final static String 	OPT_NAME 		= "name";
	private final static String 	OPT_NAME_DEF 	= "Player";
	private final static String 	OPT_PLAY_FIRST 		= "human_starts";
	private final static boolean 	OPT_PLAY_FIRST_DEF 	= true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.settings);
	}
	public static String getName(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
		.getString(OPT_NAME, OPT_NAME_DEF);
	}

	public static boolean doesHumanPlayFirst(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
		.getBoolean(OPT_PLAY_FIRST, OPT_PLAY_FIRST_DEF);
	}
	
}
