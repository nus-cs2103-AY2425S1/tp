package seedu.edulog.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.edulog.commons.util.AppUtil.checkArgument;
import static seedu.edulog.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Holds the start and end times of a Lesson, given in 24-hour format.
 */
public class LessonTime {
    public static final String NO_SAME_TIME =
        "Lessons cannot start and end at the same time.";

    public static final String NOT_24H_FORMAT =
        "Times provided must be in 24-hour time format. Examples: 0000, 1027, 1830, 2215, 2359.";


    public static final DateTimeFormatter FORMAT_24H = DateTimeFormatter.ofPattern("HHmm");

    public final LocalTime startTime;
    public final LocalTime endTime;

    public LessonTime(String startTime, String endTime) {
        requireAllNonNull(startTime, endTime);

        // per-time checks
        checkArgument(checkValidLessonTime(startTime), LessonTime.NOT_24H_FORMAT);
        checkArgument(checkValidLessonTime(endTime), LessonTime.NOT_24H_FORMAT);

        LocalTime startTimeTmp = LessonTime.convertToLocalTime(startTime);
        LocalTime endTimeTmp = LessonTime.convertToLocalTime(endTime);

        // checks between both times
        checkArgument(checkValidLessonTimes(startTimeTmp, endTimeTmp));

        this.startTime = startTimeTmp;
        this.endTime = endTimeTmp;
    }

    // Validator and parsing fns ================================================================================

    /**
     * Checks if a provided String conforms to a 24-hour time format specifier.
     */
    public static boolean checkValidLessonTime(String time) {
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
     * Checks that 2 given lesson times are not ambiguous, i.e. not the same start and end time.
     */
    public static boolean checkValidLessonTimes(LocalTime time1, LocalTime time2) {
        return !time1.equals(time2);
    }

    // Printing and identity fns ================================================================================

    /**
     * Returns if the lesson spans 2 days, e.g. Monday 2000 to 0000, or Tuesday 2200 to 0100.
     */
    public boolean spansTwoDays() {
        return startTime.isBefore(endTime);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getFormattedStartTime() {
        return startTime.format(FORMAT_24H);
    }

    public String getFormattedEndTime() {
        return endTime.format(FORMAT_24H);
    }

    @Override
    public String toString() {
        return getFormattedStartTime() + " - " + getFormattedEndTime();
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
        return startTime.equals(otherLessonTime.startTime) && endTime.equals(otherLessonTime.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }
}
