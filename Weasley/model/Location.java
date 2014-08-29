package lab4.model;

/**
 *  Location
 *   Model of location in Weasley Clock.
 *
 *  Author: Ron Cytron
 *  Course: CS132
 *  Lab:    Studio W
 *  
 */

/**
 * Jake Bruemmer
 * Lab Section B
 * jakebruemmer@wustl.edu
 * CSE 132 Lab W
 * Location.java
 * Models the Location in a Weasley Clock
 * 
 */

public class Location {
	private String name; // name of the Location
	
	/**
	 * @param name name of the Location
	 */
	public Location(String name) {
		this.name = name;
	}
	
	/**
	 * @return name of the Location
	 */
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	/**
	 * Locations that have the same name will return the same hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	/**
	 * Two Locations are equal if they have the same name.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return (name.equals(other.getName()));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}
}
