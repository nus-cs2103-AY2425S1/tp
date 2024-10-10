package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListLogsCommand extends Command {

    public static final String COMMAND_WORD = "logs";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all logs of a person identified by the NRIC.\n"
            + "Parameters: NRIC\n"
            + "Example: " + COMMAND_WORD + " S1234567A";
    private final String NRIC;

    public ListLogsCommand(String NRIC) {
        this.NRIC = NRIC;
    }
    public static final String MESSAGE_SUCCESS = "Listed all logs";
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        //model.updateFilteredPersonList(NRIC);
        //throw new CommandException("ListLogsCommand not implemented yet");
        return new CommandResult("The NRIC you inputted is: " + NRIC);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof ListLogsCommand)) {
            return false;
        }
        ListLogsCommand e = (ListLogsCommand) other;
        return NRIC.equals(e.NRIC);
    }
}
