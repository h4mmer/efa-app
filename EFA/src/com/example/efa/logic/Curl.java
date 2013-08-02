package com.example.efa.logic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

public class Curl {
	private LogMe log;

	private String log_valid_ids(String s) throws UnsupportedEncodingException,
			IOException {
		BufferedReader reader = get_url("https://graph.facebook.com/" + s
				+ "?fields=id&method=GET");
		String line;
		while ((line = reader.readLine()) != null) {

			if (line.contains("id")) {
				s = line.substring(7, line.lastIndexOf('"'));
				System.out.println("ID: " + s);
			} else {
				s = null;
			}
		}
		return s;
	}

	private BufferedReader get_url(String s)
			throws UnsupportedEncodingException, IOException {
		URL url = new URL(s);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				url.openStream(), "UTF-8"));
		return reader;
	}

	/**
	 * 
	 * @param boatName
	 * @param cox
	 *            "Nachname, Vorname" wenn nicht vorhanden "" übergeben
	 * @param crew
	 *            "Nachname, Vorname" wenn nicht vorhanden "" übergeben Maximal
	 *            24 einträge
	 * @param drummer
	 *            "Nachname, Vorname" wenn nicht vorhanden "" übergeben
	 * @param date
	 *            Im Format 0001-01-01
	 * @param startTime
	 *            Im Format 11%3A11
	 * @param arivalTime
	 *            Im Format 11%3A11
	 * @param route
	 * @param distance
	 * @param tourType
	 * @param comment
	 * @throws Exception
	 */
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void post(String boatName,String boatTyp, String cox, LinkedList<String> crew,
			String drummer, String date, String startTime, String arivalTime,
			String route, int distance, String tourType, String comment)
			throws IOException {
		
		String post = "date=1375480155&mail=&dfBoot=" + boatName + "&dfSteuermann=" + cox;
		
		for (String s : crew) {
			post += "&dfName" + crew.indexOf(s) + 1 + "=" + s;
		}
		post += "&cmbTrommler=" + drummer + "&dfDatum=" + date
				+ "&dfStartZeit=" + startTime + "&dfEndeZeit=" + arivalTime
				+ "&dfStrecke=" + route + "&dfkm=" + distance + "&cmbFahrtart="
				+ tourType + "&dfBemerkung=" + comment;
		post = "date=1375480155&mail=&dfBoot=Test&cmbBootTyp=K1&dfSteuermann=&dfName1=test&dfName2=&dfName3=&dfName4=&dfName5=&dfName6=&dfName7=&dfName8=&dfName9=&dfName10=&dfName11=&dfName12=&dfName13=&dfName14=&dfName15=&dfName16=&dfName17=&dfName18=&dfName19=&dfName20=&dfName21=&dfName22=&dfName23=&dfName24=&cmbTrommler=&dfDatum=0001-01-01&dfStartZeit=02%3A02&dfEndeZeit=23%3A03&dfStrecke=sa&dfkm=0&cmbFahrtart=Wanderfahrt&dfBemerkung=";
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		URL url = new URL("http://efa.kieler-kanu-klub.de/index.php");
		// URL url = new URL("http://127.0.0.1/index.php");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			  connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	            connection.setRequestProperty("charset", "utf-8");
	            connection.setRequestProperty("Content-Length", "" + Integer.toString(post.toString().getBytes().length));
	            connection.setRequestMethod("POST");
	            connection.setDoOutput(true);
	            connection.connect();
	            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
	            out.writeBytes(post.toString());
	            out.flush();
	            out.close();
	            InputStream is = connection.getInputStream();
	            InputStreamReader reader = new InputStreamReader(is);
	            BufferedReader r = new BufferedReader(reader);
	              StringBuilder total = new StringBuilder();
	              String line;
	              while ((line = r.readLine()) != null) {
	                  total.append(line);
	              }

	              
		} finally {
			connection.disconnect();
		}
		// connection.setRequestMethod("POST");
		// connection.setDoInput(true);
		// connection.setDoOutput(true);
		// connection.setUseCaches(false);
		// connection.setRequestProperty("Content-Type",
		// "application/x-www-form-urlencoded");
		// connection.setRequestProperty("Content-Length",
		// String.valueOf(post.length()));
		//
		// OutputStreamWriter writer = new OutputStreamWriter(
		// connection.getOutputStream());
		// writer.write(post);
		// writer.flush();
		//
		// BufferedReader reader = new BufferedReader(new InputStreamReader(
		// connection.getInputStream()));
		// String line;
		// while ((line = reader.readLine()) != null) {
		// System.out.println(line);
		// }
		//
		// writer.close();
		// reader.close();
	}
}
