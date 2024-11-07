package seedu.address.logic;

import java.util.Stack;

import seedu.address.logic.commands.Command;

/**
 * Keeps track of Commands given by user using a stack
 */
public class CommandLog {
    private Stack<Command> log = new Stack<>();
    private Stack<String> inputLog = new Stack<>();

    /**
     * Adds a command to the stack
     */
    public void add(Command command) {
        log.push(command);
    }

    /**
     * Pops most recent command at the top of the stack
     * @return null if stack is empty
     */
    public Command getPreviousCommand() {
        return log.isEmpty() ? null : log.pop();
    }

    /**
     * Adds a user input to the stack
     */
    public void addinput(String input) {
        inputLog.push(input);
    }

    /**
     * Returns most recent user input at the top of the stack
     * @return empty string if stack is empty
     */
    public String getPreviousInput() {
        return inputLog.isEmpty() ? "" : inputLog.pop();
    }

}
