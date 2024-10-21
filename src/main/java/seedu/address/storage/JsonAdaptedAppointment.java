package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;


/**
 * Jackson-friendly version of {@link Appointment}.
 */
class JsonAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    private final String name;
    private final String nric;
    private final String startTime;
    private final String endTime;
    private final boolean isCompleted;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given appointment details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(
            @JsonProperty("name") String name,
            @JsonProperty("nric") String nric,
            @JsonProperty("startTime") String startTime,
            @JsonProperty("endTime") String endTime,
            @JsonProperty("isCompleted") boolean isCompleted) {
        this.name = name;
        this.nric = nric;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCompleted = isCompleted;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        name = source.getName();
        nric = source.getNric().value; // Nric's string value
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
        isCompleted = source.isCompleted();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name"));
        }

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Nric"));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

        LocalDateTime modelStartTime;
        LocalDateTime modelEndTime;
        try {
            modelStartTime = LocalDateTime.parse(startTime);
            modelEndTime = LocalDateTime.parse(endTime);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid date format for appointment times.");
        }

        if (modelStartTime.isAfter(modelEndTime)) {
            throw new IllegalValueException("End time cannot be before start time.");
        }

        // Use the new constructor that accepts isCompleted as a parameter
        return new Appointment(name, modelNric, modelStartTime, modelEndTime, isCompleted);
    }
}
