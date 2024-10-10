package seedu.address.model.person;

import java.time.LocalDateTime;
import java.util.TreeMap;

/**
 * Represents a medical history entry associated with a doctor.
 * This class will store details of the appointments or medical interactions.
 */
public class History {
    private TreeMap<LocalDateTime, TreeMap<Integer, String>> appointmentHistory;

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
     */
    public String getOneAppointmentDetail(LocalDateTime date, int doctorId) {
        return appointmentHistory.get(date).get(doctorId);
    }

    /**
     * returns the entire appointment history of a patient
     *
     * @return all the past appointments of a patient
     */
    public TreeMap<LocalDateTime, TreeMap<Integer, String>> getAllAppointment() {
        return appointmentHistory;
    }
}
