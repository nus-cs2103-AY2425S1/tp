package seedu.edulog.storage.lesson;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.edulog.commons.exceptions.IllegalValueException;
import seedu.edulog.model.calendar.Day;
import seedu.edulog.model.calendar.Description;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.calendar.LessonTime;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
public class JsonAdaptedLesson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";

    private String description;
    private String startDay;
    private String startTime;
    private String endTime;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("description") String description, @JsonProperty("startDay") String startDay,
                             @JsonProperty("startTime") String startTime, @JsonProperty("endTime") String endTime) {
        this.description = description;
        this.startDay = startDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        description = source.getDescription().toString();
        startDay = source.getStartDay().toString();
        startTime = source.getFormattedStartTime();
        endTime = source.getFormattedEndTime();
    }

    /**
     * Converts this Jackson-friendly adapted lesson object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson.
     */
    public Lesson toModelType() throws IllegalValueException {
        // Description
        if (description == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }

        if (Description.checkEmptyDescription(description)) {
            throw new IllegalValueException(Description.DESCRIPTION_EMPTY);
        } else if (Description.checkTooLongDescription(description)) {
            throw new IllegalValueException(Description.DESCRIPTION_TOO_LONG);
        }

        Description modelDescription = new Description(description);

        // Start day
        if (startDay == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Day.class.getSimpleName()));
        }
        if (!Day.checkValidDayOfWeek(startDay)) {
            throw new IllegalValueException(Day.INVALID_DAY_OF_WEEK);
        }

        final Day modelStartDay = new Day(startDay);

        List<LessonTime> lessonTimes = LessonTimeUtil.toLessonTimes(startTime, endTime);

        final LessonTime modelStartTime = lessonTimes.get(0);
        final LessonTime modelEndTime = lessonTimes.get(1);

        return new Lesson(modelDescription, modelStartDay, modelStartTime, modelEndTime);
    }

}
