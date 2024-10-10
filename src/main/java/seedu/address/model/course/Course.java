package seedu.address.model.course;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's course in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidCourse(String)}
 */
public class Course {
    public static final String MESSAGE_CONSTRAINTS = "Courses should be in the format of two to four letters "
            + "followed by four digits, followed by 0-2 letters: e.g., MA1100, GEA1000N, GESS1000T etc.";
    /*
     * The course code must follow the format specified in MESSAGE_CONSTRAINTS as shown above.
     */
    public static final String VALIDATION_REGEX = "[a-zA-Z]{2,4}\\d{4}[a-zA-Z]{0,2}";

    public final String courseCode;

    /**
     * Constructs a {@code Course} and converts the given courseCode into uppercase.
     *
     * @param courseCode A valid course code, case-insensitive.
     */
    public Course(String courseCode) {
        requireNonNull(courseCode);
        checkArgument(isValidCourse(courseCode), MESSAGE_CONSTRAINTS);
        this.courseCode = courseCode.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid course code.
     */
    public static boolean isValidCourse(String test) {
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
        if (!(other instanceof Course)) {
            return false;
        }

        Course otherCourse = (Course) other;
        return courseCode.equals(otherCourse.courseCode);
    }

    @Override
    public int hashCode() {
        return courseCode.hashCode();
    }
}
