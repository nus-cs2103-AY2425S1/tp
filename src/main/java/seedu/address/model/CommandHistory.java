package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Represents history of successfully executed commands.
 */
public class CommandHistory implements ReadOnlyCommandHistory {
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
        setAddressBookFilePath(newCommandHistory.getAddressBookFilePath());
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
        if (!(other instanceof UserPrefs)) {
            return false;
        }

        CommandHistory otherUserPrefs = (CommandHistory) other;
        return addressBookFilePath.equals(otherUserPrefs.addressBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nLocal data file location : " + addressBookFilePath);
        return sb.toString();
    }

}
