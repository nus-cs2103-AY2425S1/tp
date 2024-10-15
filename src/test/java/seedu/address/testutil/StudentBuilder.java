package seedu.address.testutil;

import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
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
    private Phone phone;
    private TutorialGroup tutorialGroup;
    private StudentNumber studentNumber;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_CONTACT_NUMBER);
        tutorialGroup = new TutorialGroup(DEFAULT_TUTORIAL_GROUP);
        studentNumber = new StudentNumber(DEFAULT_STUDENT_NUMBER);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
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
     * Sets the {@code Phone} of the {@code StudentTest} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
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
        return new Student(name, phone, tutorialGroup, studentNumber);
    }
}
