package tahub.contacts.model.course;

import static tahub.contacts.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Course in the address book.
 */
public class Course {

    public final CourseCode courseCode;
    public final CourseName courseName;

    /**
     * Constructs a {@code Course}.
     *
     * @param courseName A valid course name.
     */
    public Course(CourseCode courseCode, CourseName courseName) {
        requireAllNonNull(courseCode, courseName);
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
        return courseCode.equals(otherCourse.courseCode) || courseName.equals(otherCourse.courseName);
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

        return courseCode.equals(otherCourse.courseCode) && courseName.equals(otherCourse.courseName);
    }

    @Override
    public int hashCode() {
        return courseName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[" + courseCode.toString() + ": " + courseName.toString() + "]";
    }

}
