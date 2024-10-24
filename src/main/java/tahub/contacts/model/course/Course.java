package tahub.contacts.model.course;

import static tahub.contacts.commons.util.AppUtil.checkArgument;
import static tahub.contacts.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Course in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidCourseName(String)}
 */
public class Course {

    public static final String COURSE_CODE_MESSAGE_CONSTRAINTS =
            "Course code should start with one or more uppercase alphabets, "
                    + "followed by exactly 4 digits, and optionally end with an uppercase "
                    + "alphabet. e.g., CS2101 or CS1101S.";
    public static final String COURSE_NAME_MESSAGE_CONSTRAINTS = "Course name should be alphanumeric";
    // One or more uppercase letters, followed by 4 digits, and an optional uppercase letter
    public static final String COURSE_CODE_VALIDATION_REGEX = "^[A-Z]+\\d{4}[A-Z]?$";
    public static final String COURSE_NAME_VALIDATION_REGEX = "^(?=.*\\p{Alnum})[\\p{Alnum} ]+$";


    public final String courseCode;
    public final String courseName;

    /**
     * Constructs a {@code Course}.
     *
     * @param courseName A valid course name.
     */
    public Course(String courseCode, String courseName) {
        requireAllNonNull(courseCode, courseName);
        checkArgument(isValidCourseCode(courseCode), COURSE_CODE_MESSAGE_CONSTRAINTS);
        checkArgument(isValidCourseName(courseName), COURSE_NAME_MESSAGE_CONSTRAINTS);
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    /**
     * Returns true if both courses have the same name or course code.
     * This defines a weaker notion of equality between two courses.
     */
    public boolean isConflictCourse(Course otherCourse) {
        if (this.equals(otherCourse)) {
            return true;
        }
        return courseCode.equals(otherCourse.courseCode);
    }

    /**
     * Returns true if a given string is a valid course code.
     */
    public static boolean isValidCourseCode(String test) {
        return test.matches(COURSE_CODE_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid course name.
     */
    public static boolean isValidCourseName(String test) {
        return test.matches(COURSE_NAME_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Course otherCourse)) {
            return false;
        }

        return courseCode.equals(otherCourse.courseCode);
    }

    @Override
    public int hashCode() {
        return courseCode.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + courseCode + ": " + courseName + ']';
    }

}
