package com.example.efa;

import java.io.File;
import java.io.IOException;

import com.example.efa.logic.LogMe;
import com.example.efa.logic.offline;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ViewSwitcher;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class DayTripActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// setContentView(R.layout.day_trip);
		
		ScrollView scrollView = new ScrollView(this);//macht das die Seite Scrollt
		
		final LinearLayout linearLayout = new LinearLayout(this);// linerareslayot
		linearLayout.setOrientation(LinearLayout.VERTICAL); //s.o.
		scrollView.addView(linearLayout);//add zum scrollview
		
		
		String[] spinnerContent = {"Gruppe","Personen"};
		final Spinner spinner = new Spinner(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, spinnerContent);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                if(item.toString()== "Personen"){
                	linearLayout.removeAllViews();
                	linearLayout.addView(spinner);
                	moreStuff(linearLayout);
                }else if(item.toString()=="Gruppe"){
                	linearLayout.removeAllViews();
                	linearLayout.addView(spinner);
                	general(linearLayout);
                }
                
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        
        linearLayout.addView(spinner);
		
		this.setContentView(scrollView);

	}
	private void moreStuff(LinearLayout linearLayout){
		TextView tv = new TextView(this);
		tv.setText("Dynamic layouts ftw!");
		linearLayout.addView(tv);
		EditText et = new EditText(this);
		et.setText("weeeeeeeeeee~!");
		linearLayout.addView(et);
		Button b = new Button(this);
		b.setText("I don't do anything, but I was added dynamically. :)");
		linearLayout.addView(b);
		for (int i = 0; i < 2; i++) {
			CheckBox cb = new CheckBox(this);
			cb.setText("I'm dynamic!"+i);
			linearLayout.addView(cb);
		}
	}
	private void general(LinearLayout linearLayout){
		//Datum
		TextView date = new TextView(this);
		date.setText("Datum:");
		linearLayout.addView(date);
		DatePicker datePicker = new DatePicker(this);
		linearLayout.addView(datePicker);
		//StartZeit
		TextView startTime = new TextView(this);
		startTime.setText("Start Zeit:");
		linearLayout.addView(startTime);
		TimePicker tpStart = new TimePicker(this);
		tpStart.setIs24HourView(true);
		linearLayout.addView(tpStart);
		//AnkunftsZeit
		TextView arivalTime = new TextView(this);
		arivalTime.setText("Ankunfts Zeit:");
		linearLayout.addView(arivalTime);
		TimePicker tpArival = new TimePicker(this);
		tpArival.setIs24HourView(true);
		linearLayout.addView(tpArival);	
		//Strecke
		TextView route = new TextView(this);
		route.setText("Strecke:");
		linearLayout.addView(route);
		offline offline = new offline(DayTripActivity.this);
        String[] spinnerContent = offline.getRouts();
		Spinner spinner = new Spinner(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, spinnerContent);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                //TODO variable = item.toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        
        //Fahrtart
        TextView type = new TextView(this);
		type.setText("Fahrtart:");
		linearLayout.addView(type);
        String[] spinnerContent2 = {"Wanderfahrt"};
		Spinner spinner2 = new Spinner(this);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, spinnerContent2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                //TODO variable = item.toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //Bemerkung
        TextView comment = new TextView(this);
		comment.setText("Bemerkung:");
		linearLayout.addView(comment);
		EditText etcomment = new EditText(this);
		etcomment.setText("TESTING!"); //TODO
		linearLayout.addView(etcomment);
		
		//Person
		
		//Boot
		
		
		//Button
		Button btnSubmit = new Button(this);
		btnSubmit.setText("Speichern");
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//TODO
				AlertDialog.Builder alert=new AlertDialog.Builder(DayTripActivity.this);
				alert.setTitle("Alarm!").setMessage("TODO").setNeutralButton("OK", null).show();
			}
		});
		linearLayout.addView(btnSubmit);
	}

}
