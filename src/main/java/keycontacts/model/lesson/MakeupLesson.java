package keycontacts.model.lesson;

import static keycontacts.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;

import keycontacts.commons.util.ToStringBuilder;

/**
 * Represents a makeup lesson which happens if a student misses a lesson.
 */
public class MakeupLesson extends Lesson {

    private final LocalDate lessonDate;

    /**
     * Every field must be present and not null.
     */
    public MakeupLesson(LocalDate lessonDate, Time startTime, Time endTime) {
        super(startTime, endTime);
        requireAllNonNull(lessonDate);
        this.lessonDate = lessonDate;
    }

    public LocalDate getLessonDate() {
        return lessonDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonDate, getStartTime(), getEndTime());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MakeupLesson)) {
            return false;
        }

        MakeupLesson otherLesson = (MakeupLesson) other;
        return super.equals(otherLesson) && lessonDate.equals(otherLesson.lessonDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("lessonDate", lessonDate)
                .add("startTime", getStartTime())
                .add("endTime", getEndTime())
                .toString();
    }

    @Override
    public String toDisplay() {
        return lessonDate.toString() + " " + super.toDisplay();
    }
}
