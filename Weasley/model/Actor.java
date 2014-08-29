package lab4.model;

/**
 *  Actor
 *   Extend StatusChanger to support a name.
 *
 *  Author: Ron Cytron
 *  Course: CS132
 *  Lab:    Studio W
 *  
 */

abstract public class Actor extends StatusChanger {

	protected final String name;
	
	/**
	 * Manage reporting of actions via PCS.
	 * @param name person taking action
	 */
	public Actor(String name) {
		this.name = name;
	}

	/**
	 * Perform the actions of the actor.
	 * @param loc location the actor moves to
	 */
	public abstract void move(Location loc);
	
	/* (non-Javadoc)
	 * @see weasley.model.StatusChanger#getName()
	 */
	public String getName() {
		return name;
	}
	
}