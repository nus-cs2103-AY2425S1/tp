package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Edits an appointment from the person identified by the patient's NRIC number
 */
public class EditAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "editapp";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an appointment from the person identified "
            + "By the patient's NRIC number, date and start time"
            + "\nParameters: "
            + PREFIX_FIND_NRIC + "PATIENT_NRIC\n"
            + PREFIX_FIND_DATE + "DATE (DD/MM/YYYY) \n"
            + PREFIX_FIND_START_TIME + "START_TIME (HH:MM) \n"
            + PREFIX_NRIC + "NEW_PATIENT_NRIC\n"
            + PREFIX_DATE + "NEW_DATE (DD/MM/YYYY) \n"
            + PREFIX_START_TIME + "NEW_START_TIME (HH:MM) \n"
            + PREFIX_END_TIME + "NEW_END_TIME (HH:MM) \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FIND_NRIC + "S1234567A "
            + PREFIX_FIND_DATE + "01/01/2025 "
            + PREFIX_FIND_START_TIME + "10:00"
            + PREFIX_NRIC + "S7654321B "
            + PREFIX_DATE + "02/02/2025 "
            + PREFIX_START_TIME + "11:00 "
            + PREFIX_END_TIME + "12:00";
    public static final String MESSAGE_SUCCESS = "Edited appointment: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Incorrect NRIC. Person not found";
    public static final String MESSAGE_INVALID_DATE = "Invalid date. Please use the DD/MM/YYYY format";
    public static final String MESSAGE_INVALID_TIME = "Invalid time. Please use the HH:MM format";
    public static final String MESSAGE_NO_APPOINTMENT = "This appointment does not exist in CareLink";
    private final Nric findPatientNric;
    private final LocalDateTime findStartDateTime;
    private final EditAppointmentDescriptor editAppointmentDescriptor;

    /**
     * Constructs a EditAppointmentCommand object
     * @param findPatientNric
     * @param findStartDateTime
     * @param editAppointmentDescriptor
     */
    public EditAppointmentCommand(Nric findPatientNric, LocalDateTime findStartDateTime,
            EditAppointmentDescriptor editAppointmentDescriptor) {
        requireAllNonNull(findPatientNric, findStartDateTime, editAppointmentDescriptor);
        this.findPatientNric = findPatientNric;
        this.findStartDateTime = findStartDateTime;
        this.editAppointmentDescriptor = editAppointmentDescriptor;
    }

    /**
     * Executes the command to edit an appointment for the person with the given NRIC.
     * @param model the model to execute the command on
     * @return the command result
     * @throws CommandException if the person is not found or the appointment does not exist
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person patient = model.getPerson(findPatientNric);

        Appointment appointmentToEdit = model.getAppointmentForPersonAndTime(patient,
                findStartDateTime);
        Appointment editedAppointment = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor);

        if (!appointmentToEdit.isSameAppointment(editedAppointment) && model.hasAppointment(editedAppointment)) {
            throw new CommandException(MESSAGE_NO_APPOINTMENT);
        }

        model.editAppointment(appointmentToEdit, patient, editedAppointment);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // model.deleteAppointment(patient, appointment);
        // model.addAppointment(patient, appointment);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedAppointment));
    }

    /**
     * Creates and returns a {@code Appointment} with the details of {@code appointmentToEdit}
     * edited with {@code editAppointmentDescriptor}.
     */

    private static Appointment createEditedAppointment(Appointment appointmentToEdit,
            EditAppointmentDescriptor editAppointmentDescriptor) {
        assert appointmentToEdit != null;

        String updatedName = editAppointmentDescriptor.getName().orElse(appointmentToEdit.getName());
        Nric updatedNric = editAppointmentDescriptor.getNric().orElse(appointmentToEdit.getNric());
        LocalDateTime updatedStartTime = editAppointmentDescriptor.getStartTime()
                .orElse(appointmentToEdit.getStartTime());
        LocalDateTime updatedEndTime = editAppointmentDescriptor.getEndTime()
                .orElse(appointmentToEdit.getEndTime());

        return new Appointment(updatedName, updatedNric, updatedStartTime, updatedEndTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAppointmentCommand)) {
            return false;
        }

        EditAppointmentCommand otherEditAppointmentCommand = (EditAppointmentCommand) other;
        return findPatientNric.equals(otherEditAppointmentCommand.findPatientNric)
                && editAppointmentDescriptor.equals(otherEditAppointmentCommand.editAppointmentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nric", findPatientNric)
                .add("editAppointmentDescriptor", editAppointmentDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the appointment with. Each non-empty field value will
     * replace the
     * corresponding field value of the appointment.
     */
    public static class EditAppointmentDescriptor {
        private String name;
        private Nric nric;
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public EditAppointmentDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            setName(toCopy.name);
            setNric(toCopy.nric);
            setStartTime(toCopy.startTime);
            setEndTime(toCopy.endTime);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, nric, startTime, endTime);
        }

        public void setName(String name) {
            this.name = name;
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setStartTime(LocalDateTime startDateTime) {
            this.startTime = startDateTime;
        }

        public Optional<LocalDateTime> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        public void setEndTime(LocalDateTime endDateTime) {
            this.endTime = endDateTime;
        }

        public Optional<LocalDateTime> getEndTime() {
            return Optional.ofNullable(endTime);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppointmentDescriptor)) {
                return false;
            }

            EditAppointmentDescriptor otherEditPersonDescriptor = (EditAppointmentDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(nric, otherEditPersonDescriptor.nric)
                    && Objects.equals(startTime, otherEditPersonDescriptor.startTime)
                    && Objects.equals(endTime, otherEditPersonDescriptor.endTime);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("nric", nric)
                    .add("startTime", startTime)
                    .add("endTime", endTime)
                    .toString();
        }
    }
}
