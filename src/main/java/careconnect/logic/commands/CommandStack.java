package careconnect.logic.commands;

import java.util.Stack;

/**
 * Provides a Stack utility with constrained accessor methods.
 */
public class CommandStack {
    private final Stack<Command> stack = new Stack<>();

    /**
     * Adds a Command to the stack.
     *
     * @param cmd that will be executed when popped.
     */
    public void add(Command cmd) {
        stack.add(cmd);
    }

    /**
     * Gets the last command from the stack.
     *
     * @return a command.
     */
    public Command getLast() {
        return stack.pop();
    }

    /**
     * Removes the last command from the stack. Returns nothing.
     */
    public void removeLast() {
        stack.pop();
    }

    /**
     * Checks if there is any executable commands in the stack
     *
     * @return boolean indicating if the command stack is empty
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
