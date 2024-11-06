package seedu.address.model;

import static java.util.Objects.requireNonNull;

/**
 * Represents the command information that users reference.
 */
public class CommandInfo {


    private String commandUsage;

    /**
     * Creates a CommandInfo object.
     */
    public CommandInfo(String commandUsage) {
        requireNonNull(commandUsage);
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
        if (!(other instanceof CommandInfo)) {
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
