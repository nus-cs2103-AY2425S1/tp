package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidClass(String)}
 */
public class StudentClass {

    public static final String MESSAGE_CONSTRAINTS =
            "Student's class names should be alphanumeric and a maximum of 50 characters"; // alphanumeric
    // and special characters
    public static final String VALIDATION_REGEX = "\\p{Alnum}{1,50}";

    public final String value;

    /**
     * Constructs an {@code studentClass}.
     *
     * @param studentClass A valid student class.
     */
    public StudentClass(String studentClass) {
        requireNonNull(studentClass);
        checkArgument(isValidClass(studentClass), MESSAGE_CONSTRAINTS);
        value = studentClass;
    }

    /**
     * Returns if a given string is a valid class.
     */
    public static boolean isValidClass(String test) {
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
        if (!(other instanceof StudentClass)) {
            return false;
        }

        StudentClass otherClass = (StudentClass) other;
        return value.equals(otherClass.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
