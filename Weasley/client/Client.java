package lab4.client;

//TA:MB Grade: 100/100
//Great job!

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import lab4.model.Location;
import lab4.model.Person;

/**
 *  Client
 *   Remote network view for a Weasley Clock.
 *
 *  Author: Jake Bruemmer
 *  Course: CS132 Lab B
 *  Email: jakebruemmer@wustl.edu
 *  Lab:    4
 *  File Name: Client.java
 *  
 *  Client side for the Weasley model. Sends queries to the server
 *  and then updates the compenents of the WeasleyClock (GUI) to reflect
 *  the information that has been sent from the server.
 *  
 *  
 */

public class Client {
	// Complete the Client class
	private Socket socket;
	private DataOutputStream dos;
	private DataInputStream dis;
	private Map<String, Set<String>> locationsToPeople;
	private Set<String> people, places;

	public Client() throws UnknownHostException, IOException {
		this.socket = new Socket("localhost", 10400);
		this.dos = new DataOutputStream(socket.getOutputStream());
		this.dis = new DataInputStream(socket.getInputStream());
		locationsToPeople = new HashMap<String, Set<String>>();
		people = new HashSet<String>();
		places = new HashSet<String>();
	}
	
	/**
	 * Sends queries to the server in order to retrieve information about people 
	 * and their whereabouts. This method updates a person's location in real time
	 * to the WeasleyClock (GUI). Follow's the protocol that has been laid out in 
	 * the lab 4 instructions and in my cover page for this lab. The client also updates
	 * the locationsToPeople instance variable constantly which allows each WeasleyClock
	 * to find out who is at what location in real time. 
	 * @param persons
	 * @param locations
	 * @param peopleToPlaces
	 */
	public void run(JList persons, JList locations, final JTextArea peopleToPlaces, final JTextArea bottomLeftTextArea, final JTextField whosThere) {
		try {
			while(true) {
				Set<String> peopleRun = new HashSet<String>();
				Set<String> placesRun = new HashSet<String>();
				HashMap<String, String> map = new HashMap<String, String>();
				
				dos.writeUTF("people");
				int a = dis.readInt();
				for(int i = 0; i < a; i++) {
					String person = dis.readUTF();
					peopleRun.add(person);
				}
				people = peopleRun;
				persons.setListData(peopleRun.toArray());

				dos.writeUTF("locations");
				int b = dis.readInt();
				for(int i = 0; i < b; i++) {
					String location = dis.readUTF();
					placesRun.add(location);
				}
				places = placesRun;
				locations.setListData(placesRun.toArray());

				for(String s : peopleRun) {
					dos.writeUTF("query " + s);
					int c = dis.readInt();
					if(c == 1) {
						String location = dis.readUTF();
						map.put(s, location);
					}
				}
				StringBuilder personLoc = new StringBuilder();
				for(String p : map.keySet()) {
					personLoc.append(p + " is at: " + map.get(p) + "! \n");
				}
				peopleToPlaces.setText(personLoc.toString());
				
				for(String s : placesRun) {
					Set<String> areHere = new HashSet<String>();
					for(String st : map.keySet()) {
						if(map.get(st).equals(s)) {
							areHere.add(st);
						}
					}
					locationsToPeople.put(s, areHere);
				}
		
				String textFieldEntry = whosThere.getText();
				if(placesRun.contains(textFieldEntry)) {
					StringBuilder sb = new StringBuilder();
					for(String s : locationsToPeople.get(textFieldEntry)) {
						sb.append(s + "\n");
					}
					bottomLeftTextArea.setText(sb.toString());
				}
				else {
					bottomLeftTextArea.setText("");
				}
				
				
			}
		}
		catch (EOFException e) {
			System.out.println("Server terminated. End of file.");
		}
		catch (SocketException t) {
			System.out.println("Server terminated from: " + t);
		}
		catch(IOException i) {
			System.out.println("Don't want this " + i);
		}
		catch(Throwable t) {
			System.out.println("Don't want this " + t);
		}
	}
	/**
	 * Entry point for lab4 client.
	 * @param args unused
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	
	public DataOutputStream getDOS() {
		return dos;
	}
	
	public DataInputStream getDIS() {
		return dis;
	}
	
	public Map<String, Set<String>> getL2P() {
		return locationsToPeople;
	}
	
	public String getPeopleHere(String s) {
		StringBuilder sb = new StringBuilder();
		for(String st : locationsToPeople.get(s)) {
			sb.append(st + "\n");
		}
		return sb.toString();
	}
	
	public Set<String> getPeople() {
		return people;
	}
	
	public Set<String> getPlaces() {
		return places;
	}
}
