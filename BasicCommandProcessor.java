package chapter6;

import java.util.ArrayDeque;
import java.util.Deque;

public class BasicCommandProcessor implements CommandProcessor {

	private final Deque<UndoableCommand> aExecuted = new ArrayDeque<>();
	private final Deque<UndoableCommand> aUndone = new ArrayDeque<>();

	@Override
	public void consume(UndoableCommand pCommand) {
		pCommand.execute();
		aExecuted.push(pCommand);
		aUndone.clear();
	}

	@Override
	public void undoLast() {
		if (aExecuted.isEmpty()) {
			throw new IllegalStateException("Nothing to undo");
		}
		UndoableCommand command = aExecuted.pop();
		command.undo();
		aUndone.push(command);
	}

	@Override
	public void redoLast() {
		if (aUndone.isEmpty()) {
			throw new IllegalStateException("Nothing to redo");
		}
		UndoableCommand command = aUndone.pop();
		command.execute();
		aExecuted.push(command);
	}
}