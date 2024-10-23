package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A class meant to group all relevant details of an appointment together.
 */
public class Appointment {
    private final LocalDateTime dateTime;
    private final int patientId;
    private final int doctorId;
    private final String remarks;

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
                && Objects.equals(doctorId, appointment.doctorId)
                && Objects.equals(remarks, appointment.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, doctorId, remarks);
    }

    @Override
    public String toString() {
        return "Appointment: " + getPatientId() + " (patient id) with " + getDoctorId() + " (doctor id). "
                + "Remarks: " + getRemarks();
    }
}


