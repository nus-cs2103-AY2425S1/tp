package seedu.address.logic.commands;

import java.util.Stack;

/**
 * Represents the command stack that stores the list of all commands
 */
public class CommandStack extends Stack<Command> {
    private static final CommandStack COMMAND_STACK = new CommandStack();

    /**
     * Constructs a command stack
     */
    private CommandStack() {
    }

    /**
     * Factory method used to get the singleton instance of command stack (used only for testing)
     */
    static CommandStack getInstance() {
        return COMMAND_STACK;
    }

    /**
     * Pushes a command to the command stack
     * @param command The most recent valid command executed by the user
     */
    public static void pushCommand(Command command) {
        if (command instanceof UndoCommand) {
            return;
        }
        COMMAND_STACK.push(command);
    }

    /**
     * Pops a command from the command stack
     * @return The most recent valid command executed by the user
     */
    public static Command popCommand() {
        if (COMMAND_STACK.isEmpty()) {
            return null;
        }
        return COMMAND_STACK.pop();
    }
}
