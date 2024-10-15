package keycontacts.model.lesson;

import static keycontacts.commons.util.CollectionUtil.requireAllNonNull;

import keycontacts.commons.util.ToStringBuilder;

/**
 *  Class representing a Student's cancelled lesson in the student directory.
 *  Guarantees: immutable, start time and end time are valid as declared in
 *  {@link #isValidTimePair(Time, Time)}.
 */
public class CancelledLesson extends Lesson {
    private final Date lessonDate;

    /**
     * Constructs a {@code CancelledLesson} object.
     *
     * @param lessonDate The lesson date of the cancelled lesson
     * @param startTime The start time of the cancelled lesson
     * @param endTime The end time of the cancelled lesson
     */
    public CancelledLesson(Date lessonDate, Time startTime, Time endTime) {
        super(startTime, endTime);
        requireAllNonNull(lessonDate);
        this.lessonDate = lessonDate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CancelledLesson)) {
            return false;
        }

        CancelledLesson otherLesson = (CancelledLesson) other;
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
        return lessonDate.toString().substring(0, 1).toUpperCase()
                + lessonDate.toString().substring(1).toLowerCase() + ", " + super.toDisplay();
    }
}
