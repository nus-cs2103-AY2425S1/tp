package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's class in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStudentClass(String)}
 */
public class StudentClass implements Comparable<StudentClass> {

    public static final String MESSAGE_CONSTRAINTS = "Classes should a non-zero digit followed by an alphabet";

    /*
     * The first character of the class must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[1-9][a-zA-Z]$";

    public final String value;

    /**
     * Constructs an {@code Class}.
     *
     * @param studentClass A valid class.
     */
    public StudentClass(String studentClass) {
        requireNonNull(studentClass);
        checkArgument(isValidStudentClass(studentClass), MESSAGE_CONSTRAINTS);
        value = studentClass.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid class.
     */
    public static boolean isValidStudentClass(String test) {
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

        StudentClass otherStudentClass = (StudentClass) other;
        return value.equals(otherStudentClass.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(StudentClass s) {
        return this.toString().compareTo(s.toString());
    }
}
