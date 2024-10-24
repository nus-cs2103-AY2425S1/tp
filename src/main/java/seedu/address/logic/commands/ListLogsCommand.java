package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.log.Log;
import seedu.address.model.person.IdentityNumber;

/**
 * Lists all persons in the address book to the user.
 */
public class ListLogsCommand extends Command {

    public static final String COMMAND_WORD = "logs";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";
    public static final String MESSAGE_SUCCESS = "Listed all logs for: ";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all logs of a person identified by the NRIC.\n"
            + "Parameters: NRIC\n"
            + "Example: " + COMMAND_WORD + " i/S1234567A";
    private final IdentityNumber identityNumber;

    /**
     * Creates a ListLogsCommand to list the logs of the specified person
     */
    public ListLogsCommand(IdentityNumber id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid Identity Number");
        }
        this.identityNumber = id; // Directly assign the identityNumber
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        //TODO: Handle behaviour later
        requireNonNull(model);
        model.updateFilteredLogListById(this.identityNumber);

        StringBuilder sb = new StringBuilder();
        sb.append("The NRIC you inputted is: ").append(this.identityNumber.toString()).append("\n");
        sb.append("The logs for this person are:\n");

        for (Log log : model.getFilteredLogList()) {
            sb.append("Appointment Date: ").append(log.getAppointmentDate())
                    .append(", Entry: ").append(log.getEntry()).append("\n");
        }

        return new CommandResult(sb.toString());
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

        // Since ListLogsCommand relies soley on id, last check to compare only id
        return identityNumber.equals(e.identityNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identityNumber", identityNumber)
                .toString();
    }
}
