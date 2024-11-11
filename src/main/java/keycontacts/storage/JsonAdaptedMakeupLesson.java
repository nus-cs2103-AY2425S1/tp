package keycontacts.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import keycontacts.commons.exceptions.IllegalValueException;
import keycontacts.model.lesson.Date;
import keycontacts.model.lesson.Lesson;
import keycontacts.model.lesson.MakeupLesson;
import keycontacts.model.lesson.Time;

/**
 * Jackson-friendly version of {@link MakeupLesson}.
 */
public class JsonAdaptedMakeupLesson {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Makeup lesson's %s field is missing or empty!";

    private final String lessonDate;
    private final String startTime;
    private final String endTime;

    /**
     * Constructs a {@code JsonAdaptedMakeupLesson} with the given makeup lesson
     * details.
     */
    @JsonCreator
    public JsonAdaptedMakeupLesson(
            @JsonProperty("lessonDate") String lessonDate,
            @JsonProperty("startTime") String startTime,
            @JsonProperty("endTime") String endTime) {
        this.lessonDate = lessonDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    /**
     * Converts a given {@code MakeupLesson} into this class for Jackson use.
     */
    public JsonAdaptedMakeupLesson(MakeupLesson source) {
        lessonDate = source.getLessonDate().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted makeup lesson object into the model's
     * {@code MakeupLesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted makeup lesson.
     */
    public MakeupLesson toModelType() throws IllegalValueException {
        if (lessonDate == null || lessonDate.trim().isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(lessonDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelLessonDate = new Date(lessonDate);

        if (startTime == null || endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(startTime) || !Time.isValidTime(endTime)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelStartTime = new Time(startTime);
        final Time modelEndTime = new Time(endTime);

        if (!Lesson.isValidTimePair(modelStartTime, modelEndTime)) {
            throw new IllegalValueException(Lesson.MESSAGE_CONSTRAINTS);
        }

        return new MakeupLesson(modelLessonDate, modelStartTime, modelEndTime);
    }
}
