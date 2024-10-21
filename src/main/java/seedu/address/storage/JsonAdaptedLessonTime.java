package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.LessonTime;

/**
 * Jackson-friendly version of {@link LessonTime}.
 */
class JsonAdaptedLessonTime {

    private final String lessonTime;

    /**
     * Constructs a {@code JsonAdaptedLessonTime} with the given {@code lessonTime}.
     */
    @JsonCreator
    public JsonAdaptedLessonTime(String lessonTime) {
        this.lessonTime = lessonTime;
    }

    /**
     * Converts a given {@code LessonTime} into this class for Jackson use.
     */
    public JsonAdaptedLessonTime(LessonTime source) {
        lessonTime = source.toString();
    }

    @JsonValue
    public String getLessonTime() {
        return lessonTime;
    }

    /**
     * Converts this Jackson-friendly adapted lesson time object into the model's {@code LessonTime} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson time.
     */
    public LessonTime toModelType() throws IllegalValueException {
        if (!LessonTime.isValidLessonTime(lessonTime)) {
            throw new IllegalValueException(LessonTime.MESSAGE_CONSTRAINTS);
        }
        return new LessonTime(lessonTime);
    }

}
