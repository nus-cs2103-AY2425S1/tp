package seedu.address.model.appointment;

import java.time.LocalDateTime;

import seedu.address.model.appointment.exceptions.InvalidAppointmentException;
import seedu.address.model.person.Nric;

/**
 * Represents a patient's appointment in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

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
            throw new InvalidAppointmentException();
        }
        this.name = name;
        this.nric = nric;
        this.startTime = startTime;
        this.endTime = endTime;
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

}
