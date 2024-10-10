package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.IdentityNumber;

/**
 * Lists all persons in the address book to the user.
 */
public class ListLogsCommand extends Command {

    public static final String COMMAND_WORD = "logs";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";
    public static final String MESSAGE_SUCCESS = "Listed all logs";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all logs of a person identified by the NRIC.\n"
            + "Parameters: NRIC\n"
            + "Example: " + COMMAND_WORD + " S1234567A";
    private final IdentityNumber identityNumber;

    /**
     * Creates a ListLogsCommand to list the logs of the specified person
     */
    public ListLogsCommand(String id) {
        try {
            this.identityNumber = new IdentityNumber(id);
        } catch (IllegalArgumentException e) {
            //TODO: handle exception later
            throw new IllegalArgumentException("Invalid NRIC");
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("ListLogsCommand not implemented yet");

        //TODO: Handle behaviour later
        //requireNonNull(model);
        //model.updateFilteredPersonList(nric);
        //return new CommandResult("The NRIC you inputted is: " + nric);
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
        return identityNumber.equals(e.identityNumber);
    }
}
