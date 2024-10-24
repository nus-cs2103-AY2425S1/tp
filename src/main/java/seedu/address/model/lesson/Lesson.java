package seedu.address.model.lesson;

import seedu.address.model.person.Name;
import seedu.address.model.person.Tutee;
import seedu.address.model.person.Tutor;

/**
 * Represents a lesson taught by a tutor and a tutee with matching subjects.
 */
public class Lesson {

    private final Tutor tutor;
    private final Tutee tutee;

    /**
     * Constructs a Lesson object.
     *
     * @param tutor The Tutor object.
     * @param tutee The Tutee object.
     */
    public Lesson(Tutor tutor, Tutee tutee) {
        this.tutor = tutor;
        this.tutee = tutee;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public Tutee getTutee() {
        return tutee;
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
                && this.getTutee().equals(otherLesson.getTutee());
    }

    public Name getTutorName() {
        return tutor.getName();
    }

    public Name getTuteeName() {
        return tutee.getName();
    }

    @Override
    public String toString() {
        return "Lesson: "; // TODO
    }

}
