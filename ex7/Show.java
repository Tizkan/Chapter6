package chapter6.ex7;

import java.util.Iterator;

/**
 * Represents a performance that can take place in a theater. All
 * implementations of this interface should be immutable.
 */
public interface Show extends Iterable<Show> {

	/**
	 * @return A description of the show.
	 */
	String description();

	/**
	 * @return The running time of the show, in minutes.
	 */
	int runningTime();

	Show copy();

	/**
	 * @return An iterator over the shows directly contained in this show. The
	 *         iterator is empty for a show that does not contain other shows.
	 */
	@Override
	Iterator<Show> iterator();
}