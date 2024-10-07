package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Student in teletutor.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;
    private final ContactNumber contactNumber;
    private final TutorialGroup tutorialGroup;
    private final StudentNumber studentNumber;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, ContactNumber contactNumber, TutorialGroup tutorialGroup, StudentNumber studentNumber) {
        requireAllNonNull(name, contactNumber, tutorialGroup, studentNumber);
        this.name = name;
        this.contactNumber = contactNumber;
        this.tutorialGroup = tutorialGroup;
        this.studentNumber = studentNumber;
    }

    public Name getName() {
        return name;
    }

    public ContactNumber getContactNumber() {
        return contactNumber;
    }

    public TutorialGroup getTutorialGroup() {
        return tutorialGroup;
    }

    public StudentNumber getStudentNumber() {
        return studentNumber;
    }

    /**
     * Returns true if both students have the same student number.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.studentNumber.equals(studentNumber);
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student otherStudent)) {
            return false;
        }

        return otherStudent.name.equals(name)
                && otherStudent.contactNumber.equals(contactNumber)
                && otherStudent.tutorialGroup.equals(tutorialGroup)
                && otherStudent.studentNumber.equals(studentNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("contactNumber", contactNumber)
                .add("tutorialGroup", tutorialGroup)
                .add("studentNumber", studentNumber)
                .toString();
    }
}
