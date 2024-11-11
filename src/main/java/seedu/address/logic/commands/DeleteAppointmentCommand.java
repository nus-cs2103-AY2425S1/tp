package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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

    public static final String COMMAND_WORD = "delappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the patient's appointment identified by the index number used in the displayed patient list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted Appointment from Patient: \n\n%1$s";
    public static final String MESSAGE_NO_APPOINTMENT = "The Patient indicated does not have an appointment";
    private static final Logger logger = LogsCenter.getLogger(DeleteAppointmentCommand.class);

    private final Index targetIndex;

    public DeleteAppointmentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("executing Delete Appointment Command!");
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning("Patient index is out of bounds!");
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

        CommandResult commandResult = new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                Messages.format(editedPerson)));
        logger.info("Delete Appointment Command executed successfully!");
        return commandResult;
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
