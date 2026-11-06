package chapter6;

import java.util.EnumMap;

import chapter6.Program4.Day;

public class Exercise18 {
	public static void main(String[] args) {
		Program4 program = new Program4(new BasicCommandProcessor());

		program.add(new Movie("Movie 1", 2000, 120), Day.MONDAY);
		program.add(new Movie("Movie 2", 2010, 90), Day.WEDNESDAY);
		program.clear();
		System.out.println("After add, add, clear:");
		System.out.println(program);

		program.undoLast();
		System.out.println("After undoLast (clear undone):");
		System.out.println(program);

		program.undoLast();
		System.out.println("After undoLast (second add undone):");
		System.out.println(program);

		program.redoLast();
		System.out.println("After redoLast (second add back):");
		System.out.println(program);
	}
}

class Program4 {

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
	private final CommandProcessor aProcessor;

	public Program4(CommandProcessor pProcessor) {
		aProcessor = pProcessor;
		doClear();
	}

	public void add(Show pShow, Day pDay) {
		aProcessor.consume(createAddCommand(pShow, pDay));
	}

	public void remove(Day pDay) {
		aProcessor.consume(createRemoveCommand(pDay));
	}

	public void clear() {
		aProcessor.consume(createClearCommand());
	}

	public void undoLast() {
		aProcessor.undoLast();
	}

	public void redoLast() {
		aProcessor.redoLast();
	}

	private UndoableCommand createAddCommand(Show pShow, Day pDay) {
		return new UndoableCommand() {
			private Show aPrevious;

			public void execute() {
				aPrevious = aShows.get(pDay);
				aShows.put(pDay, pShow);
			}

			public void undo() {
				aShows.put(pDay, aPrevious);
			}
		};
	}

	private UndoableCommand createRemoveCommand(Day pDay) {
		return new UndoableCommand() {
			private Show aPrevious;

			public void execute() {
				aPrevious = aShows.get(pDay);
				aShows.put(pDay, NULL_SHOW);
			}

			public void undo() {
				aShows.put(pDay, aPrevious);
			}
		};
	}

	private UndoableCommand createClearCommand() {
		return new UndoableCommand() {
			private EnumMap<Day, Show> aSnapshot;

			public void execute() {
				aSnapshot = new EnumMap<>(aShows);
				doClear();
			}

			public void undo() {
				aShows.putAll(aSnapshot);
			}
		};
	}

	private void doClear() {
		for (Day day : Day.values()) {
			aShows.put(day, NULL_SHOW);
		}
	}

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