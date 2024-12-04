package seedu.address.testutil;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutee;
import seedu.address.model.person.Tutor;

/**
 * A utility class to help with building Person objects.
 */
public class LessonBuilder {

    public static final Tutor DEFAULT_TUTOR = new TutorBuilder().build();
    public static final Tutee DEFAULT_TUTEE = new TuteeBuilder().build();
    public static final Subject DEFAULT_SUBJECT = new Subject("English");

    private Tutor tutor;
    private Tutee tutee;
    private Subject subject;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public LessonBuilder() {
        tutor = DEFAULT_TUTOR;
        tutee = DEFAULT_TUTEE;
        subject = DEFAULT_SUBJECT;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        tutor = lessonToCopy.getTutor();
        tutee = lessonToCopy.getTutee();
        subject = lessonToCopy.getSubject();
    }

    /**
     * Sets the {@code Tutor} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withTutor(Tutor tutor) {
        this.tutor = tutor;
        return this;
    }

    /**
     * Sets the {@code Tutee} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withAddress(Tutee tutee) {
        this.tutee = tutee;
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }

    public Lesson build() {
        return new Lesson(tutor, tutee, subject);
    }

}
