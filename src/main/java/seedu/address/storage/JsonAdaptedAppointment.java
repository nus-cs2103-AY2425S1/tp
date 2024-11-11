package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {
    private final String description;
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        if (source == null) {
            this.description = null;
            this.start = null;
            this.end = null;
        } else {
            this.description = source.getDescription();
            this.start = source.getStart();
            this.end = source.getEnd();
        }
    }

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given appointment details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("description") String description,
                                  @JsonProperty("start") LocalDateTime start,
                                  @JsonProperty("end") LocalDateTime end) {
        this.description = description;
        this.start = start;
        this.end = end;
    }

    /**
     * Converts this JsonAdaptedAppointment back to an Appointment model object.
     */
    @JsonValue
    public Appointment toModelType() throws IllegalValueException {
        return new Appointment(description, start, end);
    }
}
