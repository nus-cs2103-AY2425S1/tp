package tahub.contacts.model.course;

import static java.util.Objects.requireNonNull;
import static tahub.contacts.commons.util.AppUtil.checkArgument;

/**
 * Represents a Course's name in the unique course list of the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCourseName(String)}
 */
public class CourseName {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(?=.*\\p{Alnum})[\\p{Alnum} ]+$";

    public final String courseName;

    /**
     * Constructs a {@code CourseName}.
     *
     * @param courseName A valid course name.
     */
    public CourseName(String courseName) {
        requireNonNull(courseName);
        checkArgument(isValidCourseName(courseName), MESSAGE_CONSTRAINTS);
        this.courseName = courseName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidCourseName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return courseName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CourseName)) {
            return false;
        }

        CourseName otherCourseName = (CourseName) other;
        return courseName.equals(otherCourseName.courseName);
    }

    @Override
    public int hashCode() {
        return courseName.hashCode();
    }

}
