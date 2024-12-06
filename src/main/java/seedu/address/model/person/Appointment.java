package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * A class meant to group all relevant details of an appointment together.
 */
public class Appointment {
    private final LocalDateTime dateTime;
    private final int patientId;
    private final int doctorId;
    private final String remarks;
    private boolean completed;

    /**
     * Creates an appointment instance associated with the specified patient, doctor, and remarks.
     *
     * @param patientId Id of the patient.
     * @param doctorId Id of the doctor.
     * @param remarks Remarks given by the doctor for the appointment or patient.
     */
    public Appointment(LocalDateTime dateTime, int patientId, int doctorId, String remarks) {
        requireAllNonNull(dateTime, patientId, doctorId, remarks);
        this.dateTime = dateTime;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.remarks = remarks;
        this.completed = false;
    }

    private Appointment(LocalDateTime dateTime, int patientId, int doctorId, String remarks, Boolean completed) {
        requireAllNonNull(dateTime, patientId, doctorId, remarks);
        this.dateTime = dateTime;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.remarks = remarks;
        this.completed = completed;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getRemarks() {
        return remarks;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public void markAsComplete() {
        this.completed = true;
    }
    /**
     * Determines if an appointment is the same appointment including checking for remarks.
     *
     * @param o Object to be compared to.
     * @return True if all details are the same (including checking remarks), false if otherwise.
     */
    public boolean equalsIncludingRemarks(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Appointment appointment = (Appointment) o;

        return Objects.equals(patientId, appointment.patientId)
                && Objects.equals(dateTime, appointment.dateTime)
                && Objects.equals(doctorId, appointment.doctorId)
                && Objects.equals(remarks, appointment.remarks);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Appointment appointment = (Appointment) o;

        return Objects.equals(patientId, appointment.patientId)
                && Objects.equals(dateTime, appointment.dateTime)
                && Objects.equals(doctorId, appointment.doctorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, doctorId, remarks);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (this.completed) {
            return "[Completed] Appointment: " + getDateTime().format(formatter) + " for " + getPatientId()
                    + " (patient id) with " + getDoctorId() + " (doctor id). "
                    + "Remarks: " + getRemarks();
        } else {
            return "[Incomplete] Appointment: " + getDateTime().format(formatter) + " for " + getPatientId()
                    + " (patient id) with " + getDoctorId() + " (doctor id). "
                    + "Remarks: " + getRemarks();
        }
    }
}

