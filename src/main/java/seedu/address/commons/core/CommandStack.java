package seedu.address.commons.core;

import static seedu.address.commons.util.CollectionUtil.isDequeEqual;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * A Serializable class that contains command history data and command history max size.
 * Guarantees: immutable.
 */
public class CommandStack implements Serializable {
    private static final int DEFAULT_COMMAND_STACK_MAX_SIZE = 2000;
    private final Deque<String> commandDeque;
    private final int commandDequeMaxSize;

    /**
     * Constructs a {@code CommandStack} with default max size, and empty history.
     */
    public CommandStack() {
        commandDequeMaxSize = DEFAULT_COMMAND_STACK_MAX_SIZE;
        commandDeque = new ArrayDeque<>();
    }

    /**
     * Creates a {@code CommandStack} with specified max size, and specified history.
     */
    public CommandStack(Deque<String> commandDeque, int commandDequeMaxSize) {
        this.commandDeque = commandDeque;
        this.commandDequeMaxSize = commandDequeMaxSize;
    }
    public Deque<String> getCommandDeque() {
        return commandDeque;
    }
    public double getCommandDequeMaxSize() {
        return commandDequeMaxSize;
    }

    /**
     * Adds an executed command string to the front of {@code commandDeque}.
     * If size of the command stack exceeds {@code commandStackMaxSize},
     * removes the earliest added command string in history.
     */
    public void addCommand(String commandString) {
        assert(commandDeque.size() <= commandDequeMaxSize);
        commandDeque.addFirst(commandString);
        if (commandDeque.size() == commandDequeMaxSize + 1) {
            commandDeque.removeLast();
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
        return isDequeEqual(commandDeque, otherCommandStack.commandDeque)
                && commandDequeMaxSize == otherCommandStack.commandDequeMaxSize;
    }
    @Override
    public int hashCode() {
        return Objects.hash(commandDeque, commandDequeMaxSize);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("commandDeque", commandDeque)
                .add("commandDequeMaxSize", commandDequeMaxSize)
                .toString();
    }

}
