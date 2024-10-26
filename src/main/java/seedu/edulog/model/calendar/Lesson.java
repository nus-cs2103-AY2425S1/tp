package seedu.edulog.model.calendar;

import static seedu.edulog.commons.util.AppUtil.checkArgument;
import static seedu.edulog.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.edulog.commons.util.ToStringBuilder;

/**
 * Lesson class, representing a weekly recurring time slot for a lesson.
 */
public class Lesson {

    // Denotes the valid days of the week, spelt in full.
    public static final ArrayList<String> DAYS_OF_THE_WEEK = new ArrayList<String>(
        List.of("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"));

    public static final String INVALID_DAY_OF_WEEK =
            "Day of the week must be spelt as "
            + "'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', or 'Sunday'"
            + "only (non case-sensitive).";

    private Description description;
    private DayOfWeek startDay;
    private LessonTime times;

    /**
     * Every field must be present and not null.
     */
    public Lesson(Description description, DayOfWeek startDay, LessonTime times) {
        requireAllNonNull(description, startDay, times);

        this.description = description;
        this.startDay = startDay;
        this.times = times;
    }

    public Description getDescription() {
        return description;
    }

    public DayOfWeek getStartDay() {
        return startDay;
    }

    /**
     * Getter method that attains the LocalTime representation of the start time for time-based methods. <br> <br>
     * NOTE: Avoid breaking abstraction here - do not call this getter function for functional code!
     * If you want to attain a printable representation of time, please use {@link #getFormattedStartTime()}
     * instead, which ensures that times can be presented and stored in an acceptable 24-hour format.
     */
    public LocalTime getStartTime() {
        return times.getStartTime();
    }

    /**
     * Getter method that attains the LocalTime representation of the end time for time-based methods. <br> <br>
     * NOTE: Avoid breaking abstraction here - do not call this getter function for functional code!
     * If you want to attain a printable representation of time, please use {@link #getFormattedStartTime()}
     * instead, which ensures that times can be presented and stored in an acceptable 24-hour format.
     */
    public LocalTime getEndTime() {
        return times.getEndTime();
    }

    /**
     * Getter method that attains the start time in a format acceptable as both user input and storage input.
     * Example: "1100", "2359".
     */
    public String getFormattedStartTime() {
        return times.getFormattedStartTime();
    }

    /**
     * Getter method that attains the end time in a format acceptable as both user input and storage input.
     * Example: "1100", "2359".
     */
    public String getFormattedEndTime() {
        return times.getFormattedEndTime();
    }

    // Validator methods. TODO: OOP-ize =========================================

    /**
     * Checks if the day of week matches any of the 7 days of the week, spelt in full, example "Wednesday".
     * Non-case sensitive.
     */
    public static boolean checkValidDayOfWeek(String dayOfWeek) {
        return DAYS_OF_THE_WEEK.contains(dayOfWeek.toLowerCase());
    }

    /**
     * Returns true if lesson has the given description
     */
    public boolean isDescription(Description description) {
        return this.description.equals(description);
    }

    /**
     * Returns true if both lessons have the same description. A lesson's primary identity is its description and is
     * exclusively checked with each other. Two lessons with the same description will be marked as the same lesson
     * even if their secondary characteristics (like day of the week) vary.
     */
    public boolean isSameLesson(Lesson otherLesson) {
        return otherLesson != null
                && otherLesson.description.equals(description);
    }

    // Processor methods. TODO: OOP-ize =========================================

    /**
     * Generates a DayOfWeek based on a String representation.
     * Callers are advised to use {@link #checkValidDayOfWeek(String)} first and handle abnormal use cases themselves.
     * Otherwise, an error message will be generated that may be visible to the user.
     */
    public static DayOfWeek processDayOfWeek(String dayOfWeek) {
        checkArgument(checkValidDayOfWeek(dayOfWeek), Lesson.INVALID_DAY_OF_WEEK);
        switch (dayOfWeek.toLowerCase()) {
        case "monday":
            return DayOfWeek.MONDAY;
        case "tuesday":
            return DayOfWeek.TUESDAY;
        case "wednesday":
            return DayOfWeek.WEDNESDAY;
        case "thursday":
            return DayOfWeek.THURSDAY;
        case "friday":
            return DayOfWeek.FRIDAY;
        case "saturday":
            return DayOfWeek.SATURDAY;
        case "sunday":
            return DayOfWeek.SUNDAY;
        default:
            // because this is prior to a validity check, this use of an unchecked runtime exception is
            // warranted here as it denotes a gross failure of internal data processing.
            throw new RuntimeException("Something went horribly wrong when parsing a Lesson.");
        }
    }

    // Identity methods ========================================================================================

    /**
     * Returns true if both lessons have the same identity and data fields.
     * This defines a stronger notion of equality between two lessons, where all fields of the Lesson must match.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return description.equals(otherLesson.description)
                && startDay.equals(otherLesson.startDay)
                && times.equals(otherLesson.times);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, startDay, times);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("description", description)
                .add("From", startDay + " " + getFormattedStartTime())
                .add("To", startDay.plus(spansTwoDays() ? 1 : 0) + " " + getFormattedEndTime())
                .toString();
    }
}
