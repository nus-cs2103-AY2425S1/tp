package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

public class AppointmentCommand extends Command {

    public static final String COMMAND_WORD = "apt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to a client "
            + "identified by their distinct client name. "
            + "Existing appointment will be overwritten with the new appointment. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + " 20/12/2024 "
            + PREFIX_FROM + " [FROM] "
            + PREFIX_TO + " [TO]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "date/ 20/12/2024 fr/ 0800 to/ 1000";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Date: %2$s, From: %3$s, To: %4$s";
    public static final String MESSAGE_ADD_APPOINTMENT_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_UPDATE_APPOINTMENT_SUCCESS = "Removed remark from Person: %1$s";

    private final Index index;
    private final Appointment appointment;

    public String toAdd;
    public AppointmentCommand(Index index, Appointment appointment) {
        this.index =  index;
        this.appointment = appointment;
    }

    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), appointment, personToEdit.getTags());
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }
    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !personToEdit.getAppointment().isEmpty()
                ? MESSAGE_ADD_APPOINTMENT_SUCCESS
                : MESSAGE_UPDATE_APPOINTMENT_SUCCESS;
        return String.format(message, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentCommand)) {
            return false;
        }

        AppointmentCommand a = (AppointmentCommand) other;
        return index.equals(a.index) && appointment.equals(a.appointment);
    }
}
