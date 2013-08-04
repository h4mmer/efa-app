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
	public static void post(String boatName, String boatTyp, String cox,
			LinkedList<String> crew, String drummer, String date,
			String startTime, String arivalTime, String route, int distance,
			String tourType, String comment) throws IOException {

		String post = "date=1375480155&mail=&dfBoot=" + boatName
				+ "&dfSteuermann=" + cox;

		for (String s : crew) {
			post += "&dfName" + crew.indexOf(s) + 1 + "=" + s;
		}
		post += "&cmbTrommler=" + drummer + "&dfDatum=" + date
				+ "&dfStartZeit=" + startTime + "&dfEndeZeit=" + arivalTime
				+ "&dfStrecke=" + route + "&dfkm=" + distance + "&cmbFahrtart="
				+ tourType + "&dfBemerkung=" + comment;
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		URL url = new URL("http://efa.kieler-kanu-klub.de/index.php");
		// URL url = new URL("http://127.0.0.1/index.php");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(post.toString().getBytes().length));
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.connect();
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			out.writeBytes(post.toString());
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream(), "UTF-8"));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				total.append(line);
			}

		} finally {
			connection.disconnect();
		}
	}
}
