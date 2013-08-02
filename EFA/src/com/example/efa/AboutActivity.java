package com.example.efa;

import java.io.File;
import java.io.IOException;

import com.example.efa.logic.LogMe;
import com.example.efa.logic.Offline;
import com.example.efa.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class AboutActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// setContentView(R.layout.about);

		ScrollView scrollView = new ScrollView(this);// macht das die Seite
		final LinearLayout linearLayout = new LinearLayout(this);// linerareslayot
		linearLayout.setOrientation(LinearLayout.VERTICAL); // s.o.
		scrollView.addView(linearLayout);// add zum scrollview

		// stuff you can do
		// textView
		TextView tv = new TextView(this);
		tv.setText("Dynamic layouts ftw!");
		linearLayout.addView(tv);
		// Edit Text
		EditText et = new EditText(this);
		et.setText("weeeeeeeeeee~!");
		linearLayout.addView(et);
		// Button
		Button b = new Button(this);
		b.setText("I don't do anything, but I was added dynamically. :)");
		linearLayout.addView(b);
		// CheckBox
		CheckBox cb = new CheckBox(this);
		cb.setText("I'm dynamic!");
		linearLayout.addView(cb);
		
		//Dropdown
		String[] spinnerRouteContent = {"0","1","2","3"};
		Spinner spinnerRoute = new Spinner(this);
		ArrayAdapter<String> adapterRoute = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spinnerRouteContent);
		adapterRoute
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerRoute.setAdapter(adapterRoute);
		spinnerRoute
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
		linearLayout.addView(spinnerRoute);
		
       
		
		//wichtig
		this.setContentView(scrollView);
	}
}
