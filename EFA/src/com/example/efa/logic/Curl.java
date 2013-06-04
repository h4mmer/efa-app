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
	 * @param mail
	 * @param boatName
	 * @param boatTyp
	 * @param crew
	 * @param date
	 *            Im Format 0001-01-01
	 * @param startTime
	 *            Im Format 11%3A11
	 * @param arivalTime
	 *            Im Format 11%3A11
	 * @param route
	 * @param Distance
	 * @param tourType
	 * @param Comment
	 */
	public void post(String mail, String boatName, String boatTyp,
			String crew[][], String date, String startTime, String arivalTime,
			String route, int distance, String tourType, String comment) {
		if(crew.length!=26)return;
		String post = ("date=" + 1370255898 + "&mail=" + mail + "&dfBoot="
				+ boatName + "&cmbBootTyp=" + boatTyp + "&dfSteuermann="
				+ crew[0][1] + ", " + crew[0][0] + "&dfName1=" + crew[1][1]
				+ ", " + crew[1][0] + "&dfName2=" + crew[2][1] + ", "
				+ crew[2][0] + "&dfName3=" + crew[3][1] + ", " + crew[3][0]
				+ "&dfName4=" + crew[4][1] + ", " + crew[4][0] + "&dfName5="
				+ crew[5][1] + ", " + crew[5][0] + "&dfName6=" + crew[6][1]
				+ ", " + crew[6][0] + "&dfName7=" + crew[7][1] + ", "
				+ crew[7][0] + "&dfName8=" + crew[8][1] + ", " + crew[8][0]
				+ "&dfName9=" + crew[9][1] + ", " + crew[9][0] + "&dfName10="
				+ crew[10][1] + ", " + crew[10][0] + "&dfName11=" + crew[11][1]
				+ ", " + crew[11][0] + "&dfName12=" + crew[12][1] + ", "
				+ crew[12][0] + "&dfName13=" + crew[13][1] + ", " + crew[13][0]
				+ "&dfName14=" + crew[14][1] + ", " + crew[14][0]
				+ "&dfName15=" + crew[15][1] + ", " + crew[15][0]
				+ "&dfName16=" + crew[16][1] + ", " + crew[16][0]
				+ "&dfName17=" + crew[17][1] + ", " + crew[17][0]
				+ "&dfName18=" + crew[18][1] + ", " + crew[18][0]
				+ "&dfName19=" + crew[19][1] + ", " + crew[19][0]
				+ "&dfName20=" + crew[20][1] + ", " + crew[20][0]
				+ "&dfName21=" + crew[21][1] + ", " + crew[21][0]
				+ "&dfName22=" + crew[22][1] + ", " + crew[22][0]
				+ "&dfName23=" + crew[23][1] + ", " + crew[23][0]
				+ "&dfName24=" + crew[24][1] + ", " + crew[24][0]
				+ "&cmbTrommler=" + crew[25][1] + ", " + crew[25][0]
				+ "&dfDatum=" + date + "&dfStartZeit=" + startTime
				+ "&dfEndeZeit=" + arivalTime + "&dfStrecke=" + route
				+ "&dfkm=" + distance + "&cmbFahrtart=" + tourType
				+ "&dfBemerkung=" + comment);
	}
}
