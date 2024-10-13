package seedu.address.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a grading system for managing student grades.
 */
public class GradingSystem {
    private Map<String, Integer> gradeScale;
    private Map<String, Double> studentGrades;

    /**
     * Constructs a new GradingSystem with initialized grade scale and empty student grades.
     */
    public GradingSystem() {
        this.gradeScale = new HashMap<>();
        this.studentGrades = new HashMap<>();
        initializeGradeScale();
    }

    /**
     * Initializes the grade scale with default values.
     */
    private void initializeGradeScale() {
        gradeScale.put("A", 90);
        gradeScale.put("B", 80);
        gradeScale.put("C", 70);
        gradeScale.put("D", 60);
        gradeScale.put("F", 0);
    }

    /**
     * Adds or updates a student's grade.
     *
     * @param studentName the name of the student
     * @param score the numerical score of the student
     */
    public void addStudentGrade(String studentName, double score) {
        studentGrades.put(studentName, score);
    }

    /**
     * Retrieves the letter grade for a given student based on their score.
     *
     * @param studentName the name of the student
     * @return the letter grade of the student, or "No grade recorded" if no grade is found
     */
    public String getLetterGrade(String studentName) {
        Double score = studentGrades.get(studentName);
        if (score == null) {
            return "No grade recorded";
        }

        for (Map.Entry<String, Integer> entry : gradeScale.entrySet()) {
            if (score >= entry.getValue()) {
                return entry.getKey();
            }
        }
        return "F";
    }

    /**
     * Retrieves the numerical score for a given student.
     *
     * @param studentName the name of the student
     * @return the numerical score of the student, or -1.0 if no score is found
     */
    public double getStudentScore(String studentName) {
        return studentGrades.getOrDefault(studentName, -1.0);
    }

    /**
     * Retrieves all student grades.
     *
     * @return a new Map containing all student names and their corresponding scores
     */
    public Map<String, Double> getAllGrades() {
        return new HashMap<>(studentGrades);
    }
}
