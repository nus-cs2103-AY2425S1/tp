package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.log.Log;
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Person;

/**
 * Adds a log to the person identified by their Identity Number in the address book.
 */
public class AddLogCommand extends Command {

    public static final String COMMAND_WORD = "addlog";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a log to the person identified by the Identification Number in the person list.\n"
            + "Parameters: i/NRIC d/DATE l/LOG ENTRY\n"
            + "Format of Appointment Date: dd MMM yyyy\n"
            + "Example: " + COMMAND_WORD + " i/S1234567D d/20 Oct 2024 l/First visit to the clinic\n";

    public static final String MESSAGE_ADD_LOG_SUCCESS = "Added log for Person: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person with ID %1$s not found.";
    public static final String MESSAGE_INVALID_ID = "NRIC not found in system, perhaps there was a typo.";

    public static final String MESSAGE_DUPLICATE_LOG = "This log already exists in the person's log list. "
            + "Are you sure you are adding a new log?";


    private final IdentityNumber identityNumber;
    private final Log log;

    /**
     * Creates an AddLogCommand to add the specified log to the person.
     */
    public AddLogCommand(IdentityNumber identityNumber, Log log) {
        requireNonNull(identityNumber);
        requireNonNull(log);
        this.identityNumber = identityNumber;
        this.log = log;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToUpdate = getPerson(model);
        model.addLog(personToUpdate, log);

        return new CommandResult(String.format(MESSAGE_ADD_LOG_SUCCESS, personToUpdate.getName()),
                false, false, false, false, -1, false, identityNumber,
                log.getAppointmentDate(), log.getEntry());
    }

    //@@author junyi73
    private Person getPerson(Model model) throws CommandException {
        List<Person> lastShownList = model.getPersonList();
        Person personToUpdate = null;

        // Find the person by identity number
        for (Person person : lastShownList) {
            if (person.getIdentityNumber().equals(identityNumber)) {
                personToUpdate = person;
                break;
            }
        }

        // If person was not found, throw an exception
        if (personToUpdate == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, identityNumber));
        }
        return personToUpdate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLogCommand)) {
            return false;
        }

        AddLogCommand otherAddLogCommand = (AddLogCommand) other;
        return identityNumber.equals(otherAddLogCommand.identityNumber) && log.equals(otherAddLogCommand.log);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identityNumber", identityNumber)
                .add("log", log)
                .toString();
    }
}
