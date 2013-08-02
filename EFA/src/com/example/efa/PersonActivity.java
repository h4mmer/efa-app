package com.example.efa;

import java.io.File;
import java.io.IOException;

import com.example.efa.util.SystemUiHider;
import com.example.efa.logic.*;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
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
public class PersonActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person);
        

		Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				

				EditText etFirstName = (EditText) findViewById(R.id.etFirstName);
				String firstname = etFirstName.getText().toString();
				EditText etLastName = (EditText) findViewById(R.id.etLastName);
				String lastname = etLastName.getText().toString();				
				if (firstname.length() == 0 || lastname.length() == 0) { // ALARM
					AlertDialog.Builder alert=new AlertDialog.Builder(PersonActivity.this);
					alert.setTitle("Alert!").setMessage("We are under attack!").setNeutralButton("OK", null).show();
				
				} else {
					
				


					Offline offline = new Offline(PersonActivity.this);
					try {
						offline.newPerson(lastname, firstname);
						AlertDialog.Builder alert=new AlertDialog.Builder(PersonActivity.this);
						alert.setTitle("OKAY").setMessage("Speichern Erfolgreich").setNeutralButton("OK", null).show();
						etFirstName.setText(null);
						etLastName.setText(null);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						AlertDialog.Builder alert=new AlertDialog.Builder(PersonActivity.this);
						alert.setTitle("Alarm!").setMessage("File ERROR").setNeutralButton("OK", null).show();
					}
					
				}
				;

			}
		});

	}
}
