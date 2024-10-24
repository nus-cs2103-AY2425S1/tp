package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import seedu.address.model.Model;

/**
 * Lists all appointments in the address book to the user.
 */
public class ListAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "lista";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all appointments in SocialBook as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all appointments";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(MESSAGE_SUCCESS, true, false, false);
    }
}
