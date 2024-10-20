package tahub.contacts.model.studentcourseassociation;

import tahub.contacts.model.course.Course;
import tahub.contacts.model.grade.GradingSystem;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.tutorial.Tutorial;


/**
 * Represents an association between a student, course, grading system, and tutorial/recitation.
 */
public class StudentCourseAssociation {
    private Person student;
    private Course course;
    /**
     * Represents a Tutorial associated with a Course.
     * May be null, if this TA is not the student's tutorial TA.
     */
    private Tutorial tutorial = null;
    private GradingSystem grades;

    /**
     * Represents an association between a student, course, grading system, and tutorial.
     * The TA will view this object in TAHub.
     * This constructor is to be used if the TA is this student's Tutorial TA.
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
     * Gets the letter grade for this StudentCourseAssociation.
     *
     * @return the letter grade
     */
    public String getLetterGrade() {
        String name = String.valueOf(this.student.getName());
        return grades.getLetterGrade(name);
    }

    /**
     * Gets the overall score for this StudentCourseAssociation.
     *
     * @return the overall score
     */
    public double getOverallScore() {
        return grades.getOverallScore();
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
}
