package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Appointment;

import java.time.LocalDateTime;

public class JsonAdaptedAppointment {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";
    private final String dateTime;
    private final String patientId;
    private final String doctorId;
    private final String remarks;

    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("dateTime") String dateTime,
                                  @JsonProperty("patientId") String patientId,
                                  @JsonProperty("doctorId") String doctorId,
                                  @JsonProperty("remarks") String remarks) {
        this.dateTime = dateTime;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.remarks = remarks;
    }

    public JsonAdaptedAppointment(Appointment source) {
        this.dateTime = String.valueOf(source.getDateTime());
        this.patientId = String.valueOf(source.getPatientId());
        this.doctorId = String.valueOf(source.getDoctorId());
        this.remarks = source.getRemarks();
    }

    public Appointment toModelType() throws IllegalValueException {
        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "DateTime"));
        }
        if (patientId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "patient ID"));
        }
        if (doctorId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "doctor ID"));
        }
        if (remarks == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Remarks"));
        }
        return new Appointment(LocalDateTime.parse(dateTime), Integer.parseInt(patientId),
                Integer.parseInt(doctorId), remarks);
    }
}
