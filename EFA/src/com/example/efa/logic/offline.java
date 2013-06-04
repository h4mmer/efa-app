package com.example.efa.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	 * @return Bei erfolgreichem Speichern wird true zurückgegeben.
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
	 * @param water
	 * @param start
	 * @param mid
	 * @param destination
	 * @param km
	 * @return true wenn es erfoglreich war
	 */
	public boolean newRoute(String water, String start, String mid, String destination, int km) {
		LogMe l = new LogMe(new File(activity.getFilesDir() + "routes.txt"));
		try {
			l.schreiben("" + water + ":" + start + "-"+mid+"-"+destination+":"+km+"km");
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
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;

	}
	/**
	 * 
	 * @return
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
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
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
		return array;

	}

}
