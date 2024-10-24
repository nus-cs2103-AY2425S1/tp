package tahub.contacts.model.studentcourseassociation;

import java.util.Map;

import tahub.contacts.model.course.Attendance;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.grade.GradingSystem;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.tutorial.Tutorial;


/**
 * Represents an association between a student, course, grading system, and tutorial
 * Can be viewed as an enrollment of a student into a particular course and a particular tutorial
 */
public class StudentCourseAssociation {
    private final Person student;
    private final Course course;
    /**
     * Represents a Tutorial associated with a Course.
     * May be null, if this TA is not the student's tutorial TA.
     */
    private final Tutorial tutorial;
    private final GradingSystem grades;
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
        this.grades = new GradingSystem();
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
     * @param gradingSystem the GradingSystem used to manage this student's grades for the associated course
     * @param attendance the Attendance instance associated with this association
     */
    public StudentCourseAssociation(Person student, Course course, Tutorial tutorial,
                                    GradingSystem gradingSystem, Attendance attendance) {
        this.student = student;
        this.course = course;
        this.tutorial = tutorial;
        this.grades = gradingSystem;
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

    //=========== Grade ==================================================================================

    /**
     * Retrieves the grading system associated with this StudentCourseAssociation.
     *
     * @return the grading system used to manage student grades for the associated course
     */
    public GradingSystem getGrades() {
        return grades;
    }

    /**
     * Adds a grade for a specific assessment.
     *
     * @param assessmentName the name of the assessment
     * @param score the score achieved
     */
    public void addGrade(String assessmentName, double score) {
        grades.addGrade(assessmentName, score);
    }

    /**
     * Sets the weight for a specific assessment.
     *
     * @param assessmentName the name of the assessment
     * @param weight the weight of the assessment in the overall grade calculation
     */
    public void setAssessmentWeight(String assessmentName, double weight) {
        grades.setAssessmentWeight(assessmentName, weight);
    }

    /**
     * Gets the grade for a specific assessment.
     *
     * @param assessmentName the name of the assessment
     * @return the grade for the assessment as a percentage, or -1.0 if not found
     */
    public double getGrade(String assessmentName) {
        return grades.getGrade(assessmentName);
    }

    /**
     * Gets the overall score for this StudentCourseAssociation.
     *
     * @return the overall score as a percentage
     */
    public double getOverallScore() {
        return grades.getOverallScore();
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
     * Retrieves all assessment grades.
     *
     * @return a Map containing all assessment names and their corresponding scores
     */
    public Map<String, Double> getAllGrades() {
        return grades.getAllGrades();
    }

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

    @Override
    public String toString() {
        return String.format("Matriculation Number: %s\n" +
                             "Course Code: %s\n" +
                             "Tutorial Group: %s\n", student.getMatricNumber(),
                                                     course.courseCode,
                                                     tutorial.getTutorialId());
    }
}
