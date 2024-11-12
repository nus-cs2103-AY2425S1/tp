package seedu.edulog.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.edulog.commons.util.AppUtil.checkArgument;
import static seedu.edulog.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Holds a given time of a Lesson (either start or end time), given in 24-hour format.
 */
public class LessonTime {
    public static final String NO_SAME_TIME =
        "Lessons cannot start and end at the same time.";

    public static final String NOT_24H_FORMAT =
        "Times provided must be in 24-hour time format. Examples: 0000, 1027, 1830, 2215, 2359.";


    public static final DateTimeFormatter FORMAT_24H = DateTimeFormatter.ofPattern("HHmm");

    public static final LessonTime EARLIEST_TIME = new LessonTime("0000");
    public static final LessonTime LATEST_TIME = new LessonTime("2359");

    public final LocalTime time;

    /**
     * Accepts string representations of 24-hour times denoting the start or end time of a Lesson. <br>
     * @param time A string representation of a 24-hour time, determined by {@link #checkValidLessonTime(String)}
     */
    public LessonTime(String time) {
        requireNonNull(time);

        // per-time checks
        checkArgument(checkValidLessonTime(time), LessonTime.NOT_24H_FORMAT);

        this.time = LessonTime.convertToLocalTime(time);
    }

    // Validator and parsing fns ================================================================================

    /**
     * Checks if a provided String conforms to a 24-hour time format specifier, e.g. "2130".
     */
    public static boolean checkValidLessonTime(String time) {
        requireNonNull(time);
        if (time.length() != 4) {
            return false;
        }

        String hour = time.substring(0, 2);
        String minute = time.substring(2);

        // Ensure that hour is between 00 and 23, and minute is between 00 and 59
        return hour.compareTo("23") <= 0 && minute.compareTo("59") <= 0;
    }

    /**
     * Converts a String in 24-hour format into the internal time representation, which is LocalTime.
     */
    private static LocalTime convertToLocalTime(String time) {
        requireNonNull(time);
        checkArgument(checkValidLessonTime(time), LessonTime.NOT_24H_FORMAT);

        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(2));

        return LocalTime.of(hour, minute);
    }

    /**
     * Checks that 2 given times are not ambiguous, i.e. not the same start and end time.
     */
    public static boolean checkValidLessonTimes(String time1, String time2) {
        requireAllNonNull(time1, time2);
        return !convertToLocalTime(time1).equals(convertToLocalTime(time2));
    }


    // Utility fns ==============================================================================================
    /**
     * Returns if the lesson spans 2 days, e.g. Monday 2000 to 0000, or Tuesday 2200 to 0100.
     */
    public static boolean spansTwoDays(LessonTime startTime, LessonTime endTime) {
        requireAllNonNull(startTime, endTime);
        return endTime.time.isBefore(startTime.time);
    }

    // Printing and identity fns ================================================================================

    /**
     * Getter function for internal representation of lesson time.
     * NOTE: Please avoid the use of this function in non-test code to maintain abstraction!
     */
    public LocalTime getTime() {
        return time;
    }

    public String getFormattedTime() {
        return time.format(FORMAT_24H);
    }
    @Override
    public String toString() {
        return getFormattedTime();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonTime)) {
            return false;
        }

        LessonTime otherLessonTime = (LessonTime) other;
        return time.equals(otherLessonTime.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }
}
