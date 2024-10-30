package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Contact's student status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStudentStatus(String)}
 */
public class StudentStatus {

    public static final String MESSAGE_CONSTRAINTS = "Student statuses must take one of the values below: \n"
            + "undergraduate x, where x: An integer value between 1 and 6 inclusive\n"
            + "masters\n"
            + "phd";

    public static final String VALIDATION_REGEX = "^((undergraduate \\b[1-6]\\b)|masters|phd)$";

    public final String value;

    /**
     * Constructs an {@code StudentStatus}.
     *
     * @param studentStatus A valid student status.
     */
    public StudentStatus(String studentStatus) {
        requireNonNull(studentStatus);
        checkArgument(isValidStudentStatus(studentStatus), MESSAGE_CONSTRAINTS);
        value = studentStatus;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidStudentStatus(String test) {
        return test.matches(VALIDATION_REGEX);
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

        // instanceof handles nulls
        if (!(other instanceof StudentStatus)) {
            return false;
        }

        StudentStatus otherStudentStatus = (StudentStatus) other;
        return value.equals(otherStudentStatus.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
