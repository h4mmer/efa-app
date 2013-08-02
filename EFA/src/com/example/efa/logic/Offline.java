package com.example.efa.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import com.example.efa.PersonActivity;
import com.example.efa.logic.trowables.InvalidcrewException;
import com.example.efa.logic.trowables.UnknownException;

import android.app.Activity;
import android.app.AlertDialog;

public class Offline {

	public Activity activity;
	private String baseDir;

	public Offline(Activity activity) {
		this.activity = activity;
		baseDir =activity.getFilesDir().toString();
		
	}

	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param boatName
	 * @return Bei erfolgreichem Speichern wird true zurückgegeben.
	 * @throws Exception
	 */
	public void newPerson(String firstName, String lastName) throws Exception {
		// TODO check if alles Valid
		LogMe l = new LogMe(new File(baseDir + "persons.txt"));

		l.schreiben(l.getnumofLines() + "%" + lastName + "%" + firstName);

	}

	/**
	 * 
	 * @param boatName
	 * @param boatType
	 * @param defaultCrew
	 * @throws Exception
	 */
	public void newBoat(String boatName, String boatType, String defaultCrew)
			throws Exception {
		LogMe l = new LogMe(new File(baseDir + "boats.txt"));
		// TODO check if alles Valid
		// TODO Paderlid
		l.schreiben(l.getnumofLines() + "%" + boatName + "%" + boatType+"%"+defaultCrew);

	}

	/**
	 * 
	 * @param waters
	 * @param start
	 * @param mid
	 * @param destination
	 * @param distance
	 * @return true wenn es erfoglreich war
	 * @throws Exception
	 */
	public void newRoute(String waters, String start, String mid,
			String destination, int distance) throws Exception {
		LogMe l = new LogMe(new File(baseDir + "routes.txt"));
		// TODO check if Valid
		// TODO Diferenciate with mid wwithout
		l.schreiben(l.getnumofLines() + "%" + waters + ": " + start + " - "
				+ mid + "-" + destination + "%" + distance);

	}

	/**
	 * 
	 * @param groupName
	 *            Name der Gruppe !muss eindeutig sein!
	 * @param member
	 *            Teilnehmer der Gruppe [Person][0:Nachname,1:Vorname,2:Boot]
	 * @return true wenn es funktioniert
	 * @throws Exception
	 */
	public void newGroup(String groupName, String[][] member) throws Exception {
		LogMe l = new LogMe(new File(baseDir + "groups.txt"));
		// TODO check if valid
		String members = null;

		for (int i = 0; i < member.length; i++) {
			members += "§Name§" + member[i][0] + "$" + member[i][1] + "$"
					+ member[i][2];
		}
		l.schreiben(l.getnumofLines() + "%" + groupName + "%" + members);
	}

	/**
	 * 
	 * Speichert eine Tour Nicht belegten Feldern "" übergeben
	 * 
	 * @param boatName
	 *            Name des Boots
	 * @param cox
	 *            Name des Steuermanns
	 * @param crew
	 *            Liste der Mit fahrer (max.24)
	 * @param drummer
	 *            Name des Tromlers (Drachenboot)
	 * @param date
	 *            Datum tt.mm.jjjj
	 * @param startTime
	 *            HH:mm
	 * @param arivalTime
	 *            HH:mm
	 * @param route
	 *            Gewässer: Start - Pause - Ziel
	 * @param distance
	 *            in km
	 * @param tourtype
	 *            Wanderfahrt, Vereinsfahrt, Verbandsfahrt, Training,
	 *            Drachenboot-Training, Drachenboot-Rennen
	 * @param comment
	 *            Kommentar
	 * @return
	 * @throws Exception
	 */
	public void newDayTrip(String boatName, String cox,
			String[] crew, String drummer, String date,
			String startTime, String arivalTime, String route, String distance,
			String tourtype, String comment) throws Exception {
		if (crew.length > 24)
			throw new InvalidcrewException();
		// TODO check if alles Valid
		LogMe l = new LogMe(
				new File(baseDir + "newDayTrips.txt"));
		String paddlers = null;
		for (String s : crew) {
			if (s==null)s="";
			paddlers += "$Name$" + s;
		}
		l.schreiben(l.getLastline() + "%" + boatName + "%" + cox + "%"
				+ paddlers + "%" + drummer + "%" + date + "%" + startTime + "%"
				+ arivalTime + "%" + route + "%" + distance + "%" + tourtype
				+ "%" + comment);
	}

	/**
	 * 
	 * @return String Array Nachname, Vorname
	 */
	// Works
	public String[] getPersons() {
		LogMe l = new LogMe(new File(baseDir + "persons.txt"));
		LinkedList<String> list = null;
		try {
			list = l.getList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			String[] a = { "Error" };
			return a;
		}
		String[] array = new String[list.size()];
		for (String s : list) {
			int first = s.indexOf("%");
			int second = s.indexOf("%", first + 1);
			array[list.indexOf(s)] = s.substring(first + 1, second) + ", "
					+ s.substring(second + 1);
		}
		Arrays.sort(array);
		return array;
		

	}

	/**
	 * 
	 * @return String Array aus Bootsname (Botstyp)
	 */
	public String[] getBoats(String boatType) {

		LogMe l = new LogMe(new File(baseDir + "boats.txt"));
		LinkedList<String> list = null;
		try {
			list = l.getList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			String[] a = { "" };
			return a;
		}
		String[] array = new String[list.size()];
		for (String s : list) {
			int first = s.indexOf("%");
			int second = s.indexOf("%", first + 1);
			if (boatType == null || s.substring(second + 1) == boatType)
				array[list.indexOf(s)] = s.substring(first + 1, second);
		}
		Arrays.sort(array);
		return array;

	}
	public String getBoatTyp(String boatName){
		LogMe l = new LogMe(new File(baseDir + "boats.txt"));
		String back = null;
		try {
			back = l.getline(getlineof(boatName, l));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int first = back.indexOf("%");
		int second = back.indexOf("%", first + 1);
		back = back.substring(second+1);
		return back;
	}
	//get standart user

	/**
	 * 
	 * @return
	 */
	public String[] getRouts() {
		LogMe l = new LogMe(new File(baseDir + "routes.txt"));
		LinkedList<String> list = null;
		try {
			list = l.getList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			String[] a = { "" };
			return a;
		}
		String[] array = new String[list.size()];
		for (String s: list) {
			int first = s.indexOf("%");
			int second = s.indexOf("%", first + 1);
			array[list.indexOf(s)] = s.substring(s.indexOf("%")+1, second);
		}
		Arrays.sort(array);
		return array;

	}
	public String getdistance(String route) throws FileNotFoundException, IOException, UnknownException{
		LogMe l = new LogMe(new File(baseDir + "routes.txt"));
		String back = l.getline(getlineof(route, l));
		int first = back.indexOf("%");
		int second = back.indexOf("%", first + 1);
		back = back.substring(second+1);
		return back;
		
	}

	/**
	 * 
	 * @return
	 */
	// TODO
	public String[] getGroups() {
		LogMe l = new LogMe(new File(baseDir + "groups.txt"));
		LinkedList<String> list = null;
		try {
			list = l.getList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			String[] a = { "" };
			return a;
		}
		String[] array = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		Arrays.sort(array);
		return array;

	}
	public String[][][] getDayTrips(){
		LogMe l = new LogMe(
				new File(baseDir + "newDayTrips.txt"));
		LinkedList<String> list = null;
		try {
			list = l.getList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		String[][][] array = new String[list.size()][12][24];
		for (String s: list) {
			//Liste unterteilen
			int num = s.indexOf("%");
			int boatName = s.indexOf("%", num+1);
			int cox = s.indexOf("%", boatName + 1);
			int chrew = s.indexOf("%", cox + 1);
			int drummer = s.indexOf("%", chrew + 1);
			int date = s.indexOf("%", drummer + 1);
			int starttime = s.indexOf("%", date + 1);
			int arivaltime = s.indexOf("%", starttime + 1);
			int route = s.indexOf("%",arivaltime + 1);
			int distance = s.indexOf("%", route + 1);
			int tourType = s.indexOf("%", distance + 1);
			//BootsName
			array[list.indexOf(s)][0][0] = s.substring(num+1,boatName);
			//Steuermann
			array[list.indexOf(s)][1][0] = s.substring(boatName+1,cox);
			//Crew
			String temp = s.substring(cox+1,chrew);
			int[] index = new int[24];
			for(int i = 0; i<23;i++){
				
				index[i+1]=temp.indexOf("$Name$", index[i]+1);
				//array[list.indexOf(s)][2][i] = temp.substring(index[i]+6, index[i+1]);
			}
			//drummer
			array[list.indexOf(s)][3][0] = s.substring(chrew+1,drummer);
			//datum
			array[list.indexOf(s)][4][0] = s.substring(drummer+1,date);
			//startzeit
			array[list.indexOf(s)][5][0] = s.substring(date+1,starttime);
			//ankunftszeit
			array[list.indexOf(s)][6][0] = s.substring(starttime+1,arivaltime);
			//route
			array[list.indexOf(s)][7][0] = s.substring(arivaltime+1,route);
			//Kilometer
			array[list.indexOf(s)][8][0]= s.substring(route+1,distance);
			//TourTyp
			array[list.indexOf(s)][9][0] = s.substring(distance+1,tourType);
			//Comment
			array[list.indexOf(s)][10][0] = s.substring(tourType+1);;
			
		}
		return array;
	}
	private int getlineof(String object, LogMe l) throws FileNotFoundException, UnknownException{
		
		LinkedList<String> list = l.getList();
		for (String s:list){
			if (s.contains(object)) return list.indexOf(s);
		}
		throw new UnknownException();
	}

}
