package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.log.Log;
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class ListLogsCommand extends Command {

    public static final String COMMAND_WORD = "logs";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";
    public static final String MESSAGE_LIST_LOG_SUCCESS = "Listed all logs for: %1$s [%2$s]";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person with ID %1$s not found.";

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
        requireNonNull(model);

        ObservableList<Person> personList = model.getAddressBook().getPersonList();
        int personIndex = -1;
        Person person = null;

        for (Person p: personList) {
            if (identityNumber.equals(p.getIdentityNumber())) {
                personIndex = personList.indexOf(p);
                person = p;
                break;
            }
        }

        if (personIndex == -1 || person == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, identityNumber));
        }

        // Note: this sb is temporary for the duration that the command is not properly hooked up to the gui.
        StringBuilder sb = new StringBuilder();
        sb.append("The NRIC you inputted is: ").append(this.identityNumber).append("\n");
        sb.append("The logs for this person are:\n");

        for (Log log : model.getSessionLog(personIndex)) {
            sb.append("Appointment Date: ").append(log.getAppointmentDate())
                    .append(", Entry: ").append(log.getEntry()).append("\n");
        }

        model.getSessionLog(personIndex);
        return new CommandResult(String.format(MESSAGE_LIST_LOG_SUCCESS + sb.toString(),
                identityNumber, person.getName()),
                false, false, false, true, personIndex);
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
