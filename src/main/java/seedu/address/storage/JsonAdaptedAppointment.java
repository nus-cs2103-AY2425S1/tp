package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
class JsonAdaptedAppointment {

    private final String appointmentDate;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedAppointment(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        appointmentDate = source.appointment.toString();
    }

    @JsonValue
    public String getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Appointment toModelType() throws IllegalValueException {
        if (!Appointment.isValidAppointment(appointmentDate)) {
            throw new IllegalValueException(Appointment.MESSAGE_CONSTRAINTS);
        }
        return new Appointment(appointmentDate);
    }

}
