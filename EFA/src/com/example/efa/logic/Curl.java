package com.example.efa.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

	public boolean post(String fbuserid, int projectid) throws IOException {
		int back = 0;
		String body = "fbuserid="
				+ URLEncoder.encode(String.valueOf(fbuserid), "UTF-8") + "&"
				+ "projectid="
				+ URLEncoder.encode(String.valueOf(projectid), "UTF-8");

		URL url = new URL(
				"https://facebook.digitalroyal.de/skfoerde/abi2013/abstimmen/setvoting.php");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length",
				String.valueOf(body.length()));

		OutputStreamWriter writer = new OutputStreamWriter(
				connection.getOutputStream());
		writer.write(body);
		writer.flush();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {

			if (line.contains("1")) {
				back = 1;
			} else {
				back = 0;
			}
		}

		writer.close();
		reader.close();
		return false;
	}

	/**
	 * 
	 * @param boatName
	 * @param cox
	 *            "Nachname, Vorname" wenn nicht vorhanden "" übergeben
	 * @param crew
	 *            "Nachname, Vorname" wenn nicht vorhanden "" übergeben
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
	 * @throws IOException
	 */
	public void post(String boatName, String cox, LinkedList<String> crew,
			String drummer, String date, String startTime, String arivalTime,
			String route, int distance, String tourType, String comment)
			throws IOException {

		// Steuerman=cox
		String post = "&dfBoot=" + boatName + "&dfSteuermann=" + cox;
		for (String s : crew) {
			post += "&dfName" + crew.indexOf(s) + 1 + "=" + s;
		}
		post += "&cmbTrommler=" + drummer + "&dfDatum=" + date
				+ "&dfStartZeit=" + startTime + "&dfEndeZeit=" + arivalTime
				+ "&dfStrecke=" + route + "&dfkm=" + distance + "&cmbFahrtart="
				+ tourType + "&dfBemerkung=" + comment;

		URL url = new URL("http://efa.kieler-kanu-klub.de/index.php");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length",
				String.valueOf(post.length()));

		OutputStreamWriter writer = new OutputStreamWriter(
				connection.getOutputStream());
		writer.write(post);
		writer.flush();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}

		writer.close();
		reader.close();
	}
}
