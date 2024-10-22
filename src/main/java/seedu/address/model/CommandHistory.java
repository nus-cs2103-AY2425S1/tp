package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.isDequeEqual;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents history of successfully executed commands.
 */
public class CommandHistory implements ReadOnlyCommandHistory {
    private Deque<String> commandStack = new LinkedList<>();
    private Path addressBookFilePath = Paths.get("data" , "addressbook.json");

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
        setAddressBookFilePath(newCommandHistory.getAddressBookFilePath());
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

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
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
        return addressBookFilePath.equals(otherCommandHistory.addressBookFilePath)
                && isDequeEqual(commandStack, otherCommandHistory.getCommandStack());
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandStack, addressBookFilePath);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("commandStack", commandStack)
                .add("addressBookFilePath", addressBookFilePath)
                .toString();
    }

}
