package seedu.address.testutil;

import java.util.HashSet;

import seedu.address.model.student.Name;
import seedu.address.model.student.PresentDates;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final PresentDates DEFAULT_PRESENT_DATES = new PresentDates(new HashSet<>());
    public static final String DEFAULT_STUDENT_ID = "1000";
    public static final String DEFAULT_TUTORIAL_ID = "1001";

    private Name name;
    private PresentDates presentDates;
    private StudentId studentId;
    private TutorialId tutorialId;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        presentDates = DEFAULT_PRESENT_DATES;
        studentId = new StudentId(DEFAULT_STUDENT_ID);
        tutorialId = TutorialId.of(DEFAULT_TUTORIAL_ID);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        presentDates = studentToCopy.getPresentDates();
        studentId = studentToCopy.getStudentId();
        tutorialId = studentToCopy.getTutorialId();
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code PresentDates} of the {@code Student} that we are building.
     */
    public StudentBuilder withPresentDates(PresentDates presentDates) {
        this.presentDates = presentDates;
        return this;
    }

    public Student build() {
        return new Student(name, studentId, tutorialId, presentDates);
    }

    /**
     * Sets the {@code StudentId} of the {@code Student} that we are building.
     */
    public StudentBuilder withStudentId(String studentId) {
        this.studentId = new StudentId(studentId);
        return this;
    }

    /**
     * Sets the {@code TutorialId} of the {@code Student} that we are building.
     */
    public StudentBuilder withTutorialId(String tutorialId) {
        this.tutorialId = TutorialId.of(tutorialId);
        return this;
    }
}
