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
public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home);

		Button btnOnTour = (Button) findViewById(R.id.btnOnTour);
		btnOnTour.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(HomeActivity.this,
						OnTourActivity.class);
				startActivity(in);
			}
		});
		Button btnOnline = (Button) findViewById(R.id.btnOnline);
		btnOnline.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(HomeActivity.this,
						OnTourActivity.class);
				startActivity(in);
			}
		});
		Button btnHelp = (Button) findViewById(R.id.btnHelp);
		btnHelp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(HomeActivity.this,
						HelpActivity.class);
				startActivity(in);
			}
		});
		Button btnAbout = (Button) findViewById(R.id.btnAbout);
		btnAbout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(HomeActivity.this,
						AboutActivity.class);
				startActivity(in);
			}
		});
	}

}
