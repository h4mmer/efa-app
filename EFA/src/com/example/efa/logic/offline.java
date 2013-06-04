package com.example.efa.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import com.example.efa.PersonActivity;

import android.app.Activity;
import android.app.AlertDialog;

public class offline {

	public Activity activity;
	public offline (Activity activity){
		this.activity = activity;
	}
	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param boatName
	 * @return Bei erfolgreichem Speichern wird true zur�ckgegeben.
	 */
	public boolean newPerson(String firstName, String lastName, String boatName) {
		LogMe l = new LogMe(new File(activity.getFilesDir() + "persons.txt"));
		try {
			l.schreiben("" + firstName + "%" + lastName + "%" + boatName + "");
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 
	 * @param boatName
	 * @param boatType
	 * @return true wenn es erfolgreich war
	 */
	public boolean newBoat(String boatName, String boatType) {
		LogMe l = new LogMe(new File(activity.getFilesDir() + "boats.txt"));
		try {
			l.schreiben("" + boatName + "%" + boatType + "");
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 
	 * @param waters
	 * @param start
	 * @param mid
	 * @param destination
	 * @param distance
	 * @return true wenn es erfoglreich war
	 */
	public boolean newRoute(String waters, String start, String mid, String destination, int distance) {
		LogMe l = new LogMe(new File(activity.getFilesDir() + "routes.txt"));
		try {
			l.schreiben("" + waters + ":" + start + "-"+mid+"-"+destination+":"+distance+"km");
			return true;
		} catch (IOException e) {
			return false;
		}
	}
/**
 * 
 * @param name Name der Gruppe !muss eindeutig sein!
 * @param member Teilnehmer der Gruppe [Person][0:Vorname,1:Nachname,2:Boot]
 * @return true wenn es funktioniert
 */
	public boolean newGroup(String name,String[][] member) {
		LogMe l = new LogMe(new File(activity.getFilesDir() + "groups.txt"));
		try {
			String test = member[0][2];
			for (int i = 0; i<member.length;i++){
				l.schreiben(""+name+":"+member[i][0]+""+member[i][1]+""+member[i][2]+"");
			}
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 
	 * @param date Jahr-Monat-Tag
	 * @param startTime Stunde:Minute
	 * @param arivalTime Stunde:Minute
	 * @param route "" + waters + ":" + start + "-"+mid+"-"+destination+":"+distance+"km"
	 * @param tourType String
	 * @param comment String
	 * @param person Vorname%Nachname%rest
	 * @param boat Boot%Typ
	 * @return
	 */
	public boolean newDayTrip(String date, String startTime, String arivalTime, String route, String tourType, String comment, String person, String boat){
		return false;
	}
	/**
	 * 
	 * @return String Array Nachname, Vorname
	 */
	public String[] getPersons() {
		LogMe l = new LogMe(new File(activity.getFilesDir() + "persons.txt"));
		LinkedList<String> list = null;
		try {
			list = l.getList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			String[] a = {""};
			return a;
		}
		String[] array = new String[list.size()];
		for (String s: list) {
			int first = s.indexOf("%");
			int second = s.indexOf("%", first+1);
			array[list.indexOf(s)]= s.substring(first+1, second)+ ", "+s.substring(0, first);
		}
		Arrays.sort(array);
		return array;

	}
	/**
	 * 
	 * @return String Array aus Bootsname (Botstyp)
	 */
	public String[] getBoats() {
		LogMe l = new LogMe(new File(activity.getFilesDir() + "boats.txt"));
		LinkedList<String> list = null;
		try {
			list = l.getList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			String[] a = {""};
			return a;
		}
		String[] array = new String[list.size()];
		for (String s : list) {
			array[list.indexOf(s)] =s.substring(0, s.indexOf("%"))+" ("+s.substring(s.indexOf("%")+1, s.length())+")";
		}
		Arrays.sort(array);
		return array;

	}
	
	/**
	 * 
	 * @return
	 */
	public String[] getRouts() {
		LogMe l = new LogMe(new File(activity.getFilesDir() + "routs.txt"));
		LinkedList<String> list = null;
		try {
			list = l.getList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			String[] a = {""};
			return a;
		}
		String[] array = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		Arrays.sort(array);
		return array;

	}
	/**
	 * 
	 * @return
	 */
	public String[] getGroups() {
		LogMe l = new LogMe(new File(activity.getFilesDir() + "groups.txt"));
		LinkedList<String> list = null;
		try {
			list = l.getList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			String[] a = {""};
			return a;
		}
		String[] array = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		Arrays.sort(array);
		return array;

	}

}
