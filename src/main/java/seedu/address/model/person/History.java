package seedu.address.model.person;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import seedu.address.commons.exceptions.AppNotFoundException;

/**
 * Represents a medical history entry associated with a specific patient.
 * This class stores appointment date references in a list, and all appointment details
 * are stored in a shared appointment database across all patients.
 */
public class History {

    // Static shared appointmentDatabase, initialized only once
    private static TreeMap<LocalDateTime, Appointment<Id, String>> appointmentDatabase;

    // List of appointment date times specific to this patient's history
    private final ArrayList<LocalDateTime> appointments;

    /**
     * Constructs a History object to keep track of the appointment history of a single patient.
     * Initializes the static appointmentDatabase if this is the first instance.
     */
    public History() {
        // Initialize the static appointmentDatabase if it has not been initialized yet
        if (appointmentDatabase == null) {
            appointmentDatabase = new TreeMap<>();
        }
        appointments = new ArrayList<>();
    }

    /**
     * Adds an appointment to the appointment database and the specific patient's history.
     *
     * @param date     Date and time of the appointment.
     * @param doctorId ID of the doctor.
     * @param patientId ID of the patient.
     * @param remark   Remarks for the appointment.
     */
    public boolean addAppointment(LocalDateTime date, Id doctorId, Id patientId, String remark) {
        // Check if there is already an appointment at the same date
        if (appointmentDatabase.containsKey(date)) {
            Appointment<Id, String> existingAppointment =
                    appointmentDatabase.get(date);
            // Check if the patientId and doctorId match the existing appointment
            if (existingAppointment.getPatientId().equals(patientId)
                    && existingAppointment.getDoctorId().equals(doctorId)) {
                return false; // Duplicate found, return false
            }
        }

        // Proceed with adding the new appointment if no duplicate is found
        Appointment<Id, String> appointment =
                new Appointment<>(patientId, doctorId, remark);
        appointmentDatabase.put(date, appointment); // Store in shared database
        appointments.add(date); // Store reference in this patient's history
        return true;
    }

    /**
     * Deletes an appointment from the appointment database and the specific patient's history.
     *
     * @param date      The date and time of the appointment.
     * @param patientId The patient ID associated with the appointment.
     * @param doctorId  The doctor ID associated with the appointment.
     */
    public boolean deleteAppointment(LocalDateTime date, Id patientId, Id doctorId) {
        // Check if the appointment exists for the given date
        Appointment<Id, String> appointment =
                appointmentDatabase.get(date);

        if (appointment == null) {
            return false;
        }

        // Check if both patientId and doctorId match the retrieved appointment
        if (appointment.getPatientId().equals(patientId)
                && appointment.getDoctorId().equals(doctorId)) {
            // Check if there is a duplicate entry in the appointments list
            if (Collections.frequency(appointments, date) > 1) {
                return false; // Duplicate found, return false
            }

            // Proceed with deleting the appointment
            appointmentDatabase.remove(date); // Remove from the shared database
            appointments.remove(date); // Remove from the patient's appointment history
        } else {
            return false;
        }

        return true;
    }

    /**
     * Returns the details of a single appointment for this patient.
     * Also checks that the patientId and doctorId match.
     *
     * @param date Date and time of the appointment.
     * @param patientId The patient ID.
     * @param doctorId The doctor ID.
     * @return The appointment instance which includes patientId, doctorId, and remarks.
     * @throws AppNotFoundException if no appointment is found for the given date and IDs.
     */
    public Appointment<Id, String> getOneAppointmentDetail(LocalDateTime date,
                                                           Id patientId, Id doctorId)
            throws AppNotFoundException {
        if (!appointments.contains(date)) {
            throw new AppNotFoundException(
                    "No appointment found in this patient's history for the given date.");
        }

        Appointment<Id, String> appointment = appointmentDatabase.get(date);
        if (appointment == null
                || !appointment.getPatientId().equals(patientId)
                || !appointment.getDoctorId().equals(doctorId)) {
            throw new AppNotFoundException(
                    "No such appointment found for the given patient and doctor.");
        }
        return appointment;
    }

    /**
     * Retrieves all appointments associated with this patient's history.
     * Also filters by patientId.
     *
     * @param patientId The patient ID.
     * @return A String with all appointments for this patient.
     */
    public String getAllPatientsAppointments(Id patientId) {
        StringBuilder result = new StringBuilder(
                "These are all the appointments this patient has:\n");

        // Look up each appointment in the shared appointmentDatabase using the patient's specific appointment dates
        for (LocalDateTime date : appointments) {
            Appointment<Id, String> appointment = appointmentDatabase.get(date);
            if (appointment.getPatientId().equals(patientId)) {
                result.append(formatAppointment(date, appointment)).append("\n");
            }
        }

        return result.toString();
    }

    /**
     * Retrieves all appointments associated with this patient's history for a specific doctor.
     *
     * @param doctorId The doctor ID.
     * @return A String with all appointments for this doctor.
     */
    public static String getAllAppointments(Id doctorId) {

        StringBuilder sb = new StringBuilder();
        sb.append("All appointments for you in the database:\n");

        // Loop through all entries in the appointmentDatabase
        for (Map.Entry<LocalDateTime, Appointment<Id, String>> entry
                : appointmentDatabase.entrySet()) {
            Id checkId = entry.getValue().getDoctorId();
            if (checkId.equals(doctorId)) {
                LocalDateTime date = entry.getKey();
                Appointment<Id, String> appointment = entry.getValue();
                sb.append(formatAppointment(date, appointment)).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Retrieves all appointments for this patient on a specific date, optionally filtering by doctor.
     *
     * @param date The date for which to retrieve appointments.
     * @param patientId The patient ID.
     * @param doctorId The doctor ID.
     * @return A list of appointments on the specified date.
     * @throws AppNotFoundException if no appointments are found on the given date.
     */
    public ArrayList<Appointment<Id, String>> getAppointmentsForDay(LocalDate date,
                                                                    int patientId, int doctorId)
            throws AppNotFoundException {
        ArrayList<Appointment<Id, String>> appointmentsForDay = new ArrayList<>();

        // Iterate through this patient's specific appointments and check the date and IDs
        for (LocalDateTime appointmentDateTime : appointments) {
            Appointment<Id, String> appointment = appointmentDatabase.get(appointmentDateTime);
            if (appointmentDateTime.toLocalDate().equals(date)
                    && appointment.getPatientId().equals(patientId)
                    && (appointment.getDoctorId().equals(doctorId))) {
                appointmentsForDay.add(appointment);
            }
        }

        if (appointmentsForDay.isEmpty()) {
            throw new AppNotFoundException(
                    "No appointments found for the specified date.");
        }

        return appointmentsForDay;
    }

    /**
     * Returns the shared appointment database across all patients.
     *
     * @return The static appointmentDatabase.
     */
    public static TreeMap<LocalDateTime, Appointment<Id, String>> getAppointmentDatabase() {
        return appointmentDatabase;
    }

    /**
     * Formats an appointment as a string with the relevant details.
     *
     * @param dateTime The date and time of the appointment.
     * @param appointment The appointment object containing doctorId, patientId, and remarks.
     * @return A formatted string representing the appointment.
     */
    private static String formatAppointment(LocalDateTime dateTime,
                                            Appointment<Id, String> appointment) {
        return String.format("Appointment on %s: Doctor ID = %s, Patient ID = %s",
                dateTime.toString(),
                appointment.getDoctorId().toString(),
                appointment.getPatientId().toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("All appointments in the database:\n");

        // Loop through all entries in the appointmentDatabase
        for (Map.Entry<LocalDateTime, Appointment<Id, String>> entry
                : appointmentDatabase.entrySet()) {
            LocalDateTime date = entry.getKey();
            Appointment<Id, String> appointment = entry.getValue();
            sb.append(formatAppointment(date, appointment)).append("\n");
        }

        return sb.toString();
    }
}


