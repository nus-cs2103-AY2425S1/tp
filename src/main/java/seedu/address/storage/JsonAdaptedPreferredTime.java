package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.preferredtime.PreferredTime;


/**
 * Jackson-friendly version of {@link PreferredTime}.
 */
public class JsonAdaptedPreferredTime {

    private final String preferredTime;

    /**
     * Constructs a {@code JsonAdaptedPreferredTime} with the given {@code preferredTime}.
     */
    @JsonCreator
    public JsonAdaptedPreferredTime(String preferredTime) {
        this.preferredTime = preferredTime;
    }

    /**
     * Converts a given {@code PreferredTime} into this class for Jackson use.
     */
    public JsonAdaptedPreferredTime(PreferredTime source) {
        preferredTime = source.preferredTime;
    }

    @JsonValue
    public String getPreferredTime() {
        return preferredTime;
    }

    /**
     * Converts this Jackson-friendly adapted game object into the model's {@code PreferredTime} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted preferred time.
     */
    public PreferredTime toModelType() throws IllegalValueException {
        if (!PreferredTime.isValidPreferredTime(preferredTime)) {
            throw new IllegalValueException(PreferredTime.MESSAGE_CONSTRAINTS);
        }
        return new PreferredTime(preferredTime);
    }
}
