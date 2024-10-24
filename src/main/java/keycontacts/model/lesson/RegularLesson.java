package keycontacts.model.lesson;

import static keycontacts.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import keycontacts.commons.util.ToStringBuilder;

/**
 * Class representing a Student's lesson in the student directory.
 * Guarantees: immutable, start time and end time are valid as declared in
 * {@link keycontacts.model.lesson.Time#isValidTime(String)}, lesson day is valid as declared in
 * {@link keycontacts.model.lesson.Day#isValidDay(String)}.
 */
public class RegularLesson extends Lesson {
    public static final String MESSAGE_NO_REGULAR_LESSON = "No Regular Lesson scheduled";

    private final Day lessonDay;

    /**
     * Every field must be present and not null.
     */
    public RegularLesson(Day lessonDay, Time startTime, Time endTime) {
        super(startTime, endTime);
        requireAllNonNull(lessonDay);
        this.lessonDay = lessonDay;
    }

    public Day getLessonDay() {
        return lessonDay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonDay, getStartTime(), getEndTime());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RegularLesson)) {
            return false;
        }

        RegularLesson otherLesson = (RegularLesson) other;
        return super.equals(otherLesson) && lessonDay.equals(otherLesson.lessonDay);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("lessonDay", lessonDay)
                .add("startTime", getStartTime())
                .add("endTime", getEndTime())
                .toString();
    }

    @Override
    public String toDisplay() {
        return lessonDay.toString().substring(0, 1).toUpperCase()
                + lessonDay.toString().substring(1).toLowerCase() + ", " + super.toDisplay();
    }

    /**
     * Returns true if the lesson falls on the given day and time.
     */
    public boolean isOnDayAndTime(Day day, Time time) {
        return this.getStartTime().equals(time) && this.getLessonDay().equals(day);
    }

    @Override
    public boolean isClashing(RegularLesson other) {
        return this != other
                && this.getLessonDay().equals(other.getLessonDay())
                && this.getStartTime().isBefore(other.getEndTime())
                && this.getEndTime().isAfter(other.getStartTime());
    }

    @Override
    public boolean isClashing(MakeupLesson other) {
        return this.getLessonDay().equals(other.getLessonDate().convertToDay())
                && this.getStartTime().isBefore(other.getEndTime())
                && this.getEndTime().isAfter(other.getStartTime());
    }
}
