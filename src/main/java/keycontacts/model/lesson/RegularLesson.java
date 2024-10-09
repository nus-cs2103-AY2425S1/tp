package keycontacts.model.lesson;

import java.util.Objects;

import keycontacts.commons.util.ToStringBuilder;

/**
 * Abstract class representing a Student's lesson in the student directory.
 * Guarantees: immutable, start time and end time are valid as declared in
 * {@link keycontacts.model.lesson.Time#isValidTime(String)}, lesson day is valid as declared in
 * {@link keycontacts.model.lesson.Day#isValidDay(String)}.
 */
public class RegularLesson extends Lesson {

    private final Day lessonDay;

    public RegularLesson(Day lessonDay, Time startTime, Time endTime) {
        super(startTime, endTime);
        this.lessonDay = lessonDay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonDay, getStartTime(), getEndTime());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("lessonDay", lessonDay)
                .add("startTime", getStartTime())
                .add("endTime", getEndTime())
                .toString();
    }
}
