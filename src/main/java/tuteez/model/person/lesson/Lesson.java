package tuteez.model.person.lesson;
import static java.util.Objects.requireNonNull;
import static tuteez.commons.util.AppUtil.checkArgument;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;


/**
 * Represents a Lesson in the address book.
 * Guarantees: immutable; day and time are valid as declared in {@link #isValidLesson(String)}
 */
public class Lesson {
    public static final String MESSAGE_CONSTRAINTS =
            "Days should be one of the following: "
            + "Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday (case insensitive). \n"
            + "Lesson time should be in the format HHMM-HHMM (24-hour format). "
            + "Start time must be before end time. Start and end time cannot be the same. \n "
            + "Start time cannot be 2359 and end time cannot be 0000\n"
            + "Lesson input must contain a day and a time range separated by a space. "
            + "Example: " + PREFIX_LESSON + " monday 0900-1000";
    public static final String MESSAGE_INVALID_LESSON_DAY = "Are you sure you have given a valid day of the week? ⊙▂⊙";
    public static final String MESSAGE_INVALID_LESSON_TIME = "Are you sure you have given valid "
            + "start and end times? ⊙▂⊙ \n"
            + "Please give it in HHMM-HHMM (24-hour format)";
    public static final String MESSAGE_INVALID_LESSON_END_TIME = "Unfortunately we do not allow lessons to overflow "
            + "to the next day, the latest a lesson can end is 2359";
    public static final String MESSAGE_INVALID_LESSON_START_TIME = "Lessons have to start earlier than 2359,"
            + " we do not allow lessons to overflow to the next day.";
    public static final String MESSAGE_INVALID_TIME_ORDER = "Invalid time order, start time must be before end time";

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
        checkArgument(isValidLesson(lesson));
        String[] lessonDayTimeArr = lesson.split("\\s+", 2);
        String[] timeArr = lessonDayTimeArr[1].split("-");
        this.lessonDay = Day.convertDayToEnum(lessonDayTimeArr[0].toLowerCase());
        this.startTime = LocalTime.parse(timeArr[0], TIME_FORMATTER);
        this.endTime = LocalTime.parse(timeArr[1], TIME_FORMATTER);

    }


    /**
     * Checks if the lesson end time is at a valid time.
     *
     * <p>A valid lesson end time should not be exactly at midnight (00:00).
     * This method ensures that lessons do not overflow to the next day.</p>
     *
     * @param endTime The {@code LocalTime} representing the lesson's end time.
     * @return {@code true} if the end time is valid (not 00:00), {@code false} otherwise.
     */
    public static boolean isLessonEndAtValidTime(LocalTime endTime) {
        return !endTime.equals(LocalTime.of(0, 0));
    }

    /**
     * Checks if the lesson start time is at a valid time.
     *
     * <p>A valid lesson start time must be before the last minute of the day (23:59).
     * This method ensures that lessons begin within the current day,
     * and do not start precisely at midnight or the very end of the day.</p>
     *
     * @param startTime The {@code LocalTime} representing the lesson's start time.
     * @return {@code true} if the start time is before 23:59, {@code false} otherwise.
     */
    public static boolean isLessonStartAtValidTime(LocalTime startTime) {
        return startTime.isBefore(LocalTime.of(23, 59));
    }

    /**
     * Validates the time range format (should be HHMM-HHMM 24-hour format).
     *
     * @param timeRange The time range to validate.
     * @return true if the time range is valid.
     */
    public static boolean isValidTimeRange(String timeRange) {
        return timeRange.matches(VALID_TIME_RANGE_REGEX);
    }

    /**
     * Checks if the specified start time is before the specified end time.
     *
     * <p>This method verifies that the start time occurs earlier than the end time,
     * ensuring a valid time order.</p>
     *
     * @param startTime The start time to check.
     * @param endTime The end time to compare against the start time.
     * @return {@code true} if the start time is before the end time; {@code false} otherwise.
     */
    public static boolean isValidTimeOrder(LocalTime startTime, LocalTime endTime) {
        return startTime.isBefore(endTime);
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
    public static boolean isValidLesson(String lesson) throws IllegalArgumentException {
        assert lesson != null;
        String[] lessonDayTimeArr = lesson.split("\\s+", 2);
        if (lessonDayTimeArr.length != 2) {
            return false;
        }

        String dayString = lessonDayTimeArr[0];
        String timeRange = lessonDayTimeArr[1];

        String[] timeArr = lessonDayTimeArr[1].split("-");
        LocalTime startTime = LocalTime.parse(timeArr[0], TIME_FORMATTER);
        LocalTime endTime = LocalTime.parse(timeArr[1], TIME_FORMATTER);

        return Day.isValidDay(dayString) && isValidTimeRange(timeRange)
                && isLessonStartAtValidTime(startTime) && isLessonEndAtValidTime(endTime)
                && isValidTimeOrder(startTime, endTime);
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
     * Checks if any two lessons in the provided list clash with each other.
     *
     * <p>This method iterates through lessons in and determines
     * if there is a timing conflict between any two lessons.</p>
     *
     * @param lessons A list of {@code Lesson} objects to check for lesson clashes within the given list.
     * @return {@code true} if any two lessons in the list have a timing conflict,
     *         {@code false} otherwise.
     */
    public static boolean hasClashingLessonWithinList(List<Lesson> lessons) {
        assert lessons != null;
        return IntStream.range(0, lessons.size())
                .anyMatch(i -> IntStream.range(i + 1, lessons.size())
                        .anyMatch(j -> isClashingWithOtherLesson(lessons.get(i), lessons.get(j))));
    }


    /**
     * Calculates the duration until the next occurrence of the lesson.
     * If the lesson is currently ongoing, this method returns a zero duration.
     *
     * <p>The duration calculation considers both the day of the week and the time of day.
     * If the current day matches the lesson day but the current time is after the lesson's end time,
     * the method assumes the next lesson will occur in one week from the current day.</p>
     *
     * @return A {@code Duration} representing the time left until the next lesson,
     *         or zero if the lesson is currently ongoing.
     */
    public Duration durationTillLesson() {
        if (this.isCurrentlyOngoing()) {
            return Duration.ofDays(0).plusHours(0).plusMinutes(0);
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        Day currentDay = Day.convertDayToEnum(currentDateTime.getDayOfWeek().toString().toLowerCase());
        LocalTime currentTime = currentDateTime.toLocalTime();

        int daysUntilLesson = (lessonDay.getValue() - currentDay.getValue() + 7) % 7;
        if (currentDay == lessonDay && currentTime.isAfter(endTime)) {
            daysUntilLesson = 7;
        }

        LocalDateTime nextLessonDateTime = currentDateTime
                .plusDays(daysUntilLesson)
                .withHour(startTime.getHour())
                .withMinute(startTime.getMinute());
        return Duration.between(currentDateTime, nextLessonDateTime);
    }


    /**
     * Determines if the lesson is currently ongoing.
     *
     * <p>A lesson is considered ongoing if the current day matches the lesson's scheduled day,
     * and the current time is between the lesson's start time and end time. If the current time
     * coincides exactly with either the lesson's start time or end time, it is still considered
     * to be ongoing, providing flexibility for timing precision.</p>
     *
     * @return {@code true} if the lesson is ongoing; {@code false} otherwise.
     */
    public boolean isCurrentlyOngoing() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Day currentDay = Day.convertDayToEnum(currentDateTime.getDayOfWeek().toString().toLowerCase());
        LocalTime currentTime = currentDateTime.toLocalTime();
        return currentDay.equals(lessonDay)
                && currentTime.isAfter(startTime)
                && currentTime.isBefore(endTime.plusMinutes(1));
    }

    /**
     * Returns the day on which this lesson occurs.
     *
     * @return The {@code Day} enum representing the day of the week for this lesson.
     */
    public Day getLessonDay() {
        return lessonDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Checks if this lesson's time range falls within a specified time range.
     *
     * @param otherTimeRange A time range in the format "HHmm-HHmm".
     * @return true if this lesson's time range is overlapping the specified time range.
     */
    public boolean checkOverlappingTimeRange(String otherTimeRange) {
        String[] times = otherTimeRange.split("-");
        LocalTime otherStartTime = LocalTime.parse(times[0], TIME_FORMATTER);
        LocalTime otherEndTime = LocalTime.parse(times[1], TIME_FORMATTER);

        return ((startTime.equals(otherStartTime) || startTime.isBefore(otherStartTime))
                && endTime.isAfter(otherStartTime))
                || (startTime.isBefore(otherEndTime)
                && (endTime.equals(otherEndTime) || endTime.isAfter(otherEndTime)))
                || ((startTime.equals(otherStartTime) || (startTime.isAfter(otherStartTime))
                && (endTime.equals(otherEndTime) || endTime.isBefore(otherEndTime))));
    }

    /**
     * Compares this lesson to another object for equality.
     * Returns {@code true} if the other object is a {@code Lesson} with the
     * same start and end times as this lesson.
     *
     * @param other The object to compare for equality.
     * @return {@code true} if the other object is a {@code Lesson} and has the same
     *         say, start and end times as this lesson, {@code false} otherwise.
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
        return otherLesson.lessonDay.equals(this.lessonDay)
                && otherLesson.startTime.equals(this.startTime)
                && otherLesson.endTime.equals(this.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonDay, startTime, endTime);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + getDayAndTime() + ']';
    }

    /**
     * @return A string with only day and time
     */
    public String getDayAndTime() {
        return lessonDay.toString() + " " + startTime.toString().replace(":", "") + "-"
                + endTime.toString().replace(":", "");
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
