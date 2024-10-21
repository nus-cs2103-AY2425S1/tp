package seedu.address.model.person.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidID(String)}
 */
public class StudentID {

    public static final String MESSAGE_CONSTRAINTS =
            "Student ID should be of the format \"A0123456X\"";
    public static final String VALIDATION_REGEX = "A\\d{7}[A-Z]";

    public final String value;

    /**
     * Constructs a {@code StudentID}.
     *
     * @param studentID A valid student ID.
     */
    public StudentID(String studentID) {
        requireNonNull(studentID);
        checkArgument(isValidID(studentID), MESSAGE_CONSTRAINTS);
        value = studentID;
    }

    /**
     * Returns true if a given string is a valid student id.
     */
    public static boolean isValidID(String test) {
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
        if (!(other instanceof StudentID)) {
            return false;
        }

        StudentID otherStudentID = (StudentID) other;
        return value.equals(otherStudentID.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

