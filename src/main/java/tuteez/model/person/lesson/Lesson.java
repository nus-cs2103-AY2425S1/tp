package tuteez.model.person.lesson;
import static java.util.Objects.requireNonNull;
import static tuteez.commons.util.AppUtil.checkArgument;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;


/**
 * Represents a Lesson in the address book.
 * Guarantees: immutable; day and time are valid as declared in {@link #isValidLesson(String)}
 */
public class Lesson {
    public static final String MESSAGE_CONSTRAINTS =
            "Days should be one of the following: "
            + "Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday (case insensitive). \n"
            + "Lesson time should be in the format HHMM-HHMM (24-hour format). "
            + "Start time must be before end time. \n"
            + "Lesson input must contain a day and a time range separated by a space. "
            + "Example: " + PREFIX_LESSON + " monday 0900-1000";

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");
    private static final String VALID_TIME_RANGE_REGEX = "([01]?[0-9]|2[0-3])[0-5][0-9]-([01]?[0-9]|2[0-3])[0-5][0-9]";
    private static final HashSet<Lesson> lessonSet = new HashSet<>();
    public final String dayAndTime;


    /**
     * Constructs a {@code Lesson}.
     *
     * @param lesson A valid lesson string containing day and time range.
     */
    public Lesson(String lesson) {
        requireNonNull(lesson);
        checkArgument(isValidLesson(lesson), MESSAGE_CONSTRAINTS);
        this.dayAndTime = lesson.toUpperCase();
    }

    /**
     * Validates if the day provided is valid (i.e., part of the defined Days enum).
     *
     * @param day The day to check.
     * @return true if the day is valid.
     */
    private static boolean isValidDay(String day) {
        return Arrays.stream(Day.values())
                .anyMatch(d -> d.name().equalsIgnoreCase(day));
    }

    /**
     * Validates the time range format (should be HHMM-HHMM 24-hour format).
     *
     * @param timeRange The time range to validate.
     * @return true if the time range is valid.
     */
    private static boolean isValidTimeRange(String timeRange) {
        return timeRange.matches(VALID_TIME_RANGE_REGEX);
    }

    /**
     * Validates if the start time is before the end time.
     *
     * @param timeRange The time range to check.
     * @return true if the start time is before the end time.
     */
    private static boolean isValidTimeOrder(String timeRange) {
        if (isValidTimeRange(timeRange)) {
            String[] times = timeRange.split("-");
            LocalTime startTime = LocalTime.parse(times[0], TIME_FORMATTER);
            LocalTime endTime = LocalTime.parse(times[1], TIME_FORMATTER);
            return startTime.isBefore(endTime);
        }
        return false;
    }


    /**
     * Validates if the given lesson string is valid.
     * The lesson should consist of a day and a time range separated by a space.
     * The day must be one of the valid days of the week (case-insensitive),
     * and the time range must follow the HHMM-HHMM 24-hour format with the start time
     * being before the end time.
     *
     * @param lesson The string representing the lesson, consisting of a day and a time range.
     * @return true if the lesson is valid in terms of day, time range format, and time order;
     *          false otherwise.
     */
    public static boolean isValidLesson(String lesson) {
        String[] parts = lesson.split(" ", 2);
        if (parts.length != 2) {
            return false;
        }
        String day = parts[0];
        String timeRange = parts[1];
        return isValidDay(day) && isValidTimeRange(timeRange) && isValidTimeOrder(timeRange);
    }

    /**
     * Adds a lesson to the HashSet.
     *
     * @param lesson The lesson to add.
     */
    public static void addLesson(Lesson lesson) {
        lessonSet.add(lesson);
    }

    /**
     * Removes a lesson from the HashSet.
     *
     * @param lesson The lesson to remove.
     */
    public static void removeLesson(Lesson lesson) {
        lessonSet.remove(lesson);
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
        return dayAndTime.equals(((Lesson) other).dayAndTime);
    }

    @Override
    public int hashCode() {
        return dayAndTime.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + dayAndTime + ']';
    }
}
