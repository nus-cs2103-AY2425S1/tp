package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.appointment.Appointment;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {

    private final String date;
    private final String startTime;
    private final String endTime;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given appointment details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(
            @JsonProperty("date") String date,
            @JsonProperty("startTime") String startTime,
            @JsonProperty("endTime") String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        date = source.date().toString();
        startTime = source.startTime().toString();
        endTime = source.endTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     */
    public Appointment toModelType() {
        return new Appointment(LocalDate.parse(date), LocalTime.parse(startTime), LocalTime.parse(endTime));
    }
}
