package chapter6;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import chapter6.Program3.Day;

public class Exercise15 {
	public static void main(String[] args) {
		Program3 program = new Program3();
		List<UndoableCommand> commands = List.of(
			program.createAddCommand(new Movie("Movie 1", 2000, 120), Day.MONDAY),
			program.createAddCommand(new Movie("Movie 2", 2010, 90), Day.WEDNESDAY),
			program.createAddCommand(new Movie("Movie 3", 2020, 100), Day.MONDAY),
			program.createRemoveCommand(Day.WEDNESDAY),
			program.createClearCommand());

		// Run each command, saving the state BEFORE it runs
		List<String> before = new ArrayList<>();
		for (UndoableCommand command : commands) {
			before.add(program.toString());
			command.execute();
		}
		System.out.println("After all commands:");
		System.out.println(program);

		// Undo in reverse order and compare with the saved state
		for (int i = commands.size() - 1; i >= 0; i--) {
			commands.get(i).undo();
			boolean ok = program.toString().equals(before.get(i));
			System.out.println("undo command " + (i + 1) + ": " + (ok ? "OK" : "FAILED"));
		}
	}
}

interface UndoableCommand extends Command {
	void undo();
}

class Program3 {

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

	public Program3() {
		clear();
	}

	public UndoableCommand createAddCommand(Show pShow, Day pDay) {
		return new UndoableCommand() {
			private Show aPrevious;

			public void execute() {
				aPrevious = get(pDay);
				add(pShow, pDay);
			}

			public void undo() {
				add(aPrevious, pDay);
			}
		};
	}

	public UndoableCommand createRemoveCommand(Day pDay) {
		return new UndoableCommand() {
			private Show aPrevious;

			public void execute() {
				aPrevious = get(pDay);
				remove(pDay);
			}

			public void undo() {
				add(aPrevious, pDay);
			}
		};
	}

	public UndoableCommand createClearCommand() {
		return new UndoableCommand() {
			private EnumMap<Day, Show> aSnapshot;

			public void execute() {
				aSnapshot = new EnumMap<>(aShows);
				clear();
			}

			public void undo() {
				aShows.putAll(aSnapshot);
			}
		};
	}

	private void clear() {
		for (Day day : Day.values()) {
			aShows.put(day, NULL_SHOW);
		}
	}

	private void add(Show pShow, Day pDay) {
		assert pShow != null && pDay != null;
		aShows.put(pDay, pShow);
	}

	private void remove(Day pDay) {
		assert pDay != null;
		aShows.put(pDay, NULL_SHOW);
	}

	private Show get(Day pDay) {
		assert pDay != null;
		return aShows.get(pDay);
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