package tahub.contacts.model.grade;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a grading system for managing student grades using percentages.
 */
public class GradingSystem {
    private Map<String, Double> assessmentScores;
    private Map<String, Double> assessmentWeights;

    /**
     * Constructs a new GradingSystem with empty assessment scores and weights.
     */
    public GradingSystem() {
        this.assessmentScores = new HashMap<>();
        this.assessmentWeights = new HashMap<>();
    }

    /**
     * Gets the grade for a specific assessment.
     *
     * @param assessmentName the name of the assessment
     * @return the grade for the assessment as a percentage, or -1.0 if not found
     */
    public double getGrade(String assessmentName) {
        return assessmentScores.getOrDefault(assessmentName, -1.0);
    }

    /**
     * Adds or updates a grade for a specific assessment.
     *
     * @param assessmentName the name of the assessment
     * @param score the numerical score for the assessment (as a percentage)
     */
    public void addGrade(String assessmentName, double score) {
        assessmentScores.put(assessmentName, score);
    }

    /**
     * Sets the weight for a specific assessment.
     *
     * @param assessmentName the name of the assessment
     * @param weight the weight of the assessment in the overall grade calculation
     */
    public void setAssessmentWeight(String assessmentName, double weight) {
        assessmentWeights.put(assessmentName, weight);
    }

    /**
     * Calculates and retrieves the overall score.
     *
     * @return the weighted average score of all assessments, or -1.0 if no grades are recorded
     */
    public double getOverallScore() {
        if (assessmentScores.isEmpty()) {
            return -1.0;
        }

        double totalScore = 0.0;
        double totalWeight = 0.0;
        double remainingWeight = 1.0;

        for (Double weight : assessmentWeights.values()) {
            totalWeight += weight;
        }

        if (totalWeight > 1.0) {
            throw new IllegalStateException("Total weights exceed 1.0");
        }

        remainingWeight -= totalWeight;

        double defaultWeight = remainingWeight / (assessmentScores.size() - assessmentWeights.size());

        for (Map.Entry<String, Double> entry : assessmentScores.entrySet()) {
            String assessmentName = entry.getKey();
            double score = entry.getValue();
            double weight = assessmentWeights.getOrDefault(assessmentName, defaultWeight);

            totalScore += score * weight;
        }

        return totalScore;
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
