package com.example.efa;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import com.example.efa.logic.LogMe;
import com.example.efa.logic.Offline;
import com.example.efa.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.text.style.UpdateLayout;
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
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class DayTripActivity extends Activity {
	private static final int DATE_DIALOG = 0;
	private static final int START_TIME_DIALOG = 1;
	private static final int FINISH_TIME_DIALOG = 2;
	private static final int ID_COX_SPINNER = 100;
	private static final int ID_DRUMMER_SPINNER = 102;
	private static final int ID_CREW_SPINNER = 103;
	private static final int ID_COX_TEXTVIEW = 200;
	private static final int ID_DRUMMER_TEXTVIEW = 202;
	private static final int ID_CREW_TEXTVIEW = 203;
	private String date = null;
	private String startTime = null;
	private String arivalTime = null;
	private String[] crew = new String[24];
	private String[] drummer = new String[1];
	private String[] cox = new String[1];
	private int numOfCrew = 24;
	private boolean iscox = true;
	private boolean isdrummer = true;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// setContentView(R.layout.day_trip);

		ScrollView scrollView = new ScrollView(this);// macht das die Seite scrollt
												

		// linerareslayot
		final LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL); // s.o.
		scrollView.addView(linearLayout);// add zum scrollview
		
		// zwei auswahlmöglichkeiten
		String[] spinnerContent = { "Boot", "Gruppe" };
		final Spinner spinner = new Spinner(this);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spinnerContent);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				Object item = parent.getItemAtPosition(pos);
				if (item.toString() == "Boot") {
					linearLayout.removeAllViews();
					linearLayout.addView(spinner);
					boat(linearLayout);
				} else if (item.toString() == "Gruppe") {
					linearLayout.removeAllViews();
					linearLayout.addView(spinner);
					group(linearLayout);
				}

			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		linearLayout.addView(spinner);

		this.setContentView(scrollView);

	}

	private void boat(final LinearLayout linearLayout) {
		Time time = new Time();
		time.setToNow();
		// Datum
		if (date == null)
			date = "" + time.monthDay + "." + (time.month + 1) + "."
					+ time.year;
		final TextView tvdate = new TextView(this);
		tvdate.setText("Datum: " + date);
		linearLayout.addView(tvdate);
		Button btndatePicker = new Button(this);
		btndatePicker.setText("Ändern");

		btndatePicker.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(0);
				tvdate.setText("Datum: " + date);

			}

		});

		linearLayout.addView(btndatePicker);

		// StartZeit
		if (startTime == null)
			startTime = time.hour + ":" + time.minute;
		final TextView tvstartTime = new TextView(this);
		tvstartTime.setText("Start Zeit: " + this.startTime);
		linearLayout.addView(tvstartTime);
		Button btnStartTime = new Button(this);
		btnStartTime.setText("Ändern");

		btnStartTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(1);
				tvstartTime.setText("Start Zeit: " + startTime);

			}

		});
		linearLayout.addView(btnStartTime);

		// AnkunftsZeit
		if (arivalTime == null)
			arivalTime = time.hour + ":" + time.minute;
		final TextView tvarivalTime = new TextView(this);
		tvarivalTime.setText("Ankunfts Zeit: " + arivalTime);
		linearLayout.addView(tvarivalTime);
		Button btnFinishTime = new Button(this);
		btnFinishTime.setText("Ändern");

		btnFinishTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(2);
				tvarivalTime.setText("Ankunfts Zeit: " + arivalTime);

			}

		});
		linearLayout.addView(btnFinishTime);

		// Strecke
		final String[] route = new String[1];
		TextView tvroute = new TextView(this);
		tvroute.setText("Strecke:");
		linearLayout.addView(tvroute);
		// die routen abfragen
		final Offline offline = new Offline(DayTripActivity.this);
		String[] spinnerRouteContent = offline.getRouts();
		// neuer Spinner
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
						Object item = parent.getItemAtPosition(pos);
						route[0] = item.toString();
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
		linearLayout.addView(spinnerRoute);

		// Fahrtart
		final String[] tourtype = new String[1];
		TextView tvTourType = new TextView(this);
		tvTourType.setText("Fahrtart:");
		linearLayout.addView(tvTourType);
		Spinner spinnerTourType = new Spinner(this);
		ArrayAdapter<CharSequence> adapterTourType = ArrayAdapter
				.createFromResource(DayTripActivity.this, R.array.TourTyps,
						android.R.layout.simple_spinner_item);
		adapterTourType
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTourType.setAdapter(adapterTourType);
		spinnerTourType
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						Object item = parent.getItemAtPosition(pos);
						tourtype[0] = item.toString();
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
		linearLayout.addView(spinnerTourType);

		// Boot
		final String[] boatName = new String[1];
		TextView tvboat = new TextView(this);
		tvboat.setText("Boot:");
		linearLayout.addView(tvboat);
		String[] spinnerBoatContent = offline.getBoats(null);
		Spinner spinnerBoat = new Spinner(this);

		ArrayAdapter<String> adapterBoat = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spinnerBoatContent);
		adapterBoat
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerBoat.setAdapter(adapterBoat);
		spinnerBoat
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						Object item = parent.getItemAtPosition(pos);
						boatName[0] = item.toString();
						if (offline.getBoatTyp(boatName[0]) == "Einer") {
							numOfCrew = 1;
							iscox = false;
							isdrummer = false;
							
						} else if (offline.getBoatTyp(boatName[0]) == "Zweier") {
							numOfCrew = 2;
							iscox = false;
							isdrummer = false;
							
						} else if (offline.getBoatTyp(boatName[0]) == "3er Kanadier") {
							numOfCrew = 3;
							iscox = false;
							isdrummer = false;
							
						} else if (offline.getBoatTyp(boatName[0]) == "4er Kanadier") {
							numOfCrew = 4;
							iscox = false;
							isdrummer = false;
							
						} else if (offline.getBoatTyp(boatName[0]) == "6er Kanadier") {
							numOfCrew = 6;
							iscox = false;
							isdrummer = false;
							
						} else if (offline.getBoatTyp(boatName[0]) == "Zehner Kanadier") {
							numOfCrew = 10;
							iscox = false;
							isdrummer = false;
							
						} else if (offline.getBoatTyp(boatName[0]) == "Drachenboot") {
							numOfCrew = 20;
							iscox = true;
							isdrummer = true;
							
						}	
						paddler(linearLayout, numOfCrew, iscox, isdrummer);
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});

		linearLayout.addView(spinnerBoat);

		// Cox

		
			TextView tvcox = new TextView(this);
			tvcox.setText("Steuermann:");
			tvcox.setId(ID_COX_TEXTVIEW);
			linearLayout.addView(tvcox);
			String[] spinnerContentcox = offline.getPersons();
			Spinner spinnercox = new Spinner(this);
			spinnercox.setId(ID_COX_SPINNER);
			ArrayAdapter<String> adaptercox = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, spinnerContentcox);
			adaptercox.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnercox.setAdapter(adaptercox);
			spinnercox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parent, View view,
						int pos, long id) {
					Object item = parent.getItemAtPosition(pos);
					cox[0] = item.toString();
				}

				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
			linearLayout.addView(spinnercox);
		
		// Person
		// TODO Standart fahrer

		for (Integer i = 0; i < 24; i = i + 1) {
			final int[] I = new int[1];
			I[0] = I[0] + 1;
			TextView tvcrew = new TextView(this);
			tvcrew.setText("Paddler " + (i + 1) + ":");
			tvcrew.setId(ID_CREW_TEXTVIEW+i);
			linearLayout.addView(tvcrew);
			String[] spinnerContentcrew = offline.getPersons();
			Spinner spinnercrew = new Spinner(this);

			spinnercrew.setId(ID_CREW_SPINNER+i);
			ArrayAdapter<String> adaptercrew = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, spinnerContentcrew);
			adaptercrew.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnercrew.setAdapter(adaptercrew);
			spinnercrew.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parent, View view,
						int pos, long id) {
					Object item = parent.getItemAtPosition(pos);
					crew[I[0]] = item.toString();
				}

				public void onNothingSelected(AdapterView<?> parent) {
				}
			});

			linearLayout.addView(spinnercrew);
		}
		// Drummer

		
			TextView tvdrummer = new TextView(this);
			tvdrummer.setText("Tromler:");
			tvdrummer.setId(ID_DRUMMER_TEXTVIEW);
			linearLayout.addView(tvdrummer);
			String[] spinnerContentdrummer = offline.getPersons();
			Spinner spinnerdrummer = new Spinner(this);
			spinnerdrummer.setId(ID_DRUMMER_SPINNER);
			ArrayAdapter<String> adapterdrummer = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, spinnerContentdrummer);
			adapterdrummer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerdrummer.setAdapter(adapterdrummer);
			spinnerdrummer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parent, View view,
						int pos, long id) {
					Object item = parent.getItemAtPosition(pos);
					drummer[0] = item.toString();
				}

				public void onNothingSelected(AdapterView<?> parent) {
				}
			});

			linearLayout.addView(spinnerdrummer);
		

		// Bemerkung
		TextView comment = new TextView(this);
		comment.setText("Bemerkung:");
		linearLayout.addView(comment);
		final EditText etcomment = new EditText(this);
		etcomment.setText("");

		linearLayout.addView(etcomment);
		// Button
		Button btnSubmit = new Button(this);
		btnSubmit.setText("Speichern");
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				String comment = etcomment.getText().toString();

				try {
					String distance = offline.getdistance(route[0]);
					offline.newDayTrip(boatName[0], cox[0], crew, drummer[0],
							date, startTime, arivalTime, route[0], distance,
							tourtype[0], comment);
					AlertDialog.Builder alert = new AlertDialog.Builder(
							DayTripActivity.this);
					alert.setTitle("OKAY").setMessage("Speichern Erfolgreich")
							.setNeutralButton("OK", null).show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					e.getStackTrace().toString();
					AlertDialog.Builder alert = new AlertDialog.Builder(
							DayTripActivity.this);
					alert.setTitle("Alarm!").setMessage(e.getMessage())
							.setNeutralButton("OK", null).show();
				}
			}
		});
		linearLayout.addView(btnSubmit);
	}

	private void paddler(LinearLayout linearLayout,int numOfCrew, boolean iscox, boolean isdrummer) {
		for (int i = 0; i<numOfCrew; i++){
			linearLayout.findViewById(ID_CREW_TEXTVIEW).setVisibility(View.VISIBLE);
			linearLayout.findViewById(ID_CREW_SPINNER).setVisibility(View.VISIBLE);
		}
		for (int i = numOfCrew; i <24; i++){
			linearLayout.findViewById(ID_CREW_TEXTVIEW).setVisibility(View.GONE);
			linearLayout.findViewById(ID_CREW_SPINNER).setVisibility(View.GONE);
		}
		if(iscox){
			linearLayout.findViewById(ID_COX_TEXTVIEW).setVisibility(View.VISIBLE);
			linearLayout.findViewById(ID_COX_SPINNER).setVisibility(View.VISIBLE);
		}else{
			linearLayout.findViewById(ID_COX_TEXTVIEW).setVisibility(View.GONE);
			linearLayout.findViewById(ID_COX_SPINNER).setVisibility(View.GONE);
		}
		if(isdrummer){
			linearLayout.findViewById(ID_DRUMMER_TEXTVIEW).setVisibility(View.VISIBLE);
			linearLayout.findViewById(ID_DRUMMER_SPINNER).setVisibility(View.VISIBLE);
		}else{
			linearLayout.findViewById(ID_DRUMMER_TEXTVIEW).setVisibility(View.GONE);
			linearLayout.findViewById(ID_DRUMMER_SPINNER).setVisibility(View.GONE);
		}
		//TODO
		//linearLayout.removeAllViews();
		//linearLayout.invalidate();
		//linearLayout.postInvalidate();
		
	}

	private void group(LinearLayout linearLayout) {

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Time t = new Time();
		t.setToNow();
		switch (id) {
		case DATE_DIALOG:
			DatePickerDialog dateDlg = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							Time chosenDate = new Time();
							chosenDate.set(dayOfMonth, monthOfYear, year);
							long dtDob = chosenDate.toMillis(true);
							CharSequence strDate = DateFormat.format(
									"dd.MM.yyyy", dtDob);
							Toast.makeText(DayTripActivity.this,
									"Gewähltes Datum: " + strDate,
									Toast.LENGTH_SHORT).show();
							date = strDate.toString();
						}
					}, t.year, t.month, t.monthDay);

			dateDlg.setMessage("Suche das Datum aus");
			return dateDlg;

		case START_TIME_DIALOG:
			TimePickerDialog startDlg = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							// TODO Auto-generated method stub
							startTime = hourOfDay + ":" + minute;
							Toast.makeText(DayTripActivity.this,
									"Gewählte Startzeit: " + startTime,
									Toast.LENGTH_SHORT).show();

						}

					}, t.hour, t.minute, true);

			startDlg.setMessage("Suche die Zeit aus");
			return startDlg;

		case FINISH_TIME_DIALOG:
			TimePickerDialog finishDlg = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							// TODO Auto-generated method stub
							startTime = hourOfDay + ":" + minute;
							Toast.makeText(DayTripActivity.this,
									"Gewählte Startzeit: " + startTime,
									Toast.LENGTH_SHORT).show();

						}

					}, t.hour, t.minute, true);

			finishDlg.setMessage("Suche die Zeit aus");
			return finishDlg;
		}
		return null;

	}
}
