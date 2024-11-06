package seedu.address.model.lesson;

import seedu.address.model.person.Name;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutee;
import seedu.address.model.person.Tutor;

/**
 * Represents a lesson taught by a tutor and a tutee with matching subjects.
 */
public class Lesson {

    private final Tutor tutor;
    private final Tutee tutee;
    private final Subject subject;

    /**
     * Constructs a Lesson object.
     *
     * @param tutor   The Tutor object.
     * @param tutee   The Tutee object.
     * @param subject
     */
    public Lesson(Tutor tutor, Tutee tutee, Subject subject) {
        this.tutor = tutor;
        this.tutee = tutee;
        this.subject = subject;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public Tutee getTutee() {
        return tutee;
    }

    public Subject getSubject() {
        return subject;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return this.getTutor().equals(otherLesson.getTutor())
                && this.getTutee().equals(otherLesson.getTutee())
                && this.getSubject().equals(otherLesson.getSubject());
    }

    public Name getTutorName() {
        return tutor.getName();
    }

    public Name getTuteeName() {
        return tutee.getName();
    }

    @Override
    public String toString() {
        return String.format("Lesson: tutor %s is teaching tutee %s %s",
                tutor.getName(), tutee.getName(), subject);
    }

}
