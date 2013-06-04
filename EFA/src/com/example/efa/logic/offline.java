package com.example.efa.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import com.example.efa.PersonActivity;

import android.app.Activity;
import android.app.AlertDialog;

public class offline extends Activity {

	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param boatName
	 * @return Bei erfolgreichem Speichern wird true zurückgegeben.
	 */
	public boolean newPerson(String firstName, String lastName, String boatName) {
		LogMe l = new LogMe(new File(getFilesDir() + "persons.txt"));
		try {
			l.schreiben("" + firstName + "%" + lastName + "%" + boatName + "");
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean newBoat(String boatName, String boatType) {
		LogMe l = new LogMe(new File(getFilesDir() + "boats.txt"));
		try {
			l.schreiben("" + boatName + "%" + boatType + "");
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public String[] getBoats() {
		LogMe l = new LogMe(new File(getFilesDir() + "boats.txt"));
		LinkedList<String> list = null;
		try {
			list = l.getList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		String[] array = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;

	}

}
