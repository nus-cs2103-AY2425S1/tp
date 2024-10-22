package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.isDequeEqual;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents history of successfully executed commands.
 */
public class CommandHistory implements ReadOnlyCommandHistory {
    private Deque<String> commandStack = new LinkedList<>();

    /**
     * Creates a {@code CommandHistory} with default values.
     */
    public CommandHistory() {}

    /**
     * Creates a {@code CommandHistory} with the data in {@code CommandHistory}.
     */
    public CommandHistory(ReadOnlyCommandHistory commandHistory) {
        this();
        resetData(commandHistory);
    }

    /**
     * Resets the existing data of this {@code CommandHistory} with {@code newCommandHistory}.
     */
    public void resetData(ReadOnlyCommandHistory newCommandHistory) {
        requireNonNull(newCommandHistory);
        setCommandStack(newCommandHistory.getCommandStack());
    }

    /**
     * Pushes a command to the command stack.
     */
    public void addCommand(String commandString) {
        commandStack.add(commandString);
    }
    public Deque<String> getCommandStack() {
        return commandStack;
    }
    public void setCommandStack(Deque<String> commandStack) {
        requireNonNull(commandStack);
        this.commandStack = commandStack;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandHistory)) {
            return false;
        }

        CommandHistory otherCommandHistory = (CommandHistory) other;
        return isDequeEqual(commandStack, otherCommandHistory.getCommandStack());
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandStack);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("commandStack", commandStack)
                .toString();
    }

}
