package com.example.efa;

import com.example.efa.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class OnTourActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.on_tour);

		Button btnDayTrip = (Button) findViewById(R.id.btnDayTrip);
		btnDayTrip.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(OnTourActivity.this,
						DayTripActivity.class);
				startActivity(in);
			}
		});
		Button btnGroup = (Button) findViewById(R.id.btnGroup);
		btnGroup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(OnTourActivity.this, GroupActivity.class);
				startActivity(in);
			}
		});
		Button btnPerson = (Button) findViewById(R.id.btnPerson);
		btnPerson.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(OnTourActivity.this,
						PersonActivity.class);
				startActivity(in);
			}
		});
		Button btnBoat = (Button) findViewById(R.id.btnBoat);
		btnBoat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(OnTourActivity.this, BoatActivity.class);
				startActivity(in);
			}
		});
		Button btnRoute = (Button) findViewById(R.id.btnRoute);
		btnRoute.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(OnTourActivity.this, RouteActivity.class);
				startActivity(in);
			}
		});

	}

}
