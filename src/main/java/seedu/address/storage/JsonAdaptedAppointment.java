package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Name;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    private final String name;
    private final String date;
    private final String startTime;
    private final String endTime;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given appointment details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(
            @JsonProperty("name") String name,
            @JsonProperty("date") String date,
            @JsonProperty("startTime") String startTime,
            @JsonProperty("endTime") String endTime) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        name = source.name().fullName;
        date = source.date().toString();
        startTime = source.startTime().toString();
        endTime = source.endTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment toModelType() throws IllegalValueException {
        final Name modelName = parseName(name);
        final LocalDate modelDate = parseDate(date);
        final LocalTime modelStartTime = parseTime(startTime, "startTime");
        final LocalTime modelEndTime = parseTime(endTime, "endTime");

        return new Appointment(modelName, modelDate, modelStartTime, modelEndTime);
    }

    private Name parseName(String name) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(name);
    }

    private LocalDate parseDate(String date) throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_DATE_FORMAT);
        }
    }

    private LocalTime parseTime(String time, String field) throws IllegalValueException {
        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, field));
        }
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_TIME_FORMAT);
        }
    }
}
