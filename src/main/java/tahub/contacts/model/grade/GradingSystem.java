package tahub.contacts.model.grade;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a grading system for managing student grades.
 */
public class GradingSystem {
    private Map<String, Double> assessmentScores;
    private Map<String, Integer> gradeScale;

    /**
     * Constructs a new GradingSystem with initialized grade scale and empty student grades.
     */
    public GradingSystem() {
        this.assessmentScores = new HashMap<>();
        this.gradeScale = new HashMap<>();
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
     * Gets the grade for a specific assessment.
     *
     * @param assessmentName the name of the assessment
     * @return the grade for the assessment, or null if not found
     */
    public Double getGrade(String assessmentName) {
        return assessmentScores.get(assessmentName);
    }

    /**
     * Adds or updates a grade for a specific assessment.
     *
     * @param assessmentName the name of the assessment
     * @param score the numerical score for the assessment
     */
    public void addGrade(String assessmentName, double score) {
        assessmentScores.put(assessmentName, score);
    }

    /**
     * Calculates and retrieves the letter grade based on the overall score.
     *
     * @param studentName the name of the student (not used in current implementation)
     * @return the letter grade, or "No grade recorded" if no grades are available
     */
    public String getLetterGrade(String studentName) {
        double overallScore = getOverallScore();
        if (overallScore == -1.0) {
            return "No grade recorded";
        }

        for (Map.Entry<String, Integer> entry : gradeScale.entrySet()) {
            if (overallScore >= entry.getValue()) {
                return entry.getKey();
            }
        }
        return "F";
    }

    /**
     * Calculates and retrieves the overall score.
     *
     * @return the average score of all assessments, or -1.0 if no grades are recorded
     */
    public double getOverallScore() {
        if (assessmentScores.isEmpty()) {
            return -1.0;
        }
        return assessmentScores.values().stream().mapToDouble(Double::doubleValue).average().orElse(-1.0);
    }

    /**
     * Retrieves all assessment grades.
     *
     * @return a new Map containing all assessment names and their corresponding scores
     */
    public Map<String, Double> getAllGrades() {
        return new HashMap<>(assessmentScores);
    }
}
