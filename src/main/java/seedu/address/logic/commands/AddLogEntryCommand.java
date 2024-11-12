package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.log.AppointmentDate;
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Person;

/**
 * Prompts users to add a log entry on a separate window before calling AddLogCommand.
 */
public class AddLogEntryCommand extends Command {

    public static final String COMMAND_WORD = "addentry";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens a text box to input log entry "
            + "for a person identified by the "
            + "NRIC. Parameters: NRIC, DATE\n"
            + "Example: " + COMMAND_WORD + " i/S1234567D d/20 Nov 2024";
    public static final String MESSAGE_ADD_LOG_SUCCESS = "Added log for Person: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person with ID %1$s not found.";
    public static final String MESSAGE_INVALID_ID = "NRIC not found in system, perhaps there was a typo.";

    public static final String MESSAGE_DUPLICATE_LOG = "This log already exists in the person's log list. "
            + "Are you sure you are adding a new log?";
    private final IdentityNumber identityNumber;
    private final AppointmentDate appointmentDate;

    /**
     * Creates an AddLogEntryCommand to store identity number and appointment date.
     */
    public AddLogEntryCommand(IdentityNumber identityNumber, AppointmentDate appointmentDate) {
        this.identityNumber = identityNumber;
        this.appointmentDate = appointmentDate;
    }

    /**
     * Executes the command and returns the result.
     * @param model the model to execute the command on
     * @return the result of the command
     * @throws CommandException if the person is not found
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!isValidPerson(model)) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, identityNumber));
        }

        // return command result to prompt popup
        return new CommandResult(
                "Please enter the log entry for the appointment on "
                        + appointmentDate.toString(),
                false, false, false, false, -1, true, identityNumber,
                appointmentDate, "this should never be shown");
    }

    /**
     * Retrieves the person from the model based on the identity number.
     * @param model the model to retrieve the person from
     * @return the person with the identity number
     * @throws CommandException if the person is not found
     */
    private boolean isValidPerson(Model model) {
        List<Person> lastShownList = model.getPersonList();
        for (Person person : lastShownList) {
            if (person.getIdentityNumber().equals(identityNumber)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLogEntryCommand)) {
            return false;
        }

        AddLogEntryCommand otherAddLogEntryCommand = (AddLogEntryCommand) other;
        return identityNumber.equals(otherAddLogEntryCommand.identityNumber)
                && appointmentDate.equals(otherAddLogEntryCommand.appointmentDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identityNumber", identityNumber)
                .add("appointmentDate", appointmentDate)
                .toString();
    }
}
