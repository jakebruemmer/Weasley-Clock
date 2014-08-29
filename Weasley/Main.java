package lab4;

/**
 *  Main

 *   Drives a Weasley Clock, instantiating several locations
 *   and people, then randomly moving the people around.
 *
 *  Author: Ron Cytron, Roger Chamberlain
 *  Course: CS132
 *  Lab:    4
 *  
 */

/**
 * Jake Bruemmer
 * Lab Section B
 * jakebruemmer@wustl.edu
 * CSE 132 Lab W
 * Main.java
 * Main model for the Weasley Clock
 * 
 */

import java.io.EOFException;
import java.io.IOException;
import java.util.Random;
import java.util.Set;

import lab4.model.Location;
import lab4.model.Person;
import lab4.model.Weasley;
import lab4.view.Bouncer;
import lab4.view.Logger;
import lab4.view.NetworkView;
import lab4.view.Stalker;

public class Main implements Runnable {

	final private Weasley    model;
	final private Random     rand;
	public static final int DELAY = 1000; // nominal sleep delay (in ms)

	public Main() {
		String[]
				places = { "library", "gym", "dorm", "home", "blueberry hill", "clocktower", "forty", "the loop", "Mudd Field", "class" },
				names  = { "studiousmaximus", "alice", "bob", "charlie", "diane", "billy", "sarah", "john", "ron", "hermoine", "harry" };

		rand = new Random();

		model = new Weasley();

		for (String s : names) {
			model.addPerson(new Person(s));
		}

		for (String s : places) {
			model.addLocation(new Location(s));
		}

		//
		// logger will produce a printout of all things that happen
		//   to the model.
		//
		Logger logger = new Logger(model);
		//
		// stalker will report any change in the location of the 0th person
		//
		Stalker stalker = new Stalker(model, model.getPeople().iterator().next());
		//
		// bouncer will report anybody trying to get into the 0th place
		//
		Bouncer bouncer = new Bouncer(model, model.getLocations().iterator().next());
	}

	public void run() {

		Set<Person>   people    = model.getPeople();
		Set<Location> locations = model.getLocations();

		for (Person p : people) {
			Location at = model.getLocation(p);
			System.out.println("Person " + p + " is initially at " + at);
		}

		//
		// simulate the people moving around
		//

		for (int i=0; i < 1000; ++i) {
			//
			// Pick a random person and place
			//
			int person = rand.nextInt(people.size());
			int place  = rand.nextInt(locations.size());

			//
			// and tell the model the person's new location (may be the same as its old location)
			//
			model.setLocation(people.toArray(new Person[0])[person], locations.toArray(new Location[0])[place]);
			System.out.println(model.getMap().toString());
			sleep();

		}

		//
		// Get rid of the people and locations
		//

		//		for (Person p : people) {
		//			model.dropPerson(p);
		//		}
		//		
		//		for (Location l : locations) {
		//			model.dropLocation(l);
		//		}
	}

	/**
	 * The sleep that is observed nominally.
	 * Change DELAY to speed things up or slow things down.
	 */
	public static void sleep() {
		sleep(DELAY);
	}

	/**
	 * Delay for an arbitrary amount of time.
	 * @param ms delay amount (in ms)
	 */
	public static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		}
		catch (Exception e) {
			throw new Error("Main.sleep: should not happen " + e);
		}
	}

	/**
	 * Accessor for Weasley Clock model
	 * @return Weasley Clock model
	 */
	public Weasley getWeasley() {
		return model;
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Main m = new Main();
		Runnable v = new NetworkView(m.getWeasley());
		Thread t = new Thread(v);
		t.start();
		m.run();
	}
}
