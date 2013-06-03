package com.example.efa;

import java.io.File;
import java.io.IOException;

import com.example.efa.logic.LogMe;
import com.example.efa.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

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

        setContentView(R.layout.day_trip);
        LogMe l = new LogMe(new File(getFilesDir()+"file.bla"));
		try {
			
			AlertDialog.Builder alert=new AlertDialog.Builder(DayTripActivity.this);
			alert.setTitle("Alert!").setMessage(l.getLastline()).setNeutralButton("OK", null).show();

		} catch (IOException e) { 
			AlertDialog.Builder alert=new AlertDialog.Builder(DayTripActivity.this);
			alert.setTitle("Alert!").setMessage(e.getMessage()).setNeutralButton("OK", null).show();
			e.printStackTrace();
		}
        
    }
}
