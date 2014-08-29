package lab4.view;

/**
 *  Stalker
 *   Stalker view in Weasley Clock.
 *
 *  Author: Ron Cytron
 *  Course: CS132
 *  Lab:    Studio W
 *  
 */

import lab4.model.Person;
import lab4.model.Weasley;

public class Stalker {
	private Observer stalker;

	/**
	 * Stalker watches what one person does.
	 * @param model Weasley clock
	 * @param p person to watch
	 */
	public Stalker(Weasley model, Person p) {
		stalker = new Observer("Stalker");
		stalker.addObsession(p);
	}
}
