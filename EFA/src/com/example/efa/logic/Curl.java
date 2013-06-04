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
import java.net.URL;
import java.net.URLEncoder;

public class Curl {
	private LogMe log;

	public Curl(int i) {
		File file = new File("fbuserids.txt");
		this.log = new LogMe(file);
		if (!file.exists()) {
			System.out.println("created new File");
		}
		logids(i);
	}

	public Curl() {
		File file = new File("fbuserids.txt");
		this.log = new LogMe(file);
		if (!file.exists())
			System.out.println("created new File");
	}

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

		this.log.schreiben(s);
		return s;
	}

	private BufferedReader get_url(String s)
			throws UnsupportedEncodingException, IOException {
		URL url = new URL(s);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				url.openStream(), "UTF-8"));
		return reader;
	}

	public void logids() {
		long id = 0L;
		try {
			id = Long.parseLong(this.log.getLastline()) + 1L;
		} catch (NumberFormatException e2) {
			System.out.println("ID ist zu groß: maximal 2.147.483.647");
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		logids(id);
	}

	public void logids(long id) {
		logids(id, 1);
	}

	public void logids(long id, int anzahl) {
		while (anzahl > 0) {
			String sid = String.valueOf(id);
			try {
				log_valid_ids(sid);
				id += 1L;
				anzahl--;
			} catch (IOException e) {
				String s = e.toString();
				if (s.contains("Server returned HTTP response code: 400")) {
					id += 1L;
				} else if (s
						.contains("Server returned HTTP response code: 403"))
					try {
						long time = System.currentTimeMillis();
						String error = null;
						do {
							Thread.sleep(300010L);
							System.out.println("Es muss 5 min gewartet werden");
							try {
								log_valid_ids(sid);
								error = null;
							} catch (IOException e1) {
								error = e1.toString();
								if (s.contains("Server returned HTTP response code: 400"))
									error = null;
							}
						} while (error != null);
						System.out.println("Breaktime: "
								+ (System.currentTimeMillis() - time) + "ms");
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				else
					e.printStackTrace();
			}
		}
	}

	public int vote(String fbuserid, int projectid) throws IOException {
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
		return back;
	}

	public void logreset() {
		this.log.rest();
	}

	public boolean is_id_valid(String id) {
		try {
			log_valid_ids(id);
			return true;
		} catch (IOException e) {
		}
		return false;
	}
}
