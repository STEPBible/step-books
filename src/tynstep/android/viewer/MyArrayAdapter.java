package tynstep.android.viewer;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//public class MyArrayAdapter extends ArrayAdapter<String>{
//	public MyArrayAdapter(Context context, int textViewResourceId, List<String> list) {
//		super(context, textViewResourceId);
//	}
//	
//	public TextView getView(int position, View convertView, ViewGroup parent) {
//		TextView v = (TextView) super.getView(position, convertView, parent);
//		v.setTypeface(myFont);
//		return v;
//	}
//
//	public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
//		TextView v = (TextView) super.getView(position, convertView, parent);
//		v.setTypeface(myFont);
//		return v;
//	}
//	
//	Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/myfont.ttf");
//}

//public class MyArrayAdapter extends ArrayAdapter<String> {
//	int recurso;
//	Typeface tf;
//
//	public MyArrayAdapter(Context _context, int _resource, List<String> _items) {
//		super(_context, _resource, _items);
//		recurso=_resource;
//		tf=Typeface.createFromAsset(_context.getAssets(),"font/newathu.ttf");	
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		//You can use the new tf here.
//		TextView spinner_text=(TextView)findViewById(R.id.text1);
//		spinner_text.setTypeface(tf);
//    }
//}
