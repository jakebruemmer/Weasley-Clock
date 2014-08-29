package lab4.model;

/**
 *  Person
 *   Model of person in Weasley Clock.
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
 * Person.java
 * Creates a person based on a given name. 
 * 
 */

public class Person extends Actor {
	private String name; // name of the Person
	
	/**
	 * @param name name of the Person
	 */
	public Person(String name) {
		super(name);
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see weasley.model.Actor#move(weasley.model.Location)
	 */
	public void move(Location loc) {
		status(loc.getName());
	}
	
	/**
	 * @return name of the Person
	 */
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	/**
	 * Two Persons with the same name will have the same hashcode
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/**
	 * Two Persons are equal if they have the same name.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return (name.equals(other.getName()));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * Returns the name of a Person
	 */
	@Override
	public String toString() {
		return getName();
	}
}
