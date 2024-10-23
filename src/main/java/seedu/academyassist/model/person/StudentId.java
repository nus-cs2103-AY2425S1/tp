package seedu.academyassist.model.person;

import static java.util.Objects.requireNonNull;

import seedu.academyassist.commons.util.AppUtil;

/**
 * Represents a Person's student id in the management system.
 */
public class StudentId {
    public static final String MESSAGE_CONSTRAINTS =
            "Student ID should only contain alphanumeric characters and must be "
            + "S followed by a 5-digit number between 00001 and 99999";
    public static final StudentId TEMPORARY_STUDENT_ID = new StudentId("S99999");
    public static final String VALIDATION_REGEX = "^S(?!00000)\\d{5}$";
    public final String value;

    /**
     * Constructs a {@code StudentId}.
     * @param studentId A valid student id.
     */
    public StudentId(String studentId) {
        requireNonNull(studentId);
        AppUtil.checkArgument(isValidStudentId(studentId));
        this.value = studentId;
    }

    /**
     * Returns true if a given string is a valid studentId.
     */
    public static boolean isValidStudentId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Generates and returns a new unassigned student id.
     */
    public static StudentId generateNewStudentId(int studentCount) {
        return new StudentId(String.format("S%05d", studentCount));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null
        if (!(other instanceof StudentId)) {
            return false;
        }

        StudentId otherStudentId = (StudentId) other;
        return value.equals(otherStudentId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
