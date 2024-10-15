package seedu.address.model.person;

import java.util.Objects;

/**
 * A class meant to group all relevant details of an appointment together.
 * @param <Id>
 * @param <Id>
 * @param <String>
 */
public class Appointment<Id, String> {
    private Id patientId;
    private Id doctorId;
    private String remarks;

    /**
     * Creates an appointment instance associated with the specified patient, doctor with the given remarks.
     *
     * @param patientId Id of patient.
     * @param doctorId Id of doctor.
     * @param remarks Remarks given by the doctor for the appointment or patient.
     */
    public Appointment(Id patientId, Id doctorId, String remarks) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.remarks = remarks;
    }

    public Id getPatientId() {
        return patientId;
    }

    public Id getDoctorId() {
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

        Appointment<?, ?> appointment = (Appointment<?, ?>) o; // Typecast to Appointment

        // Compare key and value
        return Objects.equals(patientId, appointment.patientId)
                && Objects.equals(doctorId, appointment.doctorId)
                && Objects.equals(remarks, appointment.remarks);
    }
}
