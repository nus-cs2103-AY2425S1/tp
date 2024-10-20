package seedu.academyassist.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.academyassist.commons.exceptions.IllegalValueException;
import seedu.academyassist.model.person.Subject;

/**
 * Jackson-friendly version of {@link Subject}.
 */
class JsonAdaptedSubject {

    private final String subject;

    /**
     * Constructs a {@code JsonAdaptedSubject} with the given {@code subject}.
     */
    @JsonCreator
    public JsonAdaptedSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Converts a given {@code Subject} into this class for Jackson use.
     */
    public JsonAdaptedSubject(Subject source) {
        subject = source.toString();
    }

    @JsonValue
    public String getSubject() {
        return subject;
    }

    /**
     * Converts this Jackson-friendly adapted subject object into the model's {@code Subject} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted subject.
     */
    public Subject toModelType() throws IllegalValueException {
        if (!Subject.isValidSubject(subject)) {
            throw new IllegalValueException(Subject.MESSAGE_CONSTRAINTS);
        }
        return new Subject(subject);
    }

    @Override
    public String toString() {
        return subject;
    }

}
