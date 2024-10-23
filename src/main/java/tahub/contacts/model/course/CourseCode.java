package tahub.contacts.model.course;

import static java.util.Objects.requireNonNull;
import static tahub.contacts.commons.util.AppUtil.checkArgument;

/**
 * Represents a Course's code in the unique course list of the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCourseCode(String)}
 */
public class CourseCode {
    public static final String MESSAGE_CONSTRAINTS =
            "Course code should start with one or more uppercase alphabets, "
                    + "followed by exactly 4 digits, and optionally end with an uppercase "
                    + "alphabet. e.g., CS2101 or CS1101S.";
    // One or more uppercase letters, followed by 4 digits, and an optional uppercase letter
    public static final String VALIDATION_REGEX = "^[A-Z]+\\d{4}[A-Z]?$";

    public final String courseCode;

    /**
     * Constructs a {@code CourseCode}.
     *
     * @param courseCode A valid course code.
     */
    public CourseCode(String courseCode) {
        requireNonNull(courseCode);
        checkArgument(isValidCourseCode(courseCode), MESSAGE_CONSTRAINTS);
        this.courseCode = courseCode;
    }

    /**
     * Returns true if a given string is a valid code.
     */
    public static boolean isValidCourseCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }
    
    @Override
    public String toString() {
        return courseCode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CourseCode)) {
            return false;
        }

        CourseCode otherCourseCode = (CourseCode) other;
        return courseCode.equals(otherCourseCode.courseCode);
    }

    @Override
    public int hashCode() {
        return courseCode.hashCode();
    }
}
