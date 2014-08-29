package lab4.view;

/**
 *  Logger
 *   Logger view in Weasley Clock.
 *
 *  Author: Ron Cytron, Roger Chamberlain
 *  Course: CS132
 *  Lab:    Studio W
 *  
 */

import lab4.model.Person;
import lab4.model.Weasley;

/**
 * Jake Bruemmer
 * Lab Section B
 * jakebruemmer@wustl.edu
 * CSE 132 Lab W
 * Logger.java
 * Logger view in Weasley Clock
 * 
 */
public class Logger {
	private Observer logger;
	
	/**
	 * Logger watches all activity.
	 * @param model Weasley clock
	 */
	public Logger(Weasley model) {
		logger = new Observer("Logger");
		for (Person p : model.getPeople()) {
			logger.addObsession(p);
		}
	}

}
