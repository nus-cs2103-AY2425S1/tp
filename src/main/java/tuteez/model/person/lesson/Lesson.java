package tuteez.model.person.lesson;
import static java.util.Objects.requireNonNull;
import static tuteez.commons.util.AppUtil.checkArgument;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;


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
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
    private final Day lessonDay;
    private final LocalTime startTime;
    private final LocalTime endTime;


    /**
     * Constructs a {@code Lesson}.
     *
     * @param lesson A valid lesson string containing day and time range.
     */
    public Lesson(String lesson) {
        requireNonNull(lesson);
        checkArgument(isValidLesson(lesson), MESSAGE_CONSTRAINTS);
        String[] lessonDayTimeArr = lesson.split(" ");
        String[] timeArr = lessonDayTimeArr[1].split("-");
        this.lessonDay = Day.convertDayToEnum(lessonDayTimeArr[0].toLowerCase());
        this.startTime = LocalTime.parse(timeArr[0], timeFormatter);
        this.endTime = LocalTime.parse(timeArr[1], timeFormatter);
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
     * Determines whether two lessons clash in time.
     * A clash is defined as any overlap in the start and end times of the two lessons.
     *
     * <p>Note: Lessons that are back-to-back, meaning one ends exactly when the other starts
     * (e.g., 19:00-20:00 and 20:00-21:00), are not considered clashing.</p>
     *
     * @param firstLs The first {@code Lesson} to compare.
     * @param secondLs The second {@code Lesson} to compare.
     * @return {@code true} if the two lessons overlap in time, otherwise {@code false}.
     */
    public static boolean isClashingWithOtherLesson(Lesson firstLs, Lesson secondLs) {
        assert firstLs != null;
        assert secondLs != null;
        if (!firstLs.lessonDay.equals(secondLs.lessonDay)) {
            return false;
        }

        boolean completelyBefore = firstLs.endTime.isBefore(secondLs.startTime)
                 || firstLs.endTime.equals(secondLs.startTime);
        boolean completelyAfter = firstLs.startTime.isAfter(secondLs.endTime)
                 || firstLs.startTime.equals(secondLs.endTime);
        return !(completelyBefore || completelyAfter);
    }

    /**
     * Returns the day on which this lesson occurs.
     *
     * @return The {@code Day} enum representing the day of the week for this lesson.
     */
    public Day getLessonDay() {
        return lessonDay;
    }

    /**
     * Compares this lesson to another object for equality.
     * Returns {@code true} if the other object is a {@code Lesson} with the
     * same start and end times as this lesson.
     *
     * @param other The object to compare for equality.
     * @return {@code true} if the other object is a {@code Lesson} and has the same
     *         start and end times as this lesson, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return otherLesson.startTime.equals(this.startTime) && otherLesson.endTime.equals(this.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonDay, startTime, endTime);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + lessonDay.toString() + " " + startTime.toString().replace(":", "") + "-"
                + endTime.toString().replace(":", "") + ']';
    }

    /**
     * @return A string with only day and time
     */
    public String getDayAndTime() {
        return this.toString().replace("[", "").replace("]", "");
    }

    /**
     * A comparator for {@code Lesson} objects that compares them based on their start times.
     * This comparator orders lessons in ascending order of their start time.
     */
    public static class LessonComparator implements Comparator<Lesson> {

        /**
         * Compares two {@code Lesson} objects first by their day of the week and then by their start times.
         *
         * <p>If the lessons occur on different days, the comparison is based on the day of the week.
         * If the lessons occur on the same day, the comparison is based on their start times.</p>
         *
         * @param o1 The first {@code Lesson} to compare.
         * @param o2 The second {@code Lesson} to compare.
         * @return If first {@code Lesson} is smaller than, equals to or greater than second {@code Lesson}
         */
        @Override
        public int compare(Lesson o1, Lesson o2) {
            assert o1 != null;
            assert o2 != null;
            int dayComparison = o1.lessonDay.compareTo(o2.lessonDay);
            if (dayComparison != 0) {
                return dayComparison;
            }

            return o1.startTime.compareTo(o2.startTime);
        }
    }
}
