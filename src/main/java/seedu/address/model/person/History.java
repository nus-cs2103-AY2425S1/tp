//package seedu.address.model.person;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Map;
//import java.util.TreeMap;
//
//import seedu.address.commons.exceptions.AppNotFoundException;
//
///**
// * Represents a medical history entry associated with a doctor.
// * This class stores details of the appointments or medical interactions
// * for a patient, including the doctor, date, and remarks.
// */
//public class History {
//
//    /**
//     * Static database for all appointments sorted by LocalDateTime.
//     * It contains appointments shared across all patients.
//     */
//    private static TreeMap<LocalDateTime, Appointment> appointmentDatabase;
//
//    /**
//     * List of appointment dates (LocalDateTime) for this instance of a person.
//     */
//    private final ArrayList<LocalDateTime> appointments;
//
//    /**
//     * Default constructor for History class.
//     * Initializes the appointment database and the patient's list of appointments.
//     */
//    public History() {
//        // Initialize the static appointmentDatabase if it has not been initialized yet
//        if (appointmentDatabase == null) {
//            appointmentDatabase = new TreeMap<>();
//        }
//        appointments = new ArrayList<>();
//    }
//
//    /**
//     * Constructs a History object for a specific patient to track their appointments.
//     * Sets a default remark of "no remark" for the appointment.
//     *
//     * @param date The date and time of the appointment.
//     * @param patientId The ID of the patient for the appointment.
//     * @param doctorId The ID of the doctor for the appointment.
//     */
//    public History(LocalDateTime date, Id patientId, Id doctorId) {
//        this();
//        addAppointment(date, patientId, doctorId, "no remark");
//    }
//
//    /**
//     * Constructs a History object for a specific patient to track their appointments.
//     * Allows for a remark to be set for the appointment.
//     *
//     * @param date The date and time of the appointment.
//     * @param patientId The ID of the patient for the appointment.
//     * @param doctorId The ID of the doctor for the appointment.
//     * @param remark Remarks provided by the doctor for the appointment.
//     */
//    public History(LocalDateTime date, Id patientId, Id doctorId, String remark) {
//        this();
//        addAppointment(date, patientId, doctorId, remark);
//    }
//
////    /**
////     * Retrieves a formatted string of all appointments in the database.
////     * The string includes details of each appointment such as the date and
////     * any associated information stored in the Appointment object.
////     *
////     * @return A string representation of all appointments in the database.
////     */
////    public static String getHistoryDataBase() {
////        StringBuilder sb = new StringBuilder("All appointments in the database:\n");
////        for (Map.Entry<LocalDateTime, Appointment> entry : appointmentDatabase.entrySet()) {
////            LocalDateTime date = entry.getKey();
////            Appointment appointment = entry.getValue();
////            sb.append(formatAppointment(date, appointment)).append("\n");
////        }
////        return sb.toString();
////    }
//
//
//    /**
//     * Adds a new appointment to the shared appointment database and the patient's history.
//     *
//     * @param date The date and time of the appointment.
//     * @param patientId The ID of the patient.
//     * @param doctorId The ID of the doctor.
//     * @param remark The remark for the appointment.
//     * @return true if the appointment was successfully added, false if a duplicate was found.
//     */
//    public boolean addAppointment(LocalDateTime date, Id patientId, Id doctorId, String remark) {
//        if (appointmentDatabase.containsKey(date)) {
//            Appointment existingAppointment = appointmentDatabase.get(date);
//            if ((existingAppointment.getPatientId() == (patientId.getIdValue()))
//                    && existingAppointment.getDoctorId() == (doctorId.getIdValue())) {
//                return false; // Duplicate found
//            }
//        }
//        Appointment appointment = new Appointment(date, patientId.getIdValue(), doctorId.getIdValue(), remark);
//        appointmentDatabase.put(date, appointment);
//        appointments.add(date);
//        return true;
//    }
//
////    /**
////     * Deletes an appointment from the shared appointment database and the patient's history.
////     *
////     * @param date The date and time of the appointment.
////     * @param patientId The ID of the patient.
////     * @param doctorId The ID of the doctor.
////     * @return true if the appointment was successfully deleted, false if no matching appointment was found.
////     */
////    public static boolean deleteAppointment(LocalDateTime date, Id patientId, Id doctorId) {
////        Appointment appointment = appointmentDatabase.get(date);
////        Person patient = Person.getPersonWithId(patientId);
////        History history = patient.getHistory();
////        if (appointment == null || history.checkDuplicateAppointments(date)) {
////            return false;
////        }
////        appointmentDatabase.remove(date);
////        history.removeAppointments(date);
////        return true;
////    }
//
////    /**
////     * Removes an appointment from the patient's history.
////     *
////     * @param date The date and time of the appointment to remove.
////     */
////    public void removeAppointments(LocalDateTime date) {
////        if (appointments.contains(date)) {
////            appointments.remove(date);
////        }
////    }
////
////    /**
////     * Checks if the patient has duplicate appointments for the same date.
////     *
////     * @param date The date to check for duplicates.
////     * @return true if duplicate appointments are found, false otherwise.
////     */
////    public boolean checkDuplicateAppointments(LocalDateTime date) {
////        if (Collections.frequency(appointments, date) > 1) {
////            return true; // Duplicate found
////        }
////        return false;
////    }
//
////    /**
////     * Retrieves the details of a single appointment based on the date, patient, and doctor.
////     *
////     * @param date The date and time of the appointment.
////     * @param patientId The ID of the patient.
////     * @return The appointment details.
////     * @throws AppNotFoundException if no appointment is found for the given patient and doctor.
////     */
////    public Appointment getOneAppointmentDetail(LocalDateTime date, Id patientId)
////            throws AppNotFoundException {
////        if (!appointments.contains(date)) {
////            throw new AppNotFoundException("No appointment found in this patient's history for the given date.");
////        }
////        Appointment appointment = appointmentDatabase.get(date);
////        if (appointment == null || !appointment.getPatientId().equals(patientId)) {
////            throw new AppNotFoundException("No such appointment found for the given patient and doctor.");
////        }
////        return appointment;
////    }
//
////    /**
////     * Retrieves all the appointments for a specific patient.
////     *
////     * @param patientId The ID of the patient.
////     * @return A string listing all the appointments for the patient.
////     */
////    public String getAllPatientsAppointments(Id patientId) {
////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
////        StringBuilder result = new StringBuilder();
////        for (LocalDateTime date : appointments) {
////            Appointment appointment = appointmentDatabase.get(date);
////            if (appointment.getPatientId().equals(patientId)) {
////                result.append("DateTime: " + date.format(formatter) + " " + appointment).append("\n");
////            }
////        }
////        return result.toString();
////    }
//
////    /**
////     * Retrieves all appointments for a specific doctor.
////     *
////     * @param doctorId The ID of the doctor.
////     * @return A string listing all appointments for the doctor.
////     */
////    public static String getAllAppointments(Id doctorId) {
////        StringBuilder sb = new StringBuilder("All appointments for you in the database:\n");
////        for (Map.Entry<LocalDateTime, Appointment> entry : appointmentDatabase.entrySet()) {
////            Id checkId = entry.getValue().getDoctorId();
////            if (checkId.equals(doctorId)) {
////                LocalDateTime date = entry.getKey();
////                Appointment appointment = entry.getValue();
////                sb.append(appointment).append("\n");
////            }
////        }
////        return sb.toString();
////    }
////
////    /**
////     * Retrieves all appointments for a specific day for a patient and doctor.
////     *
////     * @param date The date to check.
////     * @param patientId The ID of the patient.
////     * @param doctorId The ID of the doctor.
////     * @return A list of appointments on the specified day.
////     * @throws AppNotFoundException if no appointments are found for the specified date.
////     */
////    public String getPatientAppointmentsForDay(LocalDate date, Id patientId, Id doctorId)
////            throws AppNotFoundException {
////        StringBuilder sb = new StringBuilder();
////        for (LocalDateTime appointmentDateTime : appointments) {
////            Appointment appointment = appointmentDatabase.get(appointmentDateTime);
////            if (appointmentDateTime.toLocalDate().equals(date)
////                    && appointment.getPatientId().equals(patientId)
////                    && appointment.getDoctorId().equals(doctorId)) {
////                sb.append(appointment)
////                        .append(System.lineSeparator());
////            }
////        }
////        if (sb.isEmpty()) {
////            throw new AppNotFoundException("No appointments found for the specified date.");
////        }
////        return sb.toString();
////    }
////
////    /**
////     * Retrieves all appointments for a specific day for a doctor.
////     *
////     * @param date The date to check.
////     * @param doctorId The ID of the doctor.
////     * @return A list of appointments on the specified day.
////     * @throws AppNotFoundException if no appointments are found for the specified date.
////     */
////    public String getDoctorAppointmentsForDay(LocalDate date, Id doctorId)
////            throws AppNotFoundException {
////        StringBuilder sb = new StringBuilder();
////        for (LocalDateTime appointmentDateTime : appointments) {
////            Appointment appointment = appointmentDatabase.get(appointmentDateTime);
////            if (appointmentDateTime.toLocalDate().equals(date)
////                    && appointment.getDoctorId().equals(doctorId)) {
////                sb.append(appointment)
////                        .append(System.lineSeparator());
////            }
////        }
////        if (sb.isEmpty()) {
////            throw new AppNotFoundException("No appointments found for the specified date.");
////        }
////        return sb.toString();
////    }
////    /**
////     * Retrieves all appointments for a specific patient within the past 'n' days.
////     *
////     * @param days The number of recent days to search for appointments.
////     * @param patientId The ID of the patient whose appointments to retrieve.
////     * @return A string containing all appointments formatted for the specified patient within the last 'n' days.
////     * @throws AppNotFoundException if no appointments are found within the specified time period.
////     */
////    public String getPatientAppointmentsForRecentDays(int days, Id patientId)
////            throws AppNotFoundException {
////        ArrayList<Appointment> appointmentsForRecentDays = new ArrayList<>();
////        StringBuilder sb = new StringBuilder();
////        LocalDate currentDate = LocalDate.now();
////        LocalDate startDate = currentDate.minusDays(days);
////
////        // Iterate through the patient's specific appointments and filter for recent 'n' days
////        for (LocalDateTime appointmentDateTime : appointments) { // appointments is the patient's list
////            Appointment appointment = appointmentDatabase.get(appointmentDateTime);
////            LocalDate appointmentDate = appointmentDateTime.toLocalDate();
////
////            if (!appointmentDate.isBefore(startDate) && !appointmentDate.isAfter(currentDate)
////                    && appointment.getPatientId().equals(patientId)) {
////                sb.append(appointment)
////                        .append(System.lineSeparator());
////            }
////        }
////
////        // If no appointments are found, throw an exception
////        if (sb.isEmpty()) {
////            throw new AppNotFoundException("No appointments found for the recent " + days + " days.");
////        }
////
////        // Return the formatted string of appointments
////        return sb.toString();
////    }
////
////    /**
////     * Retrieves all appointments for a specific doctor within the past 'n' days.
////     *
////     * @param days The number of recent days to search for appointments.
////     * @param doctorId The ID of the doctor whose appointments to retrieve.
////     * @return A string containing all appointments formatted for the specified doctor within the last 'n' days.
////     * @throws AppNotFoundException if no appointments are found within the specified time period.
////     */
////    public String getDoctorAppointmentsForRecentDays(int days, Id doctorId)
////            throws AppNotFoundException {
////        StringBuilder sb = new StringBuilder();
////
////        // Get the most recent date from the appointment database
////        if (appointmentDatabase.isEmpty()) {
////            throw new AppNotFoundException("No appointments found in the database.");
////        }
////
////        // Find the most recent appointment date
////        LocalDateTime mostRecentDateTime = appointmentDatabase.lastKey();
////        LocalDate mostRecentDate = mostRecentDateTime.toLocalDate();
////        LocalDate startDate = mostRecentDate.minusDays(days);
////
////        // Filter appointments within the last 'n' days for the given doctor
////        for (Map.Entry<LocalDateTime, Appointment> entry : appointmentDatabase.entrySet()) {
////            Appointment appointment = entry.getValue();
////            LocalDate appointmentDate = entry.getKey().toLocalDate();
////
////            if (!appointmentDate.isBefore(startDate) && !appointmentDate.isAfter(mostRecentDate)
////                    && appointment.getDoctorId().equals(doctorId)) {
////                sb.append(appointment)
////                        .append(System.lineSeparator());
////            }
////        }
////
////        // If no appointments are found, throw an exception
////        if (sb.isEmpty()) {
////            throw new AppNotFoundException("No appointments found for the recent " + days + " days.");
////        }
////
////        // Return the formatted string of appointments
////        return sb.toString();
////    }
////
////    /**
////     * Removes all appointments from a patient's history.
////     *
////     * @param patientId The ID of the patient that is removed.
////     */
////    public static void deletePatientsAppointments(Id patientId) {
////        Person patient = Person.getPatientWithId(patientId);
////        for (LocalDateTime date : patient.getHistory().appointments) {
////            Appointment appointment = appointmentDatabase.get(date);
////            if (appointment.getPatientId().equals(patientId)) {
////                appointmentDatabase.remove(date);
////            }
////        }
////    }
////
////    /**
////     * Formats the appointment details for display.
////     *
////     * @param dateTime The date and time of the appointment.
////     * @param appointment The appointment object.
////     * @return A formatted string representing the appointment details.
////     */
////    private static String formatAppointment(LocalDateTime dateTime, Appointment appointment) {
////        return String.format("Appointment on %s: Doctor ID = %s, Patient ID = %s",
////                dateTime.toString(), appointment.getDoctorId().toString(), appointment.getPatientId().toString());
////    }
//
////    /**
////     * Returns a string representation of all appointments in the database.
////     *
////     * @return A string listing all appointments for the patient.
////     */
////    @Override
////    public String toString() {
////        StringBuilder sb = new StringBuilder("Appointments for this patient:\n");
////
////        // Iterate over the list of appointments for this specific patient
////        for (LocalDateTime date : appointments) {
////            Appointment appointment = appointmentDatabase.get(date);
////            if (appointment != null) {
////                sb.append(formatAppointment(date, appointment)).append("\n");
////            }
////        }
////        return sb.toString();
////    }
//}
