package chapter6;

import chapter6.Program3.Day;

public class Exercise16 {
	public static void main(String[] args) {
		Program3 program = new Program3();
		CommandProcessor processor = new BasicCommandProcessor();

		processor.consume(program.createAddCommand(new Movie("Movie 1", 2000, 120), Day.MONDAY));
		processor.consume(program.createAddCommand(new Movie("Movie 2", 2010, 90), Day.WEDNESDAY));
		processor.consume(program.createClearCommand());
		System.out.println("After 3 commands:");
		System.out.println(program);

		for (int i = 1; i <= 3; i++) {
			processor.undoLast();
			System.out.println("After undoLast #" + i + ":");
			System.out.println(program);
		}
		try {
			processor.undoLast();
		} catch (IllegalStateException e) {
			System.out.println("undoLast on empty history: " + e.getMessage());
		}
	}
}