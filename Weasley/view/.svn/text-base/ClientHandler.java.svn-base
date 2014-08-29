package lab4.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

import lab4.model.Location;
import lab4.model.Person;
import lab4.model.Weasley;

public class ClientHandler implements Runnable {
	
	private Socket mySocket;
	private ServerSocket myServerSocket;
	private Weasley myWeasley;
	
	public ClientHandler(Socket s, Weasley w) {
		mySocket = s;
		myWeasley = w;
		
	}
	
	public void run() {
		try {
			DataInputStream dis = new DataInputStream(mySocket.getInputStream());
			DataOutputStream dos = new DataOutputStream(mySocket.getOutputStream());
			while (true) {
				String command = dis.readUTF();
				if (command.equals("locations")) {
					// process "locations" command
					Set<Location> locations = myWeasley.getLocations();
					dos.writeInt(locations.size());
					for (Location loc: locations) {
						dos.writeUTF(loc.getName());
					}
				}
				else if (command.equals("people")) {
					// process "people" command
					Set<Person> people = myWeasley.getPeople();
					dos.writeInt(people.size());
					for (Person p: people) {
						dos.writeUTF(p.getName());
					}
				}
				else if (command.startsWith("query")) {
					// process "query" command
					String name = command.replaceFirst("query\\s+", "");
					Person p = new Person(name);
					if (myWeasley.getPeople().contains(p)) {
						dos.writeInt(1);
						dos.writeUTF(myWeasley.getLocation(p).getName());
					}
					else { // name is not a valid Person, send error signal
						dos.writeInt(-1);
					}
				}
				else {
					// invalid command, send error signal
					dos.writeInt(-1);
				}
			}
		}
		catch (Throwable t) {
			System.out.println("I got an error: " + t);
		}
	}

}
