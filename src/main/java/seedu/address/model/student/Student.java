package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;

    // Data fields
    private final StudentId studentId;
    private final TutorialClass tutorialClass;
    private final PresentDates presentDates;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, StudentId studentId, TutorialClass tutorialClass, PresentDates presentDates) {
        requireAllNonNull(name, studentId, tutorialClass);
        this.name = name;
        this.presentDates = presentDates != null ? presentDates : new PresentDates(new ArrayList<>());
        this.studentId = studentId;
        this.tutorialClass = tutorialClass;
    }

    public Name getName() {
        return name;
    }
    public PresentDates getPresentDates() {
        return presentDates;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public TutorialClass getTutorialClass() {
        return tutorialClass;
    }

    /**
     * Returns true if both students have the same name.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName());
    }

    /**
     * Returns true if both students have the same student id.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudentId(StudentId otherStudentId) {
        if (otherStudentId == this.studentId) {
            return true;
        }

        return otherStudentId != null
                && otherStudentId.equals(getStudentId());
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

        // instanceof handles nulls
        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return name.equals(otherStudent.name)
                && studentId.equals(otherStudent.studentId)
                && tutorialClass.equals(otherStudent.tutorialClass);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, studentId, tutorialClass);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("student id", studentId)
                .add("tutorial class", tutorialClass)
                .toString();
    }

}
