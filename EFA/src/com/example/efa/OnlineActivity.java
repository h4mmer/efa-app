package com.example.efa;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;

import com.example.efa.logic.Curl;
import com.example.efa.logic.Offline;
import com.example.efa.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
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
public class OnlineActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.online);
		Button btnOnline = (Button) findViewById(R.id.btnUpload);
		btnOnline.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Offline offline = new Offline(OnlineActivity.this);
				Curl curl = new Curl();
				String[][][] dayTrips = offline.getDayTrips();
				try {
					for (String[][] s : dayTrips) {
						LinkedList<String> crew = new LinkedList<String>();
						for (String c : s[2]) {
							crew.add(c);
						}
						String boatTyp = "K1";//offline.getBoatTyp(s[1][0]);
						if(boatTyp=="Einer")boatTyp = "K1";
						//TODO
						curl.post(s[0][0], s[1][0],boatTyp , crew, s[3][0], s[4][0],s[5][0], s[6][0], s[7][0],Integer.parseInt(s[8][0]), s[9][0], s[10][0]);
					}
					AlertDialog.Builder alert = new AlertDialog.Builder(
							OnlineActivity.this);
					alert.setTitle("OKAY").setMessage("Upload erfolgreich")
							.setNeutralButton("OK", null).show();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					AlertDialog.Builder alert = new AlertDialog.Builder(
							OnlineActivity.this);
					alert.setTitle("OKAY")
							.setMessage("Falsche Kilometer angabe")
							.setNeutralButton("OK", null).show();
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					AlertDialog.Builder alert = new AlertDialog.Builder(
							OnlineActivity.this);
					alert.setTitle("OKAY")
							.setMessage("URL Problem"+e.getMessage())
							.setNeutralButton("OK", null).show();
					e.printStackTrace();
				}catch (IOException e) {
					// TODO Auto-generated catch block
					AlertDialog.Builder alert = new AlertDialog.Builder(
							OnlineActivity.this);
					alert.setTitle("OKAY")
							.setMessage("IO"+e.getMessage())
							.setNeutralButton("OK", null).show();
					e.printStackTrace();
				}

			}
		});

	}
}
