package tuteez.model.tag;
import static java.util.Objects.requireNonNull;
import static tuteez.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;


/**
 * Represents a Lesson in the address book.
 * Guarantees: immutable; day and time are valid as declared in {@link #isValidLesson(String, String)}
 */
public class Lesson {
    public static final String MESSAGE_CONSTRAINTS =
            "Days should be one of the following: "
            + "Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday (case insensitive). "
            + "Lesson time should be in the format HHMM-HHMM (24-hour format). "
            + "Start time must be before end time.";

    public static final String INCORRECT_LESSON_FORMAT =
            "Lesson input must contain a day and a time range separated by a space.";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");
    private static final String VALID_TIME_RANGE_REGEX = "([01]?[0-9]|2[0-3])[0-5][0-9]-([01]?[0-9]|2[0-3])[0-5][0-9]";
    private static final HashSet<Lesson> lessonSet = new HashSet<>();
    public final String day;
    public final String timeRange;


    /**
     * Constructs a {@code Lesson}.
     *
     * @param lesson A valid lesson string containing day and time range.
     */
    public Lesson(String lesson) {
        requireNonNull(lesson);
        String[] parts = lesson.split(" ", 2);
        checkArgument(parts.length == 2, INCORRECT_LESSON_FORMAT);
        String day = parts[0];
        String timeRange = parts[1];
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

    /**
     * Checks if the given lesson is a duplicate by checking the HashSet.
     *
     * @param lesson The lesson to check for duplication.
     * @return true if the lesson is a duplicate, false otherwise.
     */
    public static boolean isDuplicateLesson(Lesson lesson) {
        return lessonSet.contains(lesson);
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
