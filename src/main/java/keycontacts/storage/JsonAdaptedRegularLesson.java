package keycontacts.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import keycontacts.commons.exceptions.IllegalValueException;
import keycontacts.model.lesson.Day;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.lesson.Time;

/**
 * Jackson-friendly version of {@link RegularLesson}.
 */
class JsonAdaptedRegularLesson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Regular lesson's %s field is missing!";

    private final String lessonDay;
    private final String startTime;
    private final String endTime;

    /**
     * Constructs a {@code JsonAdaptedRegularLesson} with the given student association details.
     */
    @JsonCreator
    public JsonAdaptedRegularLesson(@JsonProperty("lessonDay") String lessonDay,
                                    @JsonProperty("startTime") String startTime,
                                    @JsonProperty("endTime") String endTime) {
        this.lessonDay = lessonDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given {@code RegularLesson} into this class for Jackson use.
     */
    public JsonAdaptedRegularLesson(RegularLesson source) {
        lessonDay = source.getLessonDay().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted regular lesson object into the model's {@code RegularLesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted regular lesson.
     */
    public RegularLesson toModelType() throws IllegalValueException {

        if (lessonDay == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Day.class.getSimpleName()));
        }
        if (!Day.isValidDay(lessonDay)) {
            throw new IllegalValueException(Day.MESSAGE_CONSTRAINTS);
        }
        final Day modelLessonDay = new Day(lessonDay);

        if (startTime == null || endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(startTime) || !Time.isValidTime(endTime)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelStartTime = new Time(startTime);
        final Time modelEndTime = new Time(endTime);

        return new RegularLesson(modelLessonDay, modelStartTime, modelEndTime);
    }

}
