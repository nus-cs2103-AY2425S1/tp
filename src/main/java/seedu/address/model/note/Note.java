package seedu.address.model.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.appointment.Appointment;

/**
 * Represents a Note in the address book.
 * Guarantees: immutable;
 *             appointment is valid as declared in Appointment class;
 *             note/medications is valid as declared in {@link #isValidString(String)};
 */
public class Note {
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9. ]*$";
    public static final String MESSAGE_CONSTRAINTS = "Field should only contain alphanumerical characters";
    public static final String MESSAGE_CONSTRAINTS_APPOINTMENT = "Appointments should be in form dd/MM/yyyy HHmm\n"
                                                                 + "Appointments should also be before current time";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    public final Set<Appointment> previousAppointments;
    public final Set<String> remarks;
    public final Set<String> medications;


    /**
     * Constructor for Note class
     */
    public Note() {
        previousAppointments = new HashSet<>();
        remarks = new HashSet<>();
        medications = new HashSet<>();
    }

    /**
     * Constructs a {@code Note} with the specified appointments, remarks, and medications.
     *
     * @param appointments A set of previous appointments.
     * @param remarks A set of remarks.
     * @param medications A set of medications.
     */
    public Note(Set<Appointment> appointments, Set<String> remarks, Set<String> medications) {
        requireNonNull(appointments);
        requireNonNull(remarks);
        requireNonNull(medications);

        this.previousAppointments = appointments;
        this.remarks = remarks;
        this.medications = medications;
    }

    /**
     * Adds a new appointment to the previousAppointment hashset. Validation is done
     * by Appointment class
     *
     * @param appointment A valid date
     */
    public void addAppointment(String appointment) {
        checkArgument(isValidAppointment(appointment), MESSAGE_CONSTRAINTS);
        checkArgument(isValidAppointment(appointment), MESSAGE_CONSTRAINTS_APPOINTMENT);
        Appointment previousAppointment = new Appointment(appointment);
        this.previousAppointments.add(previousAppointment);
    }

    /**
     * Adds new remark to remarks hashset
     *
     * @param remark A valid remark
     */
    public void addRemark(String remark) {
        requireNonNull(remark);
        checkArgument(isValidString(remark), MESSAGE_CONSTRAINTS);
        this.remarks.add(remark);
    }

    /**
     * Adds new medications to medications hashset
     *
     * @param medication A valid medications
     */
    public void addMedication(String medication) {
        requireNonNull(medication);
        checkArgument(isValidString(medication), MESSAGE_CONSTRAINTS);
        this.medications.add(medication);
    }

    public static boolean isValidString(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    /**
     * Ensures appointment is in proper format and is before current date
     *
     * @param test Appointment string to be tested
     */
    public static boolean isValidAppointment(String test) {
        try {
            LocalDateTime.parse(test, FORMATTER);
            return LocalDateTime.parse(test, FORMATTER).isBefore(LocalDateTime.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if both remarks have the same data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Note)) {
            return false;
        }

        Note otherNote = (Note) other;
        return (previousAppointments.stream().sorted().toList())
                       .equals(otherNote.previousAppointments.stream().sorted().toList())
               && medications.equals(otherNote.medications)
               && remarks.equals(otherNote.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(previousAppointments, remarks, medications);
    }

    @Override
    public String toString() {
        if (previousAppointments.isEmpty() && remarks.isEmpty() && medications.isEmpty()) {
            return "This note is currently empty.";
        }
        String appointment = "Previous Appointments: "
            + String.join(", ", previousAppointments.stream().map(Appointment::toString).toList()) + "\n";
        String medication = "Medications: " + String.join(", ", this.medications) + "\n";
        String remarks = "Remarks: " + String.join(", ", this.remarks);

        return appointment + medication + remarks;
    }
}
