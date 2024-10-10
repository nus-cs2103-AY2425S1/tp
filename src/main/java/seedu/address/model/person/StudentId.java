package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a student's ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStudentId(String)}
 */
public class StudentId {

    public static final String MESSAGE_CONSTRAINTS =
        "Student ID should be of the form EXXXXXXX, where X represents a digit, and it should not be blank.";

    /*
     * A student ID should begin with E, followed by 7 digits.
     */
    public static final String VALIDATION_REGEX = "^E\\d{7}$";

    public final String value;

    /**
     * Constructs a {@code studentId}
     * @param studentId A valid student ID.
     */
    public StudentId(String studentId) {
        requireNonNull(studentId);
        checkArgument(isValidStudentId(studentId), MESSAGE_CONSTRAINTS);
        this.value = studentId;
    }

    public static boolean isValidStudentId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof StudentId s) {
            return this.value.equals(s.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
