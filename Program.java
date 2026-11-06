package chapter6;

import java.util.EnumMap;

/**
 * Class responsible for managing a program that consists of various shows
 * presented on different days of one week. Each day of the week must be
 * associated with exactly one Show object. If there is not show on a given day,
 * a special object of type show is used to represent a "non-show".
 */
public class Program {

	public enum Day {
		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
	}

	private static final Show NULL_SHOW = createNullShow();

	private static Show createNullShow() {
		return new Show() {
			public String description() { return ""; }
			public int runningTime() { return 0; }
			public Show copy() { return createNullShow(); }
			public int hashCode() { return 0; }
			public boolean equals(Object pObject) {
				return pObject != null && pObject.getClass() == this.getClass();
			}
		};
	}

	private final EnumMap<Day, Show> aShows = new EnumMap<>(Day.class);

	public Program() {
		clear();
	}

	/**
	 * Clear the program by removing all existing shows.
	 */
	public void clear() {
		for (Day day : Day.values()) {
			aShows.put(day, NULL_SHOW);
		}
	}

	/**
	 * Adds a new show to the program. Overrides any existing show on that day.
	 */
	public void add(Show pShow, Day pDay) {
		assert pShow != null && pDay != null;
		aShows.put(pDay, pShow);
	}

	/**
	 * Removes a show from the program.
	 */
	public void remove(Day pDay) {
		assert pDay != null;
		aShows.put(pDay, NULL_SHOW);
	}

	/**
	 * @return A copy of the show on a given day.
	 */
	public Show get(Day pDay) {
		assert pDay != null;
		return aShows.get(pDay).copy();
	}

	/**
	 * @return True if there is no show on the given day.
	 */
	public boolean isEmpty(Day pDay) {
		assert pDay != null;
		return aShows.get(pDay).equals(NULL_SHOW);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Day day : aShows.keySet()) {
			if (aShows.containsKey(day)) {
				result
					.append(String.format("%9s", day.name()))
					.append(": ")
					.append(aShows.get(day).description())
					.append("\n");
			}
		}
		return result.toString();
	}
}