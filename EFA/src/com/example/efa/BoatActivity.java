package com.example.efa;

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
import android.widget.EditText;
import android.widget.Spinner;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class BoatActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.boat);

        final String[] boattype = new String[1];
        final String[] defaultCrew = new String[1];
        
        final Spinner sBoatType = (Spinner) findViewById(R.id.sBoatType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(BoatActivity.this, R.array.BoatTyps, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sBoatType.setAdapter(adapter);
        sBoatType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                
                boattype[0] = item.toString();
                if(boattype[0]=="Einer"){
                	//TODO
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        final Spinner sdefaultCrew = (Spinner) findViewById(R.id.sdefaultCrew);
        Offline offline = new Offline(BoatActivity.this);
        String[] spinnerContent2 =  offline.getPersons();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spinnerContent2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sdefaultCrew.setAdapter(adapter2);
        sdefaultCrew.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                defaultCrew[0] = item.toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
		
        Button btnSubmit = (Button) findViewById(R.id.btnBoatSubmit);
		btnSubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				EditText etBoatName = (EditText) findViewById(R.id.etBoatName);
				String boatname = etBoatName.getText().toString();
				if (boatname.length() == 0) { // ALARM
					AlertDialog.Builder alert = new AlertDialog.Builder(
							BoatActivity.this);
					alert.setTitle("Alert!").setMessage("Bitte Botsname eingeben!")
							.setNeutralButton("OK", null).show();
				} else {
					Offline offline = new Offline(BoatActivity.this);
					try {
						offline.newBoat(boatname,boattype[0], defaultCrew[0]);
						AlertDialog.Builder alert = new AlertDialog.Builder(
								BoatActivity.this);
						alert.setTitle("OKAY")
								.setMessage("Speichern Erfolgreich")
								.setNeutralButton("OK", null).show();
						etBoatName.setText(null);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						e.getStackTrace().toString();
						AlertDialog.Builder alert = new AlertDialog.Builder(
								BoatActivity.this);
						alert.setTitle("Alarm!").setMessage("File ERROR")
								.setNeutralButton("OK", null).show();
					}
					
				}
				;

			}
		});
        
    }
    
}
