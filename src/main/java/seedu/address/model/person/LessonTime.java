package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Represents a lesson time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLessonTime(String)}
 */
public class LessonTime {
    public static final String MESSAGE_CONSTRAINTS =
            "Lesson should be in format 'day:HH:mm' (e.g. mon:16:00), \n"
                    + "where 'day' is a three-letter abbreviation (mon, tue, wed, thu, fri, sat, sun) \n"
                    + "and time is in 24-hour format (HH:mm) between 00:00 and 23:59.";

    public static final String VALIDATION_REGEX = "(?i)^(mon|tue|wed|thu|fri|sat|sun):([01]\\d|2[0-3]):[0-5]\\d$";

    private static final Map<String, DayOfWeek> DAY_ABBREVIATION_MAP = Map.of(
            "mon", DayOfWeek.MONDAY,
            "tue", DayOfWeek.TUESDAY,
            "wed", DayOfWeek.WEDNESDAY,
            "thu", DayOfWeek.THURSDAY,
            "fri", DayOfWeek.FRIDAY,
            "sat", DayOfWeek.SATURDAY,
            "sun", DayOfWeek.SUNDAY
    );

    public final LocalTime time;

    public final DayOfWeek day;

    public final String value;

    /**
     * Constructs a {@code LessonTime}.
     *
     * @param lessonTime A valid lessonTime.
     */
    public LessonTime(String lessonTime) {
        requireNonNull(lessonTime);
        checkArgument(isValidLessonTime(lessonTime), MESSAGE_CONSTRAINTS);
        this.value = lessonTime;

        String[] parts = lessonTime.split(":");
        String dayPart = parts[0];
        this.day = parseDay(dayPart.toLowerCase());

        String timePart = parts[1] + ":" + parts[2];
        this.time = LocalTime.parse(timePart, DateTimeFormatter.ofPattern("HH:mm"));
    }

    /**
     * Returns true if a given string is a valid lessonTime.
     */
    public static boolean isValidLessonTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }
    private static DayOfWeek parseDay(String day) {
        DayOfWeek dayOfWeek = DAY_ABBREVIATION_MAP.get(day.toLowerCase());
        if (dayOfWeek == null) {
            throw new IllegalArgumentException("Invalid day abbreviation: " + day);
        }
        return dayOfWeek;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LessonTime)) {
            return false;
        }

        LessonTime otherDay = (LessonTime) other;
        return day.equals(otherDay.day) && time.equals(otherDay.time);
    }

    @Override
    public int hashCode() {
        return day.hashCode() * 31 + time.hashCode();
    }

    @Override
    public String toString() {
        return day + " " + time;
    }
}
