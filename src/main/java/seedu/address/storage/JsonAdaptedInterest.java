package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Interest;

/**
 * Jackson-friendly version of {@link Interest}.
 */
public class JsonAdaptedInterest {

    private final String interestName;

    // Constructor to convert Interest to JsonAdaptedInterest
    public JsonAdaptedInterest(Interest source) {
        this.interestName = source.toString();
    }

    @JsonCreator
    public JsonAdaptedInterest(@JsonProperty("interestName") String interestName) {
        this.interestName = interestName;
    }

    /**
     * Converts this JSON-adapted interest into the model's {@code Interest} object.
     *
     * @return An {@code Interest} object with the validated interest name.
     * @throws IllegalValueException if the interest name is missing, null, or empty after trimming.
     */
    public Interest toModelType() throws IllegalValueException {
        if (interestName == null || interestName.trim().isEmpty()) {
            throw new IllegalValueException("Interest field is missing or empty!");
        }
        return new Interest(interestName);
    }
}

