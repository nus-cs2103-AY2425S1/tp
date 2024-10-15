package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.model.tag.Tag;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {

    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param remark
     * @param tags
     */
    public Patient(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
    }

    /**
     * Adds a new appointment at the specified time, for the respective patient and doctor.
     *
     * @param dateTime  Time of appointment.
     * @param patientId Id of patient in the appointment.
     * @param doctorId  Id of doctor in the appointment.
     * @return True if command was successful, false if otherwise.
     */
    @Override
    public boolean addAppointment(LocalDateTime dateTime, Id patientId, Id doctorId, String remarks) {
        requireNonNull(dateTime);
        requireNonNull(patientId);
        requireNonNull(doctorId);
        requireNonNull(remarks);
        return History.addAppointment(dateTime, patientId, doctorId, remarks);
    }

    /**
     * Delete an appointment at the specified time, with the respective patient and doctor.
     *
     * @param dateTime  Time of appointment.
     * @param patientId Id of patient in the appointment.
     * @param doctorId  Id of doctor in the appointment.
     * @return True if command was successful, false if otherwise.
     */
    @Override
    public boolean deleteAppointment(LocalDateTime dateTime, Id patientId, Id doctorId) {
        return History.deleteAppointment(dateTime, patientId, doctorId);
    }

    /**
     * Gets an appointment at the specified time, with the respective patient and doctor.
     *
     * @param dateTime  Time of appointment.
     * @param patientId Id of patient in the appointment.
     * @param doctorId  Id of doctor in the appointment.
     * @return True if command was successful, false if otherwise.
     */
    @Override
    public Appointment getAppointment(LocalDateTime dateTime, Id patientId, Id doctorId) {
        // TODO AFTER v1.3
        return null;
    }

    /**
     * Returns all appointments of a specified Id.
     *
     * @param id Patient Id to be specified.
     * @return String representing all appointments of the Patient.
     */
    public String getAllAppointments(Id id) {
        return History.getAllAppointments(id);
    }
}
