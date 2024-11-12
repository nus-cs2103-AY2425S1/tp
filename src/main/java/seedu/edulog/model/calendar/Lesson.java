package seedu.edulog.model.calendar;

import static seedu.edulog.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.edulog.commons.util.ToStringBuilder;

/**
 * Lesson class, representing a weekly recurring time slot for a lesson.
 */
public class Lesson {
    private Description description;
    private Day startDay;
    private LessonTime startTime;

    private LessonTime endTime;

    /**
     * Every field must be present and not null.
     */
    public Lesson(Description description, Day startDay, LessonTime startTime, LessonTime endTime) {
        requireAllNonNull(description, startDay, startTime, endTime);

        this.description = description;
        this.startDay = startDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Description getDescription() {
        return description;
    }

    public Day getStartDay() {
        return startDay;
    }

    /**
     * Getter method that attains the lesson's start time. <br> <br>
     * NOTE: Avoid breaking abstraction here - do not call this getter function for non-test code.
     * If you want to attain a printable representation of time, please use {@link #getFormattedStartTime()}
     * instead, which ensures that times can be presented and stored in an acceptable 24-hour format.
     */
    public LessonTime getStartTime() {
        return startTime;
    }

    /**
     * Getter method that attains the lesson's end time. <br> <br>
     * NOTE: Avoid breaking abstraction here - do not call this getter function for non-test code.
     * If you want to attain a printable representation of time, please use {@link #getFormattedStartTime()}
     * instead, which ensures that times can be presented and stored in an acceptable 24-hour format.
     */
    public LessonTime getEndTime() {
        return endTime;
    }

    /**
     * Getter method that attains the start time in a format acceptable as both user input and storage input.
     * Example: "1100", "2359".
     */
    public String getFormattedStartTime() {
        return startTime.getFormattedTime();
    }

    /**
     * Getter method that attains the end time in a format acceptable as both user input and storage input.
     * Example: "1100", "2359".
     */
    public String getFormattedEndTime() {
        return endTime.getFormattedTime();
    }

    // Validator methods ===========================================================================================

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

    // Utility, Identity, and printing methods ===================================================================
    private boolean spansTwoDays() {
        return LessonTime.spansTwoDays(startTime, endTime);
    }

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
                .add("description", description.toString())
                .add("From", startDay + " " + getFormattedStartTime())
                .add("To",
                    startDay.plus(spansTwoDays() ? 1 : 0) + " " + getFormattedEndTime())
                .toString();
    }
}
