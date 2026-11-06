package chapter6;

public interface CommandProcessor {
	void consume(UndoableCommand pCommand);
	void undoLast();
	void redoLast();
}