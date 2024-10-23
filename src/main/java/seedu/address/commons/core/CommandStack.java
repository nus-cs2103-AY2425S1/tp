package seedu.address.commons.core;

import static seedu.address.commons.util.CollectionUtil.isDequeEqual;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * A Serializable class that contains command history and command history max size.
 * Guarantees: immutable.
 */
public class CommandStack implements Serializable {
    private static final int DEFAULT_COMMAND_STACK_MAX_SIZE = 2000;
    private final Deque<String> commandStack;
    private final int commandStackMaxSize;

    /**
     * Constructs a {@code CommandStack} with default max size, and empty history.
     */
    public CommandStack() {
        commandStackMaxSize = DEFAULT_COMMAND_STACK_MAX_SIZE;
        commandStack = new ArrayDeque<>();
    }

    /**
     * Creates a {@code CommandStack} with specified max size, and specified history.
     */
    public CommandStack(Deque<String> commandStack, int commandStackMaxSize) {
        this.commandStack = commandStack;
        this.commandStackMaxSize = commandStackMaxSize;
    }
    public Deque<String> getCommandStack() {
        return commandStack;
    }
    public double getCommandStackMaxSize() {
        return commandStackMaxSize;
    }

    /**
     * Adds an executed command string to the front.
     * If size of the command stack exceeds {@code commandStackMaxSize},
     * removes the earliest added command string in history.
     */
    public void addCommand(String commandString) {
        assert(commandStack.size() <= commandStackMaxSize);
        commandStack.addFirst(commandString);
        if (commandStack.size() == commandStackMaxSize + 1) {
            commandStack.removeLast();
        }
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof CommandStack)) {
            return false;
        }
        CommandStack otherCommandStack = (CommandStack) other;
        return isDequeEqual(commandStack, otherCommandStack.commandStack)
                && commandStackMaxSize == otherCommandStack.commandStackMaxSize;
    }
    @Override
    public int hashCode() {
        return Objects.hash(commandStack, commandStackMaxSize);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("commandStack", commandStack)
                .add("commandStackMaxSize", commandStackMaxSize)
                .toString();
    }

}
