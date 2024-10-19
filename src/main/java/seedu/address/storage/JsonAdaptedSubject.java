package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedSubject {

    private final String subjectName;

    /**
     * Constructs a {@code JsonAdaptedSubject} with the given {@code subjectName}.
     */
    @JsonCreator
    public JsonAdaptedSubject(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Converts a given {@code Subject} into this class for Jackson use.
     */
    public JsonAdaptedSubject(Subject source) {
        subjectName = source.subjectName;
    }

    @JsonValue
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Subject toModelType() throws IllegalValueException {
        if (!Subject.isValidSubject(subjectName)) {
            throw new IllegalValueException(Subject.MESSAGE_CONSTRAINTS);
        }
        return new Subject(subjectName);
    }

}
