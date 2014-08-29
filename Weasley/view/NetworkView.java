package lab4.view;

/**
 *  NetworkView
 *   Remote view in Weasley Clock.  Supports socket connections.
 *
 *  Author: Roger Chamberlain
 *  Course: CS132
 *  Lab:    4
 *  
 *  
 *  Author: Jake Bruemmer
 *  Course: CS132 Lab B
 *  Email: jakebruemmer@wustl.edu
 *  Lab:    4
 *  File Name: NetworkView.java
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import lab4.model.Location;
import lab4.model.Person;
import lab4.model.Weasley;

public class NetworkView implements Runnable {
	Weasley model;
	ServerSocket ss;
	private String location;
	private Set<String> people;

	public NetworkView(Weasley model) throws IOException {
		this.model = model;
		ss = new ServerSocket(10400);
		location = "";
	}	
	
	/**
	 * Runs a new thread for each connection. This method waits until a client trys to 
	 * connect to the server and then starts a new thread for each connection once the 
	 * connection has been made. After a connection has been made, the method continually
	 * listens for commands and queries over the data stream and makes changes to the model
	 * based on the protocol that has been established in the lab's instructions and documented
	 * on my cover page. Each thread has it's own DataInputStream and DataOutputStream that 
	 * allows communication with the client. 
	 */
	public void run() {
		try {
			while (true) {
				final Socket s;
				s = ss.accept();
				Thread t = new Thread(new Runnable() {
					public void run() {
						while (true) {
							try {
								DataInputStream dis = new DataInputStream(s.getInputStream());
								DataOutputStream dos = new DataOutputStream(s.getOutputStream());
								String command = dis.readUTF();
								if (command.equals("locations")) {
									// process "locations" command
									Set<Location> locations = model.getLocations();
									dos.writeInt(locations.size());
									for (Location loc: locations) {
										dos.writeUTF(loc.getName());
									}
								}
								else if (command.equals("people")) {
									// process "people" command
									Set<Person> people = model.getPeople();
									dos.writeInt(people.size());
									for (Person p: people) {
										dos.writeUTF(p.getName());
									}
								}
								else if (command.startsWith("query")) {
									// process "query" command
									String name = command.replaceFirst("query\\s+", "");
									Person p = new Person(name);
									if (model.getPeople().contains(p)) {
										dos.writeInt(1);
										dos.writeUTF(model.getLocation(p).getName());
									}
									else { // name is not a valid Person, send error signal
										dos.writeInt(-1);
									}
								}
								else if (command.startsWith("addPerson")) {
									// process "add person" command
									String name = command.replaceFirst("addPerson\\s+", "");
									Person p = new Person(name);
									model.addPerson(p);
								}
								else if (command.startsWith("addLocation")) {
									String name = command.replaceFirst("addLocation\\s+", "");
									Location l = new Location(name);
									model.addLocation(l);
								}
								else if (command.startsWith("dropPerson")) {
									String name = command.replaceFirst("dropPerson\\s+", "");
									Person p = new Person(name);
									model.dropPerson(p);
								}
								else if (command.startsWith("dropLocation")) {
									String name = command.replaceFirst("dropLocation\\s+", "");
									Location l = new Location(name);
									model.dropLocation(l);
								}
								else {
									// invalid command, send error signal
									dos.writeInt(-1);
								}
								
							}
							catch (Throwable t) {}
						} 
					}
				});
				t.start();
			}
		}
		catch (EOFException e1) {
			// graceful termination on EOF
		} 
		catch (IOException e) {
			System.out.println("Remote connection reset");
		}


	}
}







