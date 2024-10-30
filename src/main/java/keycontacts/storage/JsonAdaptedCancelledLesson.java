package keycontacts.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import keycontacts.commons.exceptions.IllegalValueException;
import keycontacts.model.lesson.CancelledLesson;
import keycontacts.model.lesson.Date;

/**
 * Jackson-friendly version of {@link CancelledLesson}.
 */
public class JsonAdaptedCancelledLesson {

    private final String cancelledLessonDate;

    /**
     * Constructs a {@code JsonAdaptedCancelledLesson} with the given {@code cancelledLessonDate}.
     */
    @JsonCreator
    public JsonAdaptedCancelledLesson(String cancelledLessonDate) {
        this.cancelledLessonDate = cancelledLessonDate;
    }

    /**
     * Converts a given {@code CancelledLesson} into this class for Jackson use.
     */
    public JsonAdaptedCancelledLesson(CancelledLesson source) {
        cancelledLessonDate = source.getLessonDate().toString();
    }

    @JsonValue
    public String getCancelledLessonDate() {
        return cancelledLessonDate;
    }

    /**
     * Converts this Jackson-friendly adapted cancelledLesson object into the model's {@code CancelledLesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted cancelledLesson.
     */
    public CancelledLesson toModelType() throws IllegalValueException {
        if (!Date.isValidDate(cancelledLessonDate)) {
            throw new IllegalValueException(Date.MESSAGE_INVALID_DATE);
        }
        return new CancelledLesson(new Date(cancelledLessonDate));
    }

}
