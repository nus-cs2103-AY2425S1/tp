package tahub.contacts.model.grade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents a grading system for managing student grades using percentages.
 * This system maintains a list of grades and handles weight distributions.
 * All grades are stored with their respective weights, and the total of all weights must not exceed 1.0.
 */
public class GradingSystem {
    private List<Grade> grades;

    /**
     * Constructs a new GradingSystem with an empty list of grades.
     */
    public GradingSystem() {
        this.grades = new ArrayList<>();
    }

    /**
     * Gets the grade for a specific assessment.
     *
     * @param assessmentName the name of the assessment
     * @return the grade for the assessment as a percentage, or -1.0 if not found
     * @throws IllegalArgumentException if the assessment name is null or empty
     */
    public double getGrade(String assessmentName) {
        if (assessmentName == null || assessmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Assessment name cannot be null or empty");
        }
        return grades.stream()
                .filter(g -> g.getAssessmentName().equals(assessmentName))
                .findFirst()
                .map(Grade::getScorePercentage)
                .orElse(-1.0);
    }

    /**
     * Gets the existing weight for a specific assessment.
     *
     * @param assessmentName the name of the assessment to find
     * @return the weight of the assessment
     * @throws IllegalArgumentException if the assessment is not found
     */
    private double getExistingWeight(String assessmentName) {
        return grades.stream()
                .filter(g -> g.getAssessmentName().equals(assessmentName))
                .findFirst()
                .map(Grade::getWeight)
                .orElseThrow(() -> new IllegalArgumentException("No grade found for assessment: " + assessmentName));
    }

    /**
     * Adds or updates a grade for a specific assessment.
     * If the assessment already exists, its score is updated while maintaining its weight.
     *
     * @param assessmentName the name of the assessment
     * @param score the numerical score for the assessment (as a percentage)
     * @throws IllegalArgumentException if the assessment name is invalid or if the score is out of range
     */
    public void addGrade(String assessmentName, double score) {
        if (assessmentName == null || assessmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Assessment name cannot be null or empty");
        }
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }

        double weight;
        try {
            weight = getExistingWeight(assessmentName);
        } catch (IllegalArgumentException e) {
            weight = 0.0;
        }

        grades.removeIf(g -> g.getAssessmentName().equals(assessmentName));
        grades.add(new Grade(assessmentName, score, weight));
    }

    /**
     * Sets the weight for a specific assessment.
     *
     * @param assessmentName the name of the assessment
     * @param weight the weight of the assessment in the overall grade calculation
     * @throws IllegalArgumentException if the assessment name is invalid or if the weight is out of range
     */
    public void setAssessmentWeight(String assessmentName, double weight) {
        if (assessmentName == null || assessmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Assessment name cannot be null or empty");
        }
        if (weight < 0 || weight > 1) {
            throw new IllegalArgumentException("Weight must be between 0 and 1");
        }

        Optional<Grade> existingGrade = grades.stream()
                .filter(g -> g.getAssessmentName().equals(assessmentName))
                .findFirst();

        grades.removeIf(g -> g.getAssessmentName().equals(assessmentName));

        if (existingGrade.isPresent()) {
            grades.add(new Grade(assessmentName, existingGrade.get().getScorePercentage(), weight));
        } else {
            grades.add(new Grade(assessmentName, 0.0, weight));
        }
    }

    /**
     * Calculates and retrieves the overall score.
     *
     * @return the weighted average score of all assessments, or -1.0 if no grades are recorded
     * @throws IllegalStateException if total weights exceed 1.0
     */
    public double getOverallScore() {
        if (grades.isEmpty()) {
            return -1.0;
        }

        Map<String, Double> explicitWeights = grades.stream()
                .filter(g -> g.getWeight() > 0.0)
                .collect(Collectors.toMap(Grade::getAssessmentName, Grade::getWeight));

        double totalWeight = explicitWeights.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        if (totalWeight > 1.0) {
            throw new IllegalStateException("Total weights exceed 1.0");
        }

        double remainingWeight = 1.0 - totalWeight;
        int unweightedCount = grades.size() - explicitWeights.size();
        double defaultWeight = unweightedCount > 0 ? remainingWeight / unweightedCount : 0.0;

        double totalScore = 0.0;
        for (Grade grade : grades) {
            double weight = explicitWeights.containsKey(grade.getAssessmentName())
                    ? grade.getWeight()
                    : defaultWeight;
            totalScore += grade.getScorePercentage() * weight;
        }

        return totalScore;
    }

    /**
     * Retrieves all assessment grades.
     *
     * @return a Map containing all assessment names and their corresponding scores
     */
    public Map<String, Double> getAllGrades() {
        return grades.stream()
                .collect(Collectors.toMap(
                        Grade::getAssessmentName,
                        Grade::getScorePercentage));
    }

    /**
     * Retrieves all assessment weights.
     *
     * @return a Map containing all assessment names and their corresponding weights
     */
    public Map<String, Double> getAllWeights() {
        return grades.stream()
                .filter(g -> g.getWeight() > 0.0)
                .collect(Collectors.toMap(
                        Grade::getAssessmentName,
                        Grade::getWeight));
    }
}
