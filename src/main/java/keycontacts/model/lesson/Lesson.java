package keycontacts.model.lesson;

import static keycontacts.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import keycontacts.commons.util.ToStringBuilder;

/**
 * Abstract class representing a Student's lesson in the student directory.
 * Guarantees: immutable, has a start time and end time are valid as declared in
 * {@link keycontacts.model.lesson.Time#isValidTime(String)}.
 */
public abstract class Lesson {

    private final Time startTime;
    private final Time endTime;

    /**
     * Every field must be present and not null.
     */
    public Lesson(Time startTime, Time endTime) {
        requireAllNonNull(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
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
