package chapter6;

import chapter6.Program3.Day;

public class Exercise17 {
	public static void main(String[] args) {
		Program3 program = new Program3();
		CommandProcessor processor = new BasicCommandProcessor();

		processor.consume(program.createAddCommand(new Movie("Movie 1", 2000, 120), Day.MONDAY));
		processor.consume(program.createAddCommand(new Movie("Movie 2", 2010, 90), Day.WEDNESDAY));
		processor.undoLast();
		System.out.println("After 2 commands and 1 undo:");
		System.out.println(program);

		processor.redoLast();
		System.out.println("After redoLast:");
		System.out.println(program);

		processor.undoLast();
		processor.consume(program.createAddCommand(new Movie("Movie 3", 2020, 100), Day.FRIDAY));
		System.out.println("After undo, then a new command:");
		System.out.println(program);
		try {
			processor.redoLast();
		} catch (IllegalStateException e) {
			System.out.println("redoLast after a new command: " + e.getMessage());
		}
	}
}