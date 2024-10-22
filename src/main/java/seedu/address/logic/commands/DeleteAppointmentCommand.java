package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes an appointment attached to a person identified using it's displayed index from the address book.
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "del_appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person's appointment identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted Appointment from Person: %1$s";
    public static final String MESSAGE_NO_APPOINTMENT = "The Patient indicated does not have an appointment";

    private final Index targetIndex;

    public DeleteAppointmentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());

        if (personToEdit.getAppointment() == null) {
            throw new CommandException(MESSAGE_NO_APPOINTMENT);
        }

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getId(),
                personToEdit.getWard(),
                personToEdit.getDiagnosis(),
                personToEdit.getMedication(),
                personToEdit.getNotes()
        );

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteAppointmentCommand)) {
            return false;
        }

        DeleteAppointmentCommand otherDeleteCommand = (DeleteAppointmentCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
