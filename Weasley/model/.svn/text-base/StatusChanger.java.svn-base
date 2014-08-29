package lab4.model;

/**
 *  StatusChanger
 *   Manage status changes in PCS system.
 *
 *  Author: Ron Cytron
 *  Course: CS132
 *  Lab:    Studio W
 *  
 */

import java.beans.PropertyChangeSupport;

/**
 * A class to propagate status changes via firePropertyChange.
 * @author cytron
 */

abstract public class StatusChanger {

	public static boolean logging = false;
	final protected PropertyChangeSupport pcs;
	
	/**
	 * Allocate new PropertyChangeSupport object.
	 */
	public StatusChanger() {
		pcs = new PropertyChangeSupport(this);
	}
	
	/**
	 * Determine the name of the person changing location.
	 * @return name of person changing location
	 */
	abstract public String getName();

	/**
	 * Accessor for PCS object.
	 * @return PCS object
	 */
	public PropertyChangeSupport getPCS() {
		return pcs;
	}

	/**
	 * Announce the message.
	 * @param message new location
	 */
	public void status(String message) {
		if (logging) {
			System.out.println(getName() + " is now at " + message);
		}
		//
		// The use of null below pushes out the message no matter what
		//   You could use the second and third parameters to
		//   cause a message to be pushed only if something has
		//   changed.
		//
		pcs.firePropertyChange(message, null, null);

	}

}