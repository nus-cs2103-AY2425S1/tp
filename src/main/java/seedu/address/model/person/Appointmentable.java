package seedu.address.model.person;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;



/**
 * Implemented by classes who have appointment-related functionality.
 */
public interface Appointmentable {

    /**
     * Adds a new appointment at the specified time, for the respective patient and doctor.
     *
     * @param dateTime  Time of appointment.
     * @param patientId Id of patient in the appointment.
     * @param doctorId  Id of doctor in the appointment.
     * @return True if command was successful, false if otherwise.
     */
    public abstract boolean addAppointment(LocalDateTime dateTime, int patientId, int doctorId, String remarks);

    /**
     * Edit an appointment at the specified time, with the respective patient and doctor.
     *
     * @param dateTime  Time of appointment.
     * @param patientId Id of patient in the appointment.
     * @param doctorId  Id of doctor in the appointment.
     * @return True if command was successful, false if otherwise.
     */
    public abstract boolean editAppointment(LocalDateTime dateTime, int patientId, int doctorId);

    /**
     * Delete an appointment at the specified time, with the respective patient and doctor.
     *
     * @param dateTime  Time of appointment.
     * @param patientId Id of patient in the appointment.
     * @param doctorId  Id of doctor in the appointment.
     * @return True if command was successful, false if otherwise.
     */
    public abstract boolean deleteAppointment(LocalDateTime dateTime, int patientId, int doctorId);

    /**
     * Gets an appointment at the specified time, with the respective patient and doctor.
     *
     * @param dateTime  Time of appointment.
     * @param patientId Id of patient in the appointment.
     * @param doctorId  Id of doctor in the appointment.
     * @return True if command was successful, false if otherwise.
     */
    public abstract Appointment getAppointment(LocalDateTime dateTime, int patientId, int doctorId)
            throws CommandException;

    /**
     * Marks an appointment at the specified time, with the respective patient and doctor.
     *
     * @param dateTime  Time of appointment.
     * @param patientId Id of patient in the appointment.
     * @param doctorId  Id of doctor in the appointment.
     */
    public abstract boolean markAppointment(LocalDateTime dateTime, int patientId, int doctorId);
}
