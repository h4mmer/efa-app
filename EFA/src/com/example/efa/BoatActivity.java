package com.example.efa;

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

        final Spinner sBoatType = (Spinner) findViewById(R.id.sBoatType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(BoatActivity.this, R.array.BoatTyps, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sBoatType.setAdapter(adapter);
        final String[] boattype = new String[1];
        sBoatType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                
                boattype[0] = item.toString();
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
					offline offline = new offline();
					if (offline.newBoat(boatname,boattype[0])) {
						AlertDialog.Builder alert = new AlertDialog.Builder(
								BoatActivity.this);
						alert.setTitle("OKAY")
								.setMessage("Speichern Erfolgreich")
								.setNeutralButton("OK", null).show();

					} else {
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
