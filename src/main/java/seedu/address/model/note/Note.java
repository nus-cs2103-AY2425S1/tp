package seedu.address.model.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.appointment.Appointment;

/**
 * Represents a Note in the address book.
 * Guarantees: immutable;
 *             appointment is valid as declared in Appointment class;
 *             note/medication is valid as declared in {@link #isValidString(String)};
 */
public class Note {
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9. ]*$";
    public static final String MESSAGE_CONSTRAINTS = "Field should only contain alphanumerical characters";
    public final Set<Appointment> previousAppointments;
    public final Set<String> remarks;
    public final Set<String> medication;


    /**
     * Constructor for Note class
     */
    public Note() {
        previousAppointments = new HashSet<>();
        remarks = new HashSet<>();
        medication = new HashSet<>();
    }

    /**
     * Constructs a {@code Note} with the specified appointments, remarks, and medication.
     *
     * @param appointments A set of previous appointments.
     * @param remarks A set of remarks.
     * @param medication A set of medications.
     */
    public Note(Set<Appointment> appointments, Set<String> remarks, Set<String> medication) {
        requireNonNull(appointments);
        requireNonNull(remarks);
        requireNonNull(medication);

        this.previousAppointments = appointments;
        this.remarks = remarks;
        this.medication = medication;
    }

    /**
     * Adds a new appointment to the previousAppointment hashset. Validation is done
     * by Appointment class
     *
     * @param appointment A valid date
     */
    public void addAppointment(String appointment) {
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
     * Adds new medication to medications hashset
     *
     * @param medication A valid medication
     */
    public void addMedication(String medication) {
        requireNonNull(medication);
        checkArgument(isValidString(medication), MESSAGE_CONSTRAINTS);
        this.medication.add(medication);
    }

    public static boolean isValidString(String test) {
        return test.matches(VALIDATION_REGEX) && !test.isEmpty();
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
               && medication.equals(otherNote.medication)
               && remarks.equals(otherNote.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(previousAppointments, remarks, medication);
    }

    @Override
    public String toString() {
        String appointment = "Previous Appointments: "
            + String.join(", ", previousAppointments.stream().map(Appointment::toString).toList()) + "\n";
        String medication = "Medications: " + String.join(", ", this.medication) + "\n";
        String remarks = "Remarks: " + String.join(", ", this.remarks);

        return appointment + medication + remarks;
    }
}
