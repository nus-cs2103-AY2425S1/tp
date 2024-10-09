package seedu.address.model.calendar;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Lesson class, representing a weekly recurring time slot for a lesson.
 */
public class Lesson {
    public static final String NO_SAME_TIME =
            "Lessons cannot start and end at the same time.";

    public static final DateTimeFormatter FORMAT_24H = DateTimeFormatter.ofPattern("HHmm");

    String description;
    DayOfWeek startDay;
    LocalTime startTime;
    LocalTime endTime;

    /**
     * Every field must be present and not null.
     */
    public Lesson(String description, DayOfWeek startDay, LocalTime startTime, LocalTime endTime) {
        requireAllNonNull(description, startDay, startTime, endTime);

        checkArgument(checkValidTimes(startTime, endTime), NO_SAME_TIME);

        this.description = description;
        this.startDay = startDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDescription() { return description; }

    public DayOfWeek getStartDay() { return startDay; }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Checks that lesson times are not ambiguous, i.e. not the same start and end time.
     */
    private static Boolean checkValidTimes(LocalTime time1, LocalTime time2) {
        return !time1.equals(time2);
    }


    /**
     * Returns the duration of the lesson in minutes, ranging from 1 to 1439.
     */
    public int getDuration() {
        return (int) startTime.until(endTime, ChronoUnit.MINUTES);
    }

    /**
     * Returns true if both lessons have the same description
     */
    public boolean isSameLesson(Lesson otherLesson) {
        return otherLesson != null
                && otherLesson.description.equals(description);
    }

    /**
     * Returns true if both lessons have the same identity and data fields.
     * This defines a stronger notion of equality between two lessons.
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
                && startTime.equals(otherLesson.startTime)
                && endTime.equals(otherLesson.endTime);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, startDay, startTime, endTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("description", description)
                .add("Start day: ", startDay)
                .add("Time: ", startTime.format(FORMAT_24H) + "H to " + endTime.format(FORMAT_24H) + "H")
                .toString();
    }
}
