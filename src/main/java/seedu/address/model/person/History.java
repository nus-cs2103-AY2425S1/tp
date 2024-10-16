package seedu.address.model.person;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import seedu.address.commons.exceptions.AppNotFoundException;

/**
 * Represents a medical history entry associated with a doctor.
 * This class stores details of the appointments or medical interactions
 * for a patient, including the doctor, date, and remarks.
 */
public class History {

    /**
     * Static database for all appointments sorted by LocalDateTime.
     * It contains appointments shared across all patients.
     */
    private static TreeMap<LocalDateTime, Appointment> appointmentDatabase;

    /**
     * List of appointment dates (LocalDateTime) for this instance of a person.
     */
    private final ArrayList<LocalDateTime> appointments;

    /**
     * Default constructor for History class.
     * Initializes the appointment database and the patient's list of appointments.
     */
    public History() {
        // Initialize the static appointmentDatabase if it has not been initialized yet
        if (appointmentDatabase == null) {
            appointmentDatabase = new TreeMap<>();
        }
        appointments = new ArrayList<>();
    }

    /**
     * Constructs a History object for a specific patient to track their appointments.
     * Sets a default remark of "no remark" for the appointment.
     *
     * @param date The date and time of the appointment.
     * @param patientId The ID of the patient for the appointment.
     * @param doctorId The ID of the doctor for the appointment.
     */
    public History(LocalDateTime date, Id patientId, Id doctorId) {
        this();
        addAppointment(date, patientId, doctorId, "no remark");
    }

    /**
     * Constructs a History object for a specific patient to track their appointments.
     * Allows for a remark to be set for the appointment.
     *
     * @param date The date and time of the appointment.
     * @param patientId The ID of the patient for the appointment.
     * @param doctorId The ID of the doctor for the appointment.
     * @param remark Remarks provided by the doctor for the appointment.
     */
    public History(LocalDateTime date, Id patientId, Id doctorId, String remark) {
        this();
        addAppointment(date, patientId, doctorId, remark);
    }

    /**
     * Adds a new appointment to the shared appointment database and the patient's history.
     *
     * @param date The date and time of the appointment.
     * @param patientId The ID of the patient.
     * @param doctorId The ID of the doctor.
     * @param remark The remark for the appointment.
     * @return true if the appointment was successfully added, false if a duplicate was found.
     */
    public boolean addAppointment(LocalDateTime date, Id patientId, Id doctorId, String remark) {
        if (appointmentDatabase.containsKey(date)) {
            Appointment existingAppointment = appointmentDatabase.get(date);
            if (existingAppointment.getPatientId().equals(patientId)
                    && existingAppointment.getDoctorId().equals(doctorId)) {
                return false; // Duplicate found
            }
        }
        Appointment appointment = new Appointment(patientId, doctorId, remark);
        appointmentDatabase.put(date, appointment);
        appointments.add(date);
        return true;
    }

    /**
     * Deletes an appointment from the shared appointment database and the patient's history.
     *
     * @param date The date and time of the appointment.
     * @param patientId The ID of the patient.
     * @param doctorId The ID of the doctor.
     * @return true if the appointment was successfully deleted, false if no matching appointment was found.
     */
    public static boolean deleteAppointment(LocalDateTime date, Id patientId, Id doctorId) {
        Appointment appointment = appointmentDatabase.get(date);
        Patient patient = Patient.getPatientWithId(patientId);
        History history = patient.getHistory();
        if (appointment == null || history.checkDuplicateAppointments(date)) {
            return false;
        }
        appointmentDatabase.remove(date);
        history.removeAppointments(date);
        return true;
    }

    /**
     * Removes an appointment from the patient's history.
     *
     * @param date The date and time of the appointment to remove.
     */
    public void removeAppointments(LocalDateTime date) {
        if (appointments.contains(date)) {
            appointments.remove(date);
        }
    }

    /**
     * Checks if the patient has duplicate appointments for the same date.
     *
     * @param date The date to check for duplicates.
     * @return true if duplicate appointments are found, false otherwise.
     */
    public boolean checkDuplicateAppointments(LocalDateTime date) {
        if (Collections.frequency(appointments, date) > 1) {
            return true; // Duplicate found
        }
        return false;
    }

    /**
     * Retrieves the details of a single appointment based on the date, patient, and doctor.
     *
     * @param date The date and time of the appointment.
     * @param patientId The ID of the patient.
     * @param doctorId The ID of the doctor.
     * @return The appointment details.
     * @throws AppNotFoundException if no appointment is found for the given patient and doctor.
     */
    public Appointment getOneAppointmentDetail(LocalDateTime date, Id patientId, Id doctorId)
            throws AppNotFoundException {
        if (!appointments.contains(date)) {
            throw new AppNotFoundException("No appointment found in this patient's history for the given date.");
        }
        Appointment appointment = appointmentDatabase.get(date);
        if (appointment == null || !appointment.getPatientId().equals(patientId)
                || !appointment.getDoctorId().equals(doctorId)) {
            throw new AppNotFoundException("No such appointment found for the given patient and doctor.");
        }
        return appointment;
    }

    /**
     * Retrieves all the appointments for a specific patient.
     *
     * @param patientId The ID of the patient.
     * @return A string listing all the appointments for the patient.
     */
    public String getAllPatientsAppointments(Id patientId) {
        StringBuilder result = new StringBuilder("These are all the appointments this patient has:\n");
        for (LocalDateTime date : appointments) {
            Appointment appointment = appointmentDatabase.get(date);
            if (appointment.getPatientId().equals(patientId)) {
                result.append(formatAppointment(date, appointment)).append("\n");
            }
        }
        return result.toString();
    }

    /**
     * Retrieves all appointments for a specific doctor.
     *
     * @param doctorId The ID of the doctor.
     * @return A string listing all appointments for the doctor.
     */
    public static String getAllAppointments(Id doctorId) {
        StringBuilder sb = new StringBuilder("All appointments for you in the database:\n");
        for (Map.Entry<LocalDateTime, Appointment> entry : appointmentDatabase.entrySet()) {
            Id checkId = entry.getValue().getDoctorId();
            if (checkId.equals(doctorId)) {
                LocalDateTime date = entry.getKey();
                Appointment appointment = entry.getValue();
                sb.append(formatAppointment(date, appointment)).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Retrieves all appointments for a specific day for a patient and doctor.
     *
     * @param date The date to check.
     * @param patientId The ID of the patient.
     * @param doctorId The ID of the doctor.
     * @return A list of appointments on the specified day.
     * @throws AppNotFoundException if no appointments are found for the specified date.
     */
    public ArrayList<Appointment> getAppointmentsForDay(LocalDate date, Id patientId, Id doctorId)
            throws AppNotFoundException {
        ArrayList<Appointment> appointmentsForDay = new ArrayList<>();
        for (LocalDateTime appointmentDateTime : appointments) {
            Appointment appointment = appointmentDatabase.get(appointmentDateTime);
            if (appointmentDateTime.toLocalDate().equals(date)
                    && appointment.getPatientId().equals(patientId)
                    && appointment.getDoctorId().equals(doctorId)) {
                appointmentsForDay.add(appointment);
            }
        }
        if (appointmentsForDay.isEmpty()) {
            throw new AppNotFoundException("No appointments found for the specified date.");
        }
        return appointmentsForDay;
    }

    /**
     * Formats the appointment details for display.
     *
     * @param dateTime The date and time of the appointment.
     * @param appointment The appointment object.
     * @return A formatted string representing the appointment details.
     */
    private static String formatAppointment(LocalDateTime dateTime, Appointment appointment) {
        return String.format("Appointment on %s: Doctor ID = %s, Patient ID = %s",
                dateTime.toString(), appointment.getDoctorId().toString(), appointment.getPatientId().toString());
    }

    /**
     * Returns a string representation of all appointments in the database.
     *
     * @return A string listing all appointments.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("All appointments in the database:\n");
        for (Map.Entry<LocalDateTime, Appointment> entry : appointmentDatabase.entrySet()) {
            LocalDateTime date = entry.getKey();
            Appointment appointment = entry.getValue();
            sb.append(formatAppointment(date, appointment)).append("\n");
        }
        return sb.toString();
    }
}
