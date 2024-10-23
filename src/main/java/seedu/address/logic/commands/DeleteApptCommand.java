package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEPERIOD;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.AppointmentExistsPredicate;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.MedCon;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteApptCommand extends Command {

    public static final String COMMAND_WORD = "delAppt";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Deletes a patient's appointment identified by the NRIC number of the patient.\n"
            + "Parameters: "
            + PREFIX_NRIC + "NRIC (must be a valid NRIC in the system), "
            + PREFIX_DATE + "DATE (of appointment), "
            + PREFIX_TIMEPERIOD + "TIME_PERIOD (in HHMM-HHMM, chronological order).\n"
            + "Example: "
            + COMMAND_WORD
            + PREFIX_NRIC + "S1234567Z "
            + PREFIX_DATE + "2024-10-24 "
            + PREFIX_TIMEPERIOD + "1235-1400";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS_2S = "Deleted Appointment \"%s\" for Patient %s";
    public static final String MESSAGE_PERSON_APPT_NOT_FOUND = "There is no appointment with the given date and time "
                                                               + "period for this person!";
    private static final Logger logger = LogsCenter.getLogger(DeleteApptCommand.class);

    private final NricMatchesPredicate targetNric;
    private final AppointmentExistsPredicate targetAppt;

    /**
     * Constructs the command for delAppt.
     *
     * @param targetNric Predicate for testing existing {@code Nric}.
     * @param targetAppt Predicate for testing existing {@code Appointment}.
     */
    public DeleteApptCommand(NricMatchesPredicate targetNric, AppointmentExistsPredicate targetAppt) {
        requireNonNull(targetNric);
        requireNonNull(targetAppt);
        this.targetNric = targetNric;
        this.targetAppt = targetAppt;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToEdit = model.fetchPersonIfPresent(targetNric)
                                   .orElseThrow(() -> new CommandException(Messages.MESSAGE_PERSON_NRIC_NOT_FOUND));
        Appointment apptToDelete = targetAppt.getApptMatch(personToEdit)
                                             .orElseThrow(() -> new CommandException(MESSAGE_PERSON_APPT_NOT_FOUND));
        Person editedPerson = createEditedPerson(personToEdit, apptToDelete);

        model.setPerson(personToEdit, editedPerson);
        logger.info("delAppt command has been exceuted successfully on a person with NRIC: " + targetNric + " for "
                    + "appointment: " + targetAppt);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS_2S,
                                               apptToDelete,
                                               personToEdit.getNric().value));
    }

    private Person createEditedPerson(Person personToEdit, Appointment apptToDelete) {
        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        DateOfBirth updatedDateOfBirth = personToEdit.getDateOfBirth();
        Gender updatedGender = personToEdit.getGender();
        Nric updatedNric = personToEdit.getNric();
        Priority updatedPriority = personToEdit.getPriority();
        Set<Allergy> updatedAllergies = personToEdit.getAllergies();
        Set<MedCon> updatedMedCons = personToEdit.getMedCons();


        // get list, make mutable, delete, and make immutable
        Set<Appointment> oldAppointmentList = personToEdit.getAppointments();
        ArrayList<Appointment> newAppointmentsMutable = new ArrayList<>(oldAppointmentList);
        newAppointmentsMutable.remove(apptToDelete);
        Set<Appointment> newAppointments = new HashSet<>(newAppointmentsMutable);

        return new Person(updatedName, updatedPhone, updatedEmail, updatedNric, updatedAddress, updatedDateOfBirth,
                          updatedGender, updatedAllergies, updatedPriority, newAppointments, updatedMedCons);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteApptCommand otherDeleteCommand)) {
            return false;
        }

        return targetNric.equals(otherDeleteCommand.targetNric)
               && targetAppt.equals(otherDeleteCommand.targetAppt);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetNric", targetNric)
                .add("targetAppt", targetAppt)
                .toString();
    }
}
