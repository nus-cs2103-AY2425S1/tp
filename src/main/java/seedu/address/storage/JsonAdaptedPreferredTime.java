package seedu.address.storage;

import static seedu.address.model.preferredtime.PreferredTime.VALIDATED_PATTERN;

import java.util.regex.Matcher;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.preferredtime.Day;
import seedu.address.model.preferredtime.PreferredTime;
import seedu.address.model.preferredtime.Time;



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

        Matcher matcher = VALIDATED_PATTERN.matcher(preferredTime); // should always match as checked
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid preferred time format: " + preferredTime);
        }

        if (!Day.isValidDay(matcher.group(1))) {
            throw new IllegalValueException(Day.MESSAGE_CONSTRAINTS);
        }

        if (!Time.isValidTime(matcher.group(2))) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }

        return new PreferredTime(preferredTime);
    }
}
