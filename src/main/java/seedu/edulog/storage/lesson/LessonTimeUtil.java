package seedu.edulog.storage.lesson;

import java.util.Arrays;
import java.util.List;
import seedu.edulog.commons.exceptions.IllegalValueException;
import seedu.edulog.model.calendar.LessonTime;

/**
 * Encapsulates methods that verifies the validity of an individual lesson time obtained from Jackson,
 * as well as the validity of the combined lesson times within a {@link seedu.edulog.model.calendar.Lesson}.
 */
public class LessonTimeUtil {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";

    public static List<LessonTime> toLessonTimes(String startTime, String endTime) throws IllegalValueException {
        // Start time
        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "start time"));
        }

        if (!LessonTime.checkValidLessonTime(startTime)) {
            throw new IllegalValueException(LessonTime.NOT_24H_FORMAT);
        }

        // End time
        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "end time"));
        }
        if (!LessonTime.checkValidLessonTime(endTime)) {
            throw new IllegalValueException(LessonTime.NOT_24H_FORMAT);
        }

        // Start time-end time interactions
        if (!LessonTime.checkValidLessonTimes(startTime, endTime)) {
            throw new IllegalValueException(LessonTime.NO_SAME_TIME);
        }

        return Arrays.asList(new LessonTime(startTime), new LessonTime(endTime));
    }
}
