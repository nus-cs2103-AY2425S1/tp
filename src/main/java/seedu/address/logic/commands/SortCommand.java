package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Sorts the persons in the address book by their appointment dates.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Sorted list by appointment dates.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts persons by their appointment dates in ascending order.\n"
            + "Example: " + COMMAND_WORD;
    public static final String NO_APPOINTMENT_FOUND = "No contacts with appointment dates found.";
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        int noAppointment = model.sortFilteredPersons();
        if (noAppointment == -1) {
            throw new CommandException(NO_APPOINTMENT_FOUND);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof SortCommand;
    }

    @Override
    public String toString() {

        return COMMAND_WORD;
    }

}
