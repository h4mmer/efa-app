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
import android.widget.Button;
import android.widget.EditText;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class RouteActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.route);

		Button btnSubmit = (Button) findViewById(R.id.btnRouteSubmit);
		btnSubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				EditText etWaters = (EditText) findViewById(R.id.etWaters);
				String waters = etWaters.getText().toString();
				EditText etStart = (EditText) findViewById(R.id.etStart);
				String start = etStart.getText().toString();
				EditText etMid = (EditText) findViewById(R.id.etMid);
				String mid = etMid.getText().toString();
				EditText etDestination = (EditText) findViewById(R.id.etDestination);
				String destination = etDestination.getText().toString();
				EditText etDistance = (EditText) findViewById(R.id.etDistance);
				int distance = Integer
						.parseInt(etDistance.getText().toString());

				if (waters.length() == 0 || start.length() == 0
						|| mid.length() == 0 || destination.length() == 0
						|| distance < 0) { // ALARM
					AlertDialog.Builder alert = new AlertDialog.Builder(
							RouteActivity.this);
					alert.setTitle("Alert!")
							.setMessage("Bitte alle Felder ausfüllen!")
							.setNeutralButton("OK", null).show();
				} else {
					Offline offline = new Offline(RouteActivity.this);
					try {
						offline.newRoute(waters, start, mid, destination,
								distance);
						AlertDialog.Builder alert = new AlertDialog.Builder(
								RouteActivity.this);
						alert.setTitle("OKAY")
								.setMessage("Speichern Erfolgreich")
								.setNeutralButton("OK", null).show();
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						AlertDialog.Builder alert = new AlertDialog.Builder(
								RouteActivity.this);
						alert.setTitle("Alarm!").setMessage("File ERROR")
								.setNeutralButton("OK", null).show();
					}
					
				}

			}
		});

	}
}
