package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Time;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.shared.Date;

/**
 * Adds an appointment to an existing person in the address book.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "add-appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an appointment to the patient and doctor identified "
            + "at the specified date and time.\n"
            + "Parameters: "
            + PREFIX_PATIENT_NAME + "PATIENT NAME "
            + PREFIX_DOCTOR_NAME + "DOCTOR NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATIENT_NAME + "John Doe "
            + PREFIX_DOCTOR_NAME + "Jane Doe "
            + PREFIX_DATE + "23-09-2024 "
            + PREFIX_TIME + "1100";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the address book";

    private final Name patientName;
    private final Name doctorName;
    private final Date date;
    private final Time time;
    private Appointment appointmentToAdd;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(Name patientName, Name doctorName, Date date, Time time) {
        requireAllNonNull(patientName, doctorName, date, time);
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Optional<Person> patient = lastShownList.stream()
                .filter(person -> person instanceof Patient && person.getName().equals(patientName))
                .findAny();
        Optional<Person> doctor = lastShownList.stream()
                .filter(person -> person instanceof Doctor && person.getName().equals(doctorName))
                .findAny();

        Person patientToEdit = patient.get();
        Person doctorToEdit = doctor.get();
        this.appointmentToAdd = new Appointment((Doctor) doctorToEdit, (Patient) patientToEdit, this.date, this.time);

        Person editedPatient;
        Person editedDoctor;

        if (model.hasAppointment(appointmentToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        editedPatient = patientToEdit;
        editedDoctor = doctorToEdit;
        model.setPerson(patientToEdit, editedPatient);
        model.setPerson(doctorToEdit, editedDoctor);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(appointmentToAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAppointmentCommand)) {
            return false;
        }

        AddAppointmentCommand otherAddAppointmentCommand = (AddAppointmentCommand) other;
        boolean patientNameCheck = this.patientName.equals(otherAddAppointmentCommand.patientName);
        boolean doctorNameCheck = this.doctorName.equals(otherAddAppointmentCommand.doctorName);
        boolean dateCheck = this.date.equals(otherAddAppointmentCommand.date);
        boolean timeCheck = this.time.equals(otherAddAppointmentCommand.time);
        return patientNameCheck && doctorNameCheck && dateCheck && timeCheck;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("appointmentToAdd", appointmentToAdd)
                .toString();
    }
}
