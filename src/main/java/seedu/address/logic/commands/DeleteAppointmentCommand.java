package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;


/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "deleteappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the patient identified by their NRIC or Index used in the patient list.\n"
            + "Parameters: NRIC (must be a valid NRIC), Date(dd-MM-yyyy), 24h time(HH:mm)\n"
            + "Example: " + COMMAND_WORD + " s1234567z 01-01-2024 12:12\n"
            + "OR \n"
            + "Parameters: Index (must be a valid Index in the list), Date(dd-MM-yyyy), 24h time(HH:mm)\n"
            + "Example: " + COMMAND_WORD + " 1 01-01-2024 12:12";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted Appointment Successfully";

    private final Nric targetNric;
    private final Index targetIndex;
    private final Appointment appointment;

    /**
     * Creates a DeleteCommand to delete a person identified by their NRIC.
     *
     * @param targetNric The NRIC of the person to be deleted.
     * @param appointment The Appointment of the person to be deleted.
     */
    public DeleteAppointmentCommand(Nric targetNric, Appointment appointment) {
        this.targetNric = targetNric;
        this.targetIndex = null;
        this.appointment = appointment;
    }

    /**
     * Creates a DeleteCommand to delete a person identified by their index in the displayed person list.
     *
     * @param targetIndex The index of the person to be deleted.
     * @param appointment The Appointment of the person to be deleted.
     */
    public DeleteAppointmentCommand(Index targetIndex, Appointment appointment) {
        this.targetIndex = targetIndex;
        this.targetNric = null;
        this.appointment = appointment;
    }
    @Override // Change
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToModify = null;
        if (targetNric != null) {
            for (Person person : lastShownList) {
                if (person.getNric().equals(targetNric)) {
                    personToModify = person;
                    break;
                }
            }

            if (personToModify == null) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_NRIC);
            }
        } else if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToModify = lastShownList.get(targetIndex.getZeroBased());
        }

        Appointment appointmentToDelete = null;
        if (personToModify.getAppointment().equals(appointment)) {
            appointmentToDelete = appointment;
        }

        if (appointmentToDelete == null) {
            throw new CommandException(Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
        }

        Person newPerson = new Person(personToModify.getName(),
                personToModify.getAge(),
                personToModify.getGender(),
                personToModify.getNric(),
                personToModify.getPhone(),
                personToModify.getEmail(),
                personToModify.getAddress(),
                new Appointment(null),
                personToModify.getTags());
        model.setPerson(personToModify, newPerson);

        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, Messages.format(personToModify)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteAppointmentCommand)) {
            return false;
        }

        DeleteAppointmentCommand nextCommand = (DeleteAppointmentCommand) other;
        if (targetNric != null && nextCommand.targetNric != null) {
            return targetNric.equals(nextCommand.targetNric) && appointment.equals(nextCommand.appointment);
        } else if (targetNric == null && nextCommand.targetNric == null) {
            // If both NRICs are null, they are considered equal (in case of index-based deletions)
            return targetIndex.equals(nextCommand.targetIndex) && appointment.equals(nextCommand.appointment);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (targetIndex == null) {
            return new ToStringBuilder(this)
                    .add("targetNric", targetNric)
                    .add("appointment", appointment)
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("targetIndex", targetIndex)
                    .add("appointment", appointment)
                    .toString();
        }

    }
}
