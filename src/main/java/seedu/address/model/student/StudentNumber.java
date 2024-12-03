package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's student number in teletutor.
 * Guarantees: immutable; is valid as declared in {@link #isValidStudentNumber(String)}
 */
public class StudentNumber {
    public static final String MESSAGE_CONSTRAINTS =
            """
            Student numbers should only have two alphabets (one at the beginning and one at the end),
            and there should be strictly 7 digits in between these alphabets
            """;

    /**
     * The student number must start with a letter, followed by exactly 7 numbers, and end with a letter.
     */
    public static final String VALIDATION_REGEX = "[A-Za-z][0-9]{7}[A-Za-z]";

    public final String value;

    /**
     * Returns true if a given string is a valid student number.
     *
     * @param studentNumber A valid student number.
     */
    public StudentNumber(String studentNumber) {
        requireNonNull(studentNumber);
        checkArgument(isValidStudentNumber(studentNumber), MESSAGE_CONSTRAINTS);
        this.value = studentNumber.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid student number.
     */
    public static boolean isValidStudentNumber(String test) {
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

        if (!(other instanceof StudentNumber otherStudentNumber)) {
            return false;
        }

        return value.equals(otherStudentNumber.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
