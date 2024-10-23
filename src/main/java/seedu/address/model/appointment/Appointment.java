package seedu.address.model.appointment;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.model.appointment.exceptions.InvalidAppointmentException;
import seedu.address.model.person.Nric;

/**
 * Represents a patient's appointment in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

    public static final String INVALID_APPOINTMENT_ERROR = "End time cannot be before start time";

    private final String name;
    private final Nric nric;

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private boolean isCompleted = false;

    /**
     * Constructs an appointment with the given details.
     *
     * @param name the name of the appointment
     * @param nric the nric of the patient
     * @param startTime the start time of the appointment
     * @param endTime the end time of the appointment
     */
    public Appointment(String name, Nric nric, LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new InvalidAppointmentException(INVALID_APPOINTMENT_ERROR);
        }
        this.name = name;
        this.nric = nric;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructs an appointment with the given details and completion status.
     *
     * @param name the name of the appointment
     * @param nric the nric of the patient
     * @param startTime the start time of the appointment
     * @param endTime the end time of the appointment
     * @param isCompleted the completion status of the appointment
     */
    public Appointment(String name, Nric nric, LocalDateTime startTime, LocalDateTime endTime, boolean isCompleted) {
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new InvalidAppointmentException(INVALID_APPOINTMENT_ERROR);
        }
        this.name = name;
        this.nric = nric;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCompleted = isCompleted;
    }

    /**
     * @return the name of the appointment
     */
    public String getName() {
        return name;
    }

    /**
     * @return the nric of the patient
     */
    public Nric getNric() {
        return nric;
    }

    /**
     * @return the start time of the appointment
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * @return the end time of the appointment
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * @return true if the appointment has been completed, false otherwise
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Marks the appointment as completed.
     */
    public void markAsCompleted() {
        isCompleted = true;
    }

    /**
     * Returns true if both appointments have the same nric.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameAppointment(Appointment otherAppointment) {
        if (otherAppointment == this) {
            return true;
        }

        return otherAppointment != null
                && otherAppointment.getNric().equals(getNric())
                && otherAppointment.getStartTime().equals(getStartTime());
    }

    /**
     * Returns a string representation of the appointment.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Appointment: " + name + "\n");
        sb.append("Nric: " + nric.maskNric() + "\n");
        sb.append("Start Time: " + startTime + "\n");
        sb.append("End Time: " + endTime + "\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Appointment)) {
            return false;
        }
        Appointment otherAppointment = (Appointment) other;
        return otherAppointment.getName().equals(getName())
                && otherAppointment.getNric().equals(getNric())
                && otherAppointment.getStartTime().equals(getStartTime())
                && otherAppointment.getEndTime().equals(getEndTime());
    }

    public String getAppointmentDetails() {
        StringBuilder sb = new StringBuilder();

        // Formatter for the full date format like "22 October 2024"
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");

        String formattedStartDate = startTime.toLocalDate().format(dateFormatter);
        LocalTime startTimeOfDay = startTime.toLocalTime();
        String formattedEndDate = endTime.toLocalDate().format(dateFormatter);
        LocalTime endTimeOfDay = endTime.toLocalTime();

        sb.append("From: " + formattedStartDate + " " + startTimeOfDay + "HRS ");
        sb.append("To: " + formattedEndDate + " " + endTimeOfDay + "HRS ");

        // Status logic
        String status = isCompleted ? "Completed" : "Not Completed";
        sb.append("Status: " + status);

        return sb.toString();
    }

}
