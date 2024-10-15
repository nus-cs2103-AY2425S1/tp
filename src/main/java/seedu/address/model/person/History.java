package seedu.address.model.person;

import java.time.LocalDateTime;
import java.util.TreeMap;

import seedu.address.commons.exceptions.AppNotFoundException;

/**
 * Represents a medical history entry associated with a doctor.
 * This class will store details of the appointments or medical interactions.
 */
public class History {
    private TreeMap<LocalDateTime, TreeMap<Integer, String>> appointmentHistory;

    /**
     * Placeholder default constructor TODO.
     */
    public History() {
    }

    /**
     * Constructs a History object to keep track the appointment history of a single patient.
     * @param date date of the appointment
     * @param doctorId ID of the doctor that the patient is under
     * @param remark some remarks that the doctor inputs for the appointment session including
     *               treatment, condition and many others
     */
    public History(LocalDateTime date, int doctorId, String remark) {
        TreeMap<Integer, String> appointmentDetails = new TreeMap<>();
        appointmentDetails.put(doctorId, remark);
        this.appointmentHistory = new TreeMap<>();
        appointmentHistory.put(date, appointmentDetails);
    }

    /**
     * Constructs a History object to keep track the appointment history of a single patient.
     * Remark is set to null by default
     * @param date date of the appointment
     * @param doctorId ID of the doctor that the patient is under
     */
    public History(LocalDateTime date, int doctorId) {
        TreeMap<Integer, String> appointmentDetails = new TreeMap<>();
        appointmentDetails.put(doctorId, null);
        this.appointmentHistory = new TreeMap<>();
        appointmentHistory.put(date, appointmentDetails);
    }

    /**
     * returns the detail of one appointment
     * @param date date of the appointment
     * @param doctorId the doctor that the appointment is scheduled under
     *
     * @return all remarks of that appointment which could include the patient's condition and treatment.
     * @throws AppNotFoundException if the appointment with the given details cannot be found.
     */
    public String getOneAppointmentDetail(LocalDateTime date, int doctorId) throws AppNotFoundException {
        TreeMap<Integer, String> appointmentDetails = appointmentHistory.get(date);
        if (appointmentDetails == null || !appointmentDetails.containsKey(doctorId)) {
            throw new AppNotFoundException("No such appointment is found.");
        }
        return appointmentDetails.get(doctorId);
    }

    /**
     * returns the entire appointment history of a patient
     *
     * @return all the past appointments of a patient
     */
    public TreeMap<LocalDateTime, TreeMap<Integer, String>> getAllAppointment() {
        return new TreeMap<>(appointmentHistory);
    }

    // Nigel's code suggestion

    /**
     * Adds an appointment to the database with the specified content.
     *
     * @param dateTime Date & time of the appointment.
     * @param patientId Id of patient in the appointment.
     * @param doctorId Id of doctor in the appointment.
     * @return True if appointment was successfully added, false if otherwise.
     */
    public static boolean addAppointment(LocalDateTime dateTime, Id patientId, Id doctorId, String remarks) {
        // TODO something new Appointment(dateTime, patientId, doctorId, remarks)
        // Other notes: check for duplicate appointments / clashing timeslots w doctor & patient
        // What to throw if got error
        return false;
    }

    /**
     * Deletes the specified appointment with the respective details.
     * @param dateTime Date & time of the appointment to delete.
     * @param patientId Id of the patient in the appointment.
     * @param doctorId Id of doctor in the appointment.
     * @return True if appointment was successfully deleted, false if otherwise.
     */
    public static boolean deleteAppointment(LocalDateTime dateTime, Id patientId, Id doctorId) {
        // TODO something
        return false;
    }

    /**
     * Returns a String (or not String) representing all appointments related to that user id.
     *
     * @param id Id of Person to get appointments of.
     * @return String representing all appointments related to the user id provided.
     */
    public static String getAllAppointments(Id id) {
        // TODO
        return "WIP";
    }

}
