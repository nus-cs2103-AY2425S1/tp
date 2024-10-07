package seedu.address.testutil;

import seedu.address.model.student.ContactNumber;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.student.TutorialGroup;

/**
 * A utility class to help with building StudentTest objects.
 */
public class StudentBuilder {
    public static final String DEFAULT_NAME = "Hugh Jackman";
    public static final String DEFAULT_CONTACT_NUMBER = "91234567";
    public static final String DEFAULT_TUTORIAL_GROUP = "G01";
    public static final String DEFAULT_STUDENT_NUMBER = "A1234567A";

    private Name name;
    private ContactNumber contactNumber;
    private TutorialGroup tutorialGroup;
    private StudentNumber studentNumber;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        contactNumber = new ContactNumber(DEFAULT_CONTACT_NUMBER);
        tutorialGroup = new TutorialGroup(DEFAULT_TUTORIAL_GROUP);
        studentNumber = new StudentNumber(DEFAULT_STUDENT_NUMBER);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        contactNumber = studentToCopy.getContactNumber();
        tutorialGroup = studentToCopy.getTutorialGroup();
        studentNumber = studentToCopy.getStudentNumber();
    }

    /**
     * Sets the {@code Name} of the {@code StudentTest} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code ContactNumber} of the {@code StudentTest} that we are building.
     */
    public StudentBuilder withContactNumber(String contactNumber) {
        this.contactNumber = new ContactNumber(contactNumber);
        return this;
    }

    /**
     * Sets the {@code TutorialGroup} of the {@code StudentTest} that we are building.
     */
    public StudentBuilder withTutorialGroup(String tutorialGroup) {
        this.tutorialGroup = new TutorialGroup(tutorialGroup);
        return this;
    }

    /**
     * Sets the {@code StudentNumber} of the {@code StudentTest} that we are building.
     */
    public StudentBuilder withStudentNumber(String studentNumber) {
        this.studentNumber = new StudentNumber(studentNumber);
        return this;
    }

    public Student build() {
        return new Student(name, contactNumber, tutorialGroup, studentNumber);
    }
}
