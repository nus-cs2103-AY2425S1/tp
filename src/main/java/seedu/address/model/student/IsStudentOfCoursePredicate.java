package seedu.address.model.student;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.course.Course;

/**
 * Tests that a Student is taking any of the given Course(s).
 * Matching is case-insensitive but exact, e.g. 'CS2103' will not match 'CS2103T'.
 */
public class IsStudentOfCoursePredicate implements Predicate<Student> {
    private final List<String> courses;

    public IsStudentOfCoursePredicate(List<String> courses) {
        this.courses = courses.stream().map(String::toUpperCase).toList();
    }

    /**
     * Returns true if the given student is taking any of the specified courses.
     * Uses case-insensitive string matching on the course codes.
     *
     * @param student the student to check
     * @return true if student is taking at least one of the specified courses
     */
    @Override
    public boolean test(Student student) {
        return courses.stream().anyMatch(c -> getCourseStringList(student).contains(c));
    }

    private List<String> getCourseStringList(Student student) {
        return student.getCourses().stream().map(Course::toString).toList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IsStudentOfCoursePredicate)) {
            return false;
        }

        IsStudentOfCoursePredicate otherPredicate = (IsStudentOfCoursePredicate) other;
        return courses.equals(otherPredicate.courses);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("courses", courses).toString();
    }
}
