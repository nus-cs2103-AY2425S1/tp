package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Grade;

/**
 * Jackson-friendly version of {@link Grade}.
 */
class JsonAdaptedGrade {

    private final String grade;

    /**
     * Constructs a {@code JsonAdaptedGrade} with the given {@code grade}.
     */
    @JsonCreator
    public JsonAdaptedGrade(String grade) {
        this.grade = grade;
    }

    /**
     * Converts a given {@code Grade} into this class for Jackson use.
     */
    public JsonAdaptedGrade(Grade source) {
        grade = source.value;
    }

    @JsonValue
    public String getGrade() {
        return grade;
    }

    /**
     * Converts this Jackson-friendly adapted grade object into the model's {@code Grade} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted grade.
     */
    public Grade toModelType() throws IllegalValueException {
        if (!Grade.isValidGrade(grade)) {
            throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
        }
        return new Grade(grade);
    }

}
