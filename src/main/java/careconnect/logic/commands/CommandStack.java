package careconnect.logic.commands;

import java.util.Stack;

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

    public void removeLast() {
        stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
