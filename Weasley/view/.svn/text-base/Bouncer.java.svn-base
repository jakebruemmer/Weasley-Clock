package lab4.view;

/**
 * 
 */

/**
 *  Bouncer

 *   Bouncer view in Weasley Clock.
 *
 *  Author: Ron Cytron, Roger Chamberlain
 *  Course: CS132
 *  Lab:    Studio W
 *  
 */
/**
 * Jake Bruemmer
 * Lab Section B
 * jakebruemmer@wustl.edu
 * CSE 132 Lab W
 * Bouncer.java
 * Bouncer view in Weasley Clock
 * 
 */

import lab4.model.Location;
import lab4.model.Person;
import lab4.model.Weasley;

public class Bouncer {
	private Observer bouncer;
	
	/**
	 * Bouncer watches everyone entering a particular location.
	 * @param model Weasley clock
	 * @param location location to watch
	 */
	public Bouncer(Weasley model, Location location) {
		bouncer = new Observer("Bouncer");
		for (Person p : model.getPeople()) {
			bouncer.addSubject(p, location.getName());
		}
	}

}
