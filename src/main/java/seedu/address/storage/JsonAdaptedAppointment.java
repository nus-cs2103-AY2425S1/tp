package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {
    private final JsonAdaptedDate date;
    private final JsonAdaptedFrom from;
    private final JsonAdaptedTo to;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given Appointment details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("date") JsonAdaptedDate date,
                                  @JsonProperty("from") JsonAdaptedFrom from,
                                  @JsonProperty("to") JsonAdaptedTo to) {
        this.date = date;
        this.from = from;
        this.to = to;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        this.date = new JsonAdaptedDate(source.getDate());
        this.from = new JsonAdaptedFrom(source.getFrom());
        this.to = new JsonAdaptedTo(source.getTo());
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        if (from == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, From.class.getSimpleName()));
        }

        if (to == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, To.class.getSimpleName()));
        }

        Date modelDate = date.toModelType();
        From modelFrom = from.toModelType();
        To modelTo = to.toModelType();

        return new Appointment(modelDate, modelFrom, modelTo);
    }

}
