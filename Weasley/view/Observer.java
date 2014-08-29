package lab4.view;

/**
 *  Observer
 *   An observer of a subject's status change.
 *
 *  Author: Ron Cytron
 *  Course: CS132
 *  Lab:    Studio W
 *  
 */

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import lab4.model.StatusChanger;

public class Observer {

	private final String name;

	public Observer(String name) {
		this.name = name;
	}

	/**
	 * Observe the subject only when the specified event (status change) occurs,
	 *   reacting by printing out a message.
	 * @param subject person who moved
	 * @param event new location
	 */
	public void addSubject(final StatusChanger subject, final String event) {

		Runnable r = new Runnable() {
			public void run() {
				System.out.println("   " 
						+ name  					// Observer's name
						+ ": I see that " 
						+ subject + " is now at " 	// Subject's name
						+ event);					// Subject's location
			}
		};

		register(subject.getPCS(), event, r);
	}

	/**
	 * Helper method to cause the provided Runnable to execute when the
	 *   specified pcs's event occurs.
	 * @param pcs PCS object
	 * @param event new location
	 * @param r Runnable object
	 */
	private void register(PropertyChangeSupport pcs, String event, final Runnable r) {
		//
		// Use the method to add listeners for a particular event
		//
		pcs.addPropertyChangeListener(
				event,
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent arg0) {
						r.run();

					}

				}
		);
	}

	/**
	 * Observe the subject for *any* status change
	 * @param subject
	 */
	public void addObsession(final StatusChanger subject) {
		//
		// Use the method to add listeners for *all* events
		//
		subject.getPCS().addPropertyChangeListener(
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						System.out.println("   " 
								+ name 
								+ " watching over " 
								+ subject
								+ " who is now at " 
								+ evt.getPropertyName());

					}

				}
		);
	}


}
