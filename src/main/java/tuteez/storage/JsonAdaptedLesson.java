package tuteez.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import tuteez.commons.exceptions.IllegalValueException;
import tuteez.model.person.lesson.Lesson;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
public class JsonAdaptedLesson {
    private final String dayAndTime;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code lesson}.
     */
    @JsonCreator
    public JsonAdaptedLesson(String dayAndTime) {
        this.dayAndTime = dayAndTime;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        dayAndTime = source.getDayAndTime();
    }

    @JsonValue
    public String getDayAndTime() {
        return dayAndTime;
    }

    /**
     * Converts this Jackson-friendly adapted Lesson object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Lesson.
     */
    public Lesson toModelType() throws IllegalValueException {
        if (!Lesson.isValidLesson(dayAndTime)) {
            throw new IllegalValueException(Lesson.MESSAGE_CONSTRAINTS);
        }
        return new Lesson(dayAndTime);
    }
}
