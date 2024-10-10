package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all doctors in the address book to the user.
 */
public class ListDoctorCommand extends Command {

    public static final String COMMAND_WORD = "list-doctor";
    public static final String MESSAGE_SUCCESS = "Listed all doctors";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "list command not implemented yet";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all persons in the address book.\n";

    /**
     * Executes the command and returns the result message.
     * As this command is not yet implemented, it throws a {@code CommandException}.
     *
     * @param model The model to execute the command on.
     * @throws CommandException if the command is not implemented.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }

    /**
     * Checks if this {@code ListDoctorCommand} is equal to another object.
     * Two {@code ListDoctorCommand} instances are considered equal if they are
     * the same instance or if they are both instances of {@code ListDoctorCommand}.
     *
     * @param other The other object to compare to.
     * @return true if both are the same instance or are both instances of {@code ListDoctorCommand}, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }

        // instanceof handles nulls and checks if 'other' is of the same class
        if (!(other instanceof ListDoctorCommand)) {
            return false;
        }

        // No fields to compare, so return true if it is an instance of ListDoctorCommand
        return true;
    }

    /**
     * Returns the string representation of the command.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("commandWord", COMMAND_WORD)
                .toString();
    }
}
