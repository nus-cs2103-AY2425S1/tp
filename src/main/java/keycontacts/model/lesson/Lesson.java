package keycontacts.model.lesson;

import static keycontacts.commons.util.AppUtil.checkArgument;
import static keycontacts.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import keycontacts.commons.util.ToStringBuilder;

/**
 * Abstract class representing a Student's lesson in the student directory.
 * Guarantees: immutable, start time and end time are valid as declared in
 * {@link #isValidTimePair(Time, Time)}.
 */
public abstract class Lesson {

    public static final String MESSAGE_CONSTRAINTS = "Start time should be before end time";

    private final Time startTime;
    private final Time endTime;

    /**
     * Every field must be present and not null.
     */
    public Lesson(Time startTime, Time endTime) {
        requireAllNonNull(startTime, endTime);
        checkArgument(isValidTimePair(startTime, endTime), MESSAGE_CONSTRAINTS);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public abstract boolean isClashing(RegularLesson regularLesson);
    public abstract boolean isClashing(MakeupLesson makeupLesson);

    public static boolean isValidTimePair(Time startTime, Time endTime) {
        return startTime.getTime().isBefore(endTime.getTime());
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }

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
        return startTime.equals(otherLesson.startTime) && endTime.equals(otherLesson.endTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .toString();
    }

    /**
     * Returns a user-friendly display message of the lesson.
     */
    public String toDisplay() {
        return startTime.toString() + " - " + endTime.toString();
    }
}
