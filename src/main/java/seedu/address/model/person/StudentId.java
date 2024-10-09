package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class StudentId {

    public static final String MESSAGE_CONSTRAINTS =
        "Student ID should be of the form EXXXXXXX, where X represents a digit, and it should not be blank.";

    /*
     * A student ID should begin with E, followed by 7 digits.
     */
    public static final String VALIDATION_REGEX = "^E\\d{7}$";

    public final String studentId;

    /**
     * Constructs a {@code studentId}
     * @param studentId A valid student ID.
     */
    public StudentId(String studentId) {
        requireNonNull(studentId);
        checkArgument(isValidStudentId(studentId), MESSAGE_CONSTRAINTS);
        this.studentId = studentId;
    }

    public static boolean isValidStudentId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof StudentId s) {
            return this.studentId.equals(s.studentId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.studentId.hashCode();
    }
}
