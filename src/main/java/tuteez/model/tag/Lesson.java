package tuteez.model.tag;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static tuteez.commons.util.AppUtil.checkArgument;

/**
 * Represents a Lesson in the address book.
 * Guarantees: immutable; day and time are valid as declared in {@link #isValidLesson(String, String)}
 */
public class Lesson {
    public static final String MESSAGE_CONSTRAINTS =
            "Days should be one of the following: Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday (case insensitive). "
            + "Lesson time should be in the format HHMM-HHMM (24-hour format). "
            + "Start time must be before end time.";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");
    private static final String VALID_TIME_RANGE_REGEX = "([01]?[0-9]|2[0-3])[0-5][0-9]-([01]?[0-9]|2[0-3])[0-5][0-9]";
    public final String day;
    public final String timeRange;

    /**
     * Constructs a {@code Lesson}.
     *
     * @param day A valid day.
     * @param timeRange A valid lesson time range (HHMM-HHMM).
     */
    public Lesson(String day, String timeRange) {
        requireNonNull(day);
        requireNonNull(timeRange);
        checkArgument(isValidLesson(day, timeRange), MESSAGE_CONSTRAINTS);

        this.day = day;
        this.timeRange = timeRange;
    }

    /**
     * Returns true if the given day and lesson time are valid, and the start time is before the end time.
     *
     * @param day The string representing the day.
     * @param timeRange The string representing the time range in HHMM-HHMM format.
     * @return true if both the day and lesson time are valid, and the start time is before the end time.
     */
    public static boolean isValidLesson(String day, String timeRange) {
        // Check if the day is valid
        boolean isValidDay = Arrays.stream(Day.values())
                .anyMatch(d -> d.name().equalsIgnoreCase(day));

        // Check if the time range matches the format HHMM-HHMM
        boolean isValidTimeRange = timeRange.matches(VALID_TIME_RANGE_REGEX);

        // If the time range is valid, further check if start time is before end time
        if (isValidTimeRange) {
            String[] times = timeRange.split("-");
            LocalTime startTime = LocalTime.parse(times[0], TIME_FORMATTER);
            LocalTime endTime = LocalTime.parse(times[1], TIME_FORMATTER);
            boolean isValidTimeOrder = startTime.isBefore(endTime);

            return isValidDay && isValidTimeOrder;
        }

        return false;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return day.equals(otherLesson.day) && timeRange.equals(otherLesson.timeRange);
    }

    @Override
    public int hashCode() {
        return day.hashCode() + timeRange.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + day + ' ' + timeRange + ']';
    }
}
