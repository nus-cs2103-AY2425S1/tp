package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.core.CommandGetterResult;
import seedu.address.commons.core.CommandStack;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents history of successfully executed commands.
 */
public class CommandHistory implements ReadOnlyCommandHistory {
    private CommandStack commandStack = new CommandStack();

    /**
     * Creates a {@code CommandHistory} with default values.
     */
    public CommandHistory() {}

    /**
     * Creates a {@code CommandHistory} with the data in {@code CommandHistory}.
     */
    public CommandHistory(ReadOnlyCommandHistory commandHistory) {
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
     * Pushes a command to the command stack. Removes the last command
     * of the command stack if size exceeds {@code commandStackMaxSize}.
     */
    public void addCommand(String commandString) {
        //TODO: assert commandString is a valid and executed command?
        setCommandStack(commandStack.addCommand(commandString));
    }
    public CommandStack getCommandStack() {
        return commandStack;
    }
    public void setCommandStack(CommandStack commandStack) {
        requireNonNull(commandStack);
        this.commandStack = commandStack;
    }

    public CommandGetterResult getEarlierCommandGetterResult(CommandGetterResult commandGetterResult) {
        return commandStack.getEarlierCommandGetterResult(commandGetterResult);
    }

    public CommandGetterResult getLaterCommandGetterResult(CommandGetterResult commandGetterResult) {
        return commandStack.getLaterCommandGetterResult(commandGetterResult);
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
        return commandStack.equals(otherCommandHistory.commandStack);
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
