package tahub.contacts.model.studentcourseassociation;

import tahub.contacts.model.course.Attendance;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.tutorial.Tutorial;


/**
 * Represents an association between a student, course, grading system, and tutorial
 */
public class StudentCourseAssociation {
    private final Person student;
    private final Course course;
    /**
     * Represents a Tutorial associated with a Course.
     * May be null, if this TA is not the student's tutorial TA.
     */
    private final Tutorial tutorial;
    private final Attendance attendance;

    /**
     * Represents an association between a student, course, grading system, tutorial, and attendance.
     * The TA will view this object in TAHub.
     * This constructor is to be used if the TA is first initialising a contact in TAHub.
     * Must be carefully used, as it will result in the loss of the grades and attendance fields.
     *
     * @param student the student associated with this association
     * @param course the course associated with this association
     * @param tutorial the tutorial associated with this association
     */
    public StudentCourseAssociation(Person student, Course course, Tutorial tutorial) {
        this.student = student;
        this.course = course;
        this.tutorial = tutorial;
        this.attendance = new Attendance();
    }

    /**
     * Represents an association between a student, course, grading system, tutorial, and attendance.
     * The TA will view this object in TAHub.
     * This constructor is to be used if the SCA has already been prepopulated with GradingSystem and
     * Attendance (such as when de-serialising an JsonAdaptedSCA from storage to this Model).
     *
     * @param student the student associated with this association
     * @param course the course associated with this association
     * @param tutorial the tutorial associated with this association
     * @param attendance the Attendance instance associated with this association
     */
    public StudentCourseAssociation(Person student, Course course, Tutorial tutorial, Attendance attendance) {
        this.student = student;
        this.course = course;
        this.tutorial = tutorial;
        this.attendance = attendance;
    }

    /**
     * Get the student associated with this StudentCourseAssociation.
     *
     * @return the student associated with this StudentCourseAssociation
     */
    public Person getStudent() {
        return student;
    }

    /**
     * Retrieve the Course associated with this StudentCourseAssociation.
     *
     * @return the Course object associated with this StudentCourseAssociation
     */
    public Course getCourse() {
        return course;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    //=========== Attendance ==================================================================================

    /**
     * Retrieves the {@link Attendance} instance associated with this StudentCourseAssociation.
     *
     * @return the {@link Attendance} instance.
     */
    public Attendance getAttendance() {
        return attendance;
    }

    //=========== Utility ==================================================================================

    /**
     * Compares this StudentCourseAssociation with the specified object for equality.
     * First checks if both StudentCourseAssociations have the same Student and Course
     * If they are the same, check if the tutorials match
     *
     * @param other the object to compare this StudentCourseAssociation with
     * @return true if the two objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentCourseAssociation)) {
            return false;
        }

        StudentCourseAssociation otherStudentCourseAssociation = (StudentCourseAssociation) other;
        boolean checkStudentAndCourse = this.student.equals(otherStudentCourseAssociation.student)
                && this.course.equals(otherStudentCourseAssociation.course);

        if (!checkStudentAndCourse) {
            return false;
        }

        return this.tutorial.equals(otherStudentCourseAssociation.tutorial);
    }

    /**
     * Compares this {@code StudentCourseAssociation} with another {@code StudentCourseAssociation} for equality
     * based on the primary identifiers: matriculation number, course code, and tutorial ID.
     *
     * @param other The other {@code StudentCourseAssociation} to compare this {@code StudentCourseAssociation} with
     * @return true if the two SCAs are equal, false otherwise
     */
    public boolean isSameSca(StudentCourseAssociation other) {
        if (other == this) {
            return true;
        }

        return this.student.isSamePerson(other.student)
                && this.course.isConflictCourse(other.course)
                && this.tutorial.equals(other.tutorial);
    }

    @Override
    public String toString() {
        return student.toStringShort() + " - " + course.toString() + " - " + tutorial.toString();
    }
}
