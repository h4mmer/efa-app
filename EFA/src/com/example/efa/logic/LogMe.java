package com.example.efa.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class LogMe {
	FileWriter writer;
	File file;

	/**
	 * 
	 * @param f File welches bearbeitet werden soll.
	
	 */
	public LogMe(File f)  {
		this.file = f;
		if (!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * Reset das File
	 */
	public void rest() {
		try {
			this.writer = new FileWriter(this.file);
			this.writer.flush();
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Füt eine Neue Zeile hinzu
	 * @param InputString String dergeschrieben werden soll
	 * @throws IOException 
	 */
	public void schreiben(String InputString) throws IOException {
		
			this.writer = new FileWriter(this.file, true);
			this.writer.write(InputString);
			this.writer.write(System.getProperty("line.separator"));
			this.writer.flush();
			this.writer.close();
		
	}

	/**
	 * Giebt die Letzte Zeileas
	 * @return Letzte Zeile in datei
	 * @throws IOException
	 */
	public String getLastline() throws IOException {
		FileReader fr = new FileReader(this.file);
		BufferedReader br = new BufferedReader(fr);
		String back = null;
		while (true) {
			String lastline = br.readLine();
			if (lastline == null)
				break;
			back = lastline;
		}

		br.close();
		return back;
	}

	/**
	 * Giebt die nachgefragte Line aus
	 * @param line Zeile die ausgegebn werden soll
	 * @return String
	 * @throws IOException
	 */
	public String getline(int line) throws IOException {
		FileReader fr = new FileReader(this.file);
		BufferedReader br = new BufferedReader(fr);
		String back = null;
		for (int i = line; i > 0; i--) {
			back = br.readLine();
		}
		br.close();
		return back;
	}

	public LinkedList<String> getList() throws FileNotFoundException {
		FileReader fr = new FileReader(this.file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		LinkedList<String> list = new LinkedList<String>();
		try {
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public int getnumofLines(){
		return 0;
	}
}
