package seedu.address.model;

import seedu.address.model.person.Age;

/**
 * Represents the command information that users reference.
 */
public class CommandInfo {


    private String commandUsage;

    /**
     * Creates a CommandInfo object.
     */
    public CommandInfo(String commandUsage) {
        this.commandUsage = commandUsage;
    }

    /**
     * Gets the command usage string.
     */
    public String getUsage() {
        return commandUsage;
    }

    @Override
    public String toString() {
        return commandUsage;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Age)) {
            return false;
        }

        CommandInfo otherName = (CommandInfo) other;
        return commandUsage.equals(otherName.commandUsage);
    }

    @Override
    public int hashCode() {
        return commandUsage.hashCode();
    }
}
