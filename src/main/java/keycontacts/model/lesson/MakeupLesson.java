package keycontacts.model.lesson;

import static keycontacts.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import keycontacts.commons.util.ToStringBuilder;

/**
 * Represents a makeup lesson which happens if a student misses a lesson.
 */
public class MakeupLesson extends Lesson {

    private final Date lessonDate;

    /**
     * Every field must be present and not null.
     */
    public MakeupLesson(Date lessonDate, Time startTime, Time endTime) {
        super(startTime, endTime);
        requireAllNonNull(lessonDate);
        this.lessonDate = lessonDate;
    }

    public Date getLessonDate() {
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
        return lessonDate.equals(otherLesson.lessonDate)
                && getStartTime().equals(otherLesson.getStartTime())
                && getEndTime().equals(otherLesson.getEndTime());
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
        return lessonDate.toDisplay() + ", " + super.toDisplay();
    }

    /**
     * Returns a makeup lesson set containing the lists of strings given.
     */
    public static Set<MakeupLesson> getMakeupLessonSet(String[] dates, String[] startTimes, String[] endTimes) {
        return IntStream.range(0, dates.length)
                .mapToObj(i -> new MakeupLesson(new Date(dates[i]), new Time(startTimes[i]), new Time(endTimes[i])))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isClashing(RegularLesson other) {
        return this.getLessonDate().convertToDay().equals(other.getLessonDay())
                && this.getStartTime().isBefore(other.getEndTime())
                && this.getEndTime().isAfter(other.getStartTime());
    }

    @Override
    public boolean isClashing(MakeupLesson other) {
        return this != other
                && this.getLessonDate().equals(other.getLessonDate())
                && this.getStartTime().isBefore(other.getEndTime())
                && this.getEndTime().isAfter(other.getStartTime());
    }
}
