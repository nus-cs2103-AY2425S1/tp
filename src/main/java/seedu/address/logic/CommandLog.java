package seedu.address.logic;

import java.util.Stack;

import seedu.address.logic.commands.Command;

/**
 * Keeps track of Commands given by user
 */
public class CommandLog {
    private Stack<Command> log = new Stack<>();

    public void add(Command command) {
        log.push(command);
    }

    public Command undo() {
        return log.isEmpty() ? null : log.pop();
    }

    public boolean hasLog() {
        return !log.isEmpty();
    }
}
