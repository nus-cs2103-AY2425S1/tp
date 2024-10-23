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

    /**
     * Returns true if any 2 lessons clash.
     */
    public static boolean lessonsClash(Lesson lesson1, Lesson lesson2) {
        if (lesson1 == null || lesson2 == null) {
            return false;
        }

        // Handle clash between two MakeupLessons
        if (lesson1 instanceof MakeupLesson && lesson2 instanceof MakeupLesson) {
            MakeupLesson ml1 = (MakeupLesson) lesson1;
            MakeupLesson ml2 = (MakeupLesson) lesson2;
            return ml1.getLessonDate().equals(ml2.getLessonDate())
                    && ml1.getStartTime().isNotAfter(ml2.getEndTime())
                    && ml1.getEndTime().isNotBefore(ml2.getStartTime());
        }

        // Handle clash between two RegularLessons
        if (lesson1 instanceof RegularLesson && lesson2 instanceof RegularLesson) {
            RegularLesson rl1 = (RegularLesson) lesson1;
            RegularLesson rl2 = (RegularLesson) lesson2;
            return rl1.getLessonDay().equals(rl2.getLessonDay())
                    && rl1.getStartTime().isNotAfter(rl2.getEndTime())
                    && rl1.getEndTime().isNotBefore(rl2.getStartTime());
        }

        // Handle clash between MakeupLesson and RegularLesson
        if (lesson1 instanceof MakeupLesson && lesson2 instanceof RegularLesson) {
            MakeupLesson ml = (MakeupLesson) lesson1;
            RegularLesson rl = (RegularLesson) lesson2;
            return ml.getLessonDate().convertToDay().equals(rl.getLessonDay())
                    && ml.getStartTime().isNotAfter(rl.getEndTime())
                    && ml.getEndTime().isNotBefore(rl.getStartTime());
        }

        if (lesson1 instanceof RegularLesson && lesson2 instanceof MakeupLesson) {
            RegularLesson rl = (RegularLesson) lesson1;
            MakeupLesson ml = (MakeupLesson) lesson2;
            return rl.getLessonDay().equals(ml.getLessonDate().convertToDay())
                    && rl.getStartTime().isNotAfter(ml.getEndTime())
                    && rl.getEndTime().isNotBefore(ml.getStartTime());
        }

        return false;
    }

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
