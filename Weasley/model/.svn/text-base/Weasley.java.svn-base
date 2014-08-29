package lab4.model;

/**
 *  Weasley


 *   Model of clock in Weasley Clock.
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
 * Weasley.java
 * Model of a Person in a Weasley Clock
 * 
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Weasley {
	private Set<Person> people, whereYouAt;  // set of people part of clock
	private Set<Location> locations;  // set of active locations
	private Map<Person,Location> located; // which person is at which location
	private Map<String, Set<String>> locationsToPeople;
	/**
	 * Initializes empty set of people and set of locations with one entry, "limbo".
	 */
	public Weasley() {
		people = new HashSet<Person>();
		locations = new HashSet<Location>();
		located = new HashMap<Person,Location>();
		addLocation(new Location("limbo"));
		whereYouAt = new HashSet<Person>();		
	}
	
	/**
	 * Adds a person to the set of people being tracked.  The person's
	 * initial location is "limbo".
	 * @param p person to be added to set of people
	 */
	public void addPerson(Person p) {
		if (people.contains(p)) {
			return;
		}
		people.add(p);
		Location loc = new Location("limbo");
		setLocation(p,loc);
	}
	
	/**
	 * Adds a location to the set of locations that are active.
	 * @param loc location to be added to set of locations
	 */
	public void addLocation(Location loc) {
		locations.add(loc);
	}
	
	/**
	 * Accessor for set of people being tracked.
	 * @return set of people being tracked
	 */
	public synchronized Set<Person> getPeople() {
		return people;
	}
	
	/**
	 * Accessor for set of locations that are active.
	 * @return set of locations that are active
	 */
	public synchronized Set<Location> getLocations() {
		return locations;
	}
	
	/**
	 * Determine the location for a given person.
	 * If this person has not already been identified, they're in limbo.
	 * @param p person for whom to query location
	 * @return location of person p
	 */
	public synchronized Location getLocation(Person p) {
		if(getPeople().contains(p)) {
			return located.get(p);
		}
		else {
			return new Location("limbo");
		}
	}
	
	/**
	 * Establish new location for a given person. If this person has not already been identified 
	 * to the model, then add them to the Persons in the model before mapping their location.
	 * @param p person for who we are to establish location
	 * @param loc new location for person p
	 */
	public synchronized void setLocation(Person p, Location loc) {
		if (loc.equals(getLocation(p))) {
			return;
		}
		if(!people.contains(p)) {
			people.add(p);
		}
		located.put(p, loc);
		p.move(loc);
		locations.add(loc);
	}
	
	/**
	 * Remove a person from tracking. Modified to make sure that the person
	 * is removed from both the map and the Set of people in the model. The
	 * modification makes sure that the GUI updates in real time. 
	 * @param p person to be removed
	 */
	public synchronized void dropPerson(Person p) {
		located.remove(p);
		people.remove(p);
		return;
	}
	
	/**
	 * Remove an active location. All people at that location get moved to "limbo".
	 * Modified to make sure that a location that's been removed is also removed
	 * from the set of locations in the model. Modification ensures that the GUI updates
	 * in real time.
	 * @param loc location to be removed
	 */
	public synchronized void dropLocation(Location loc) {
		for (Person p : people) {
			if (loc.equals(getLocation(p))) {
				setLocation(p, new Location("limbo"));
			}
		}
		locations.remove(loc);
		return;
	}
	
	public Map<Person, Location> getMap() {
		return located;
	}	
}
