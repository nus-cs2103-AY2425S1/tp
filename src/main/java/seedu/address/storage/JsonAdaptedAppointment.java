package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Appointment;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";
    private final String dateTime;
    private final String patientId;
    private final String doctorId;
    private final String remarks;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given appointment details.
     */
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

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        this.dateTime = String.valueOf(source.getDateTime());
        this.patientId = String.valueOf(source.getPatientId());
        this.doctorId = String.valueOf(source.getDoctorId());
        this.remarks = source.getRemarks();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
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
