package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class StudentNumber {

    public static final String MESSAGE_CONSTRAINTS =
            "Student numbers should be of the format A01234567X"
            + "and adhere to the following constraints:\n"
            + "1. The student number should start with A0.\n"
            + "2. This is followed by exactly 7 numerical digits.\n"
            + "3. Lastly, it ends with another letter, which could be A or any other uppercase letter.";

    private static final String START_PART_REGEX = "^A0";
    private static final String DIGIT_PART_REGEX = "\\d{7}";
    private static final String END_PART_REGEX = "[A-Z]$";
    public static final String VALIDATION_REGEX = START_PART_REGEX + DIGIT_PART_REGEX + END_PART_REGEX;

    public final String value;

    public StudentNumber(String studentNumber) {
        requireNonNull(studentNumber);
        checkArgument(isValidStudentNumber(studentNumber), MESSAGE_CONSTRAINTS);
        value = studentNumber;
    }

    /**
     * Returns if a given string is a valid email.
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

        // instanceof handles nulls
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
