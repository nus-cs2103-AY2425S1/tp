package seedu.address.logic.commands;

import java.util.Stack;

/**
 * Represents the command stack that stores the list of all commands
 */
public class CommandStack extends Stack<Command> {
    private static final CommandStack commandStack = new CommandStack();

    public static CommandStack getInstance() {
        return commandStack;
    }
}
