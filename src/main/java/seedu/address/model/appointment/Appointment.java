package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Hashtable;
import java.util.Objects;
import java.util.Random;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.shared.Date;

/**
 * Represents an Appointment in the contacts book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

    // Hashtable of ID => appointment
    private static Hashtable<Integer, Appointment> appointmentById = new Hashtable<>();

    // ID of the appointment
    private final Integer id;

    // Data fields
    private final Doctor doctor;
    private final Patient patient;
    private final Date date;
    private final Time time;

    /**
     * Constructor with all fields.
     * Every field must be present and not null.
     */
    public Appointment(Integer id, Doctor doctor, Patient patient, Date date, Time time) {
        requireAllNonNull(id, doctor, patient, date, time);

        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.time = time;

        doctor.addAppointment(this);
        patient.addAppointment(this);

        appointmentById.put(id, this);
    }

    /**
     * Constructor with only data fields.
     * Automatically assigns a new random unique ID.
     */
    public Appointment(Doctor doctor, Patient patient, Date date, Time time) {
        this(getNewId(), doctor, patient, date, time);
    }

    private static Integer getNewId() {
        Random r = new Random();
        int newId;
        do {
            newId = r.nextInt();
        } while (appointmentById.containsKey(newId));

        return newId;
    }

    /**
     * Delete an appointment based on its ID.
     * @param id ID of the appointment to delete.
     * @return Whether the ID exists.
     */
    public static boolean deleteAppointmentById(Integer id) {
        if (!appointmentById.containsKey(id)) {
            return false;
        }

        Appointment appointment = appointmentById.get(id);
        appointment.doctor.removeAppointment(appointment);
        appointment.patient.removeAppointment(appointment);

        appointmentById.remove(id);

        return true;
    }

    /**
     * Retrieves an appointment by its ID.
     * @param id The ID of the appointment to retrieve.
     * @return The appointment with the given ID, or null if it doesn't exist.
     */
    public static Appointment getAppointmentById(Integer id) {
        return appointmentById.get(id); // returns null if ID doesn't exist
    }

    public Integer getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getDoctorName() {
        return doctor.getName().toString();
    }

    public Patient getPatient() {
        return patient;
    }

    public String getPatientName() {
        return patient.getName().toString();
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    /**
     * Returns true if both appointments have the same ID.
     * This defines a weaker notion of equality between two appointments.
     */
    public boolean isSameAppointment(Appointment otherAppointment) {
        if (otherAppointment == this) {
            return true;
        }

        return otherAppointment != null
                && getId().equals(otherAppointment.getId());
    }

    /**
     *  Returns true if all fields in both appointments are the same.
     * This defines a stronger notion of equality between two appointments.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return getId().equals(otherAppointment.getId())
                && doctor.equals(otherAppointment.doctor)
                && patient.equals(otherAppointment.patient)
                && date.equals(otherAppointment.date)
                && time.equals(otherAppointment.time);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(doctor, patient, date, time);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", getId())
                .add("doctor", doctor)
                .add("patient", patient)
                .add("date", date)
                .add("time", time)
                .toString();
    }
}
