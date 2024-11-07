package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
            + PREFIX_NRIC + "PATIENT_NRIC\n"
            + PREFIX_DATE + "DATE (DD/MM/YYYY) \n"
            + PREFIX_START_TIME + "START_TIME (HH:MM) \n"
            + PREFIX_NEW_DATE + "NEW_DATE (DD/MM/YYYY) \n"
            + PREFIX_NEW_START_TIME + "NEW_START_TIME (HH:MM) \n"
            + PREFIX_NEW_END_TIME + "NEW_END_TIME (HH:MM) \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567D "
            + PREFIX_DATE + "01/01/2025 "
            + PREFIX_START_TIME + "10:00 "
            + PREFIX_NEW_DATE + "02/02/2025 "
            + PREFIX_NEW_START_TIME + "11:00 "
            + PREFIX_NEW_END_TIME + "12:00";
    public static final String MESSAGE_SUCCESS = "Edited appointment: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT =
        "An appointment already exists at the edited date and time";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Incorrect NRIC. Person not found";
    public static final String MESSAGE_INVALID_DATE = "Invalid date. Please use the DD/MM/YYYY format";
    public static final String MESSAGE_INVALID_TIME = "Invalid time. Please use the HH:MM format";
    public static final String MESSAGE_NO_APPOINTMENT = "This appointment does not exist in CareLink";
    public static final String MESSAGE_INVALID_START_END_TIME = "Start time must be before end time";
    public static final String MESSAGE_START_TIME_IN_PAST = "Start time must be in the future";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
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

        if (patient == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        Appointment appointmentToEdit = model.getAppointmentForPersonAndTime(patient,
                findStartDateTime);

        if (appointmentToEdit == null) {
            throw new CommandException(MESSAGE_NO_APPOINTMENT);
        }

        Appointment editedAppointment = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor);

        boolean success = model.editAppointment(appointmentToEdit, patient, editedAppointment);

        if (!success) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedAppointment));
    }

    /**
     * Creates and returns a {@code Appointment} with the details of {@code appointmentToEdit}
     * edited with {@code editAppointmentDescriptor}.
     */

    private static Appointment createEditedAppointment(Appointment appointmentToEdit,
            EditAppointmentDescriptor editAppointmentDescriptor) throws CommandException {
        assert appointmentToEdit != null;

        String name = appointmentToEdit.getName();
        Nric nric = appointmentToEdit.getNric();
        LocalDate date = editAppointmentDescriptor.getDate().orElse(appointmentToEdit.getStartTime().toLocalDate());
        LocalTime startTime = editAppointmentDescriptor.getStartTime()
                .orElse(appointmentToEdit.getStartTime().toLocalTime());
        LocalTime endTime = editAppointmentDescriptor.getEndTime().orElse(appointmentToEdit.getEndTime().toLocalTime());
        LocalDateTime updatedStartTime = LocalDateTime.of(date, startTime);
        LocalDateTime updatedEndTime = LocalDateTime.of(date, endTime);

        if (!updatedStartTime.isBefore(updatedEndTime)) {
            throw new CommandException(MESSAGE_INVALID_START_END_TIME);
        }

        if (updatedStartTime.isBefore(LocalDateTime.now())) {
            throw new CommandException(MESSAGE_START_TIME_IN_PAST);
        }

        return new Appointment(name, nric, updatedStartTime, updatedEndTime);
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
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;

        public EditAppointmentDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            setDate(toCopy.date);
            setStartTime(toCopy.startTime);
            setEndTime(toCopy.endTime);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(date, startTime, endTime);
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }

        public Optional<LocalTime> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        public void setEndTime(LocalTime endTime) {
            this.endTime = endTime;
        }

        public Optional<LocalTime> getEndTime() {
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

            EditAppointmentDescriptor otherEditAppointmentDescriptor = (EditAppointmentDescriptor) other;
            return Objects.equals(date, otherEditAppointmentDescriptor.date)
                    && Objects.equals(startTime, otherEditAppointmentDescriptor.startTime)
                    && Objects.equals(endTime, otherEditAppointmentDescriptor.endTime);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("date", date)
                    .add("startTime", startTime)
                    .add("endTime", endTime)
                    .toString();
        }
    }
}
