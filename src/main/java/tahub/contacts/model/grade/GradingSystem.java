package tahub.contacts.model.grade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents a grading system for managing student grades using percentages.
 * This system maintains a list of grades and handles weight distributions.
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
     */
    public double getGrade(String assessmentName) {
        return grades.stream()
                .filter(g -> g.getAssessmentName().equals(assessmentName))
                .findFirst()
                .map(Grade::getScorePercentage)
                .orElse(-1.0);
    }

    /**
     * Adds or updates a grade for a specific assessment.
     *
     * @param assessmentName the name of the assessment
     * @param score the numerical score for the assessment (as a percentage)
     */
    public void addGrade(String assessmentName, double score) {
        double weight = getExistingWeight(assessmentName);
        grades.removeIf(g -> g.getAssessmentName().equals(assessmentName));
        grades.add(new Grade(assessmentName, score, weight));
    }

    private double getExistingWeight(String assessmentName) {
        return grades.stream()
                .filter(g -> g.getAssessmentName().equals(assessmentName))
                .findFirst()
                .map(Grade::getWeight)
                .orElse(0.0);
    }

    /**
     * Sets the weight for a specific assessment.
     *
     * @param assessmentName the name of the assessment
     * @param weight the weight of the assessment in the overall grade calculation
     */
    public void setAssessmentWeight(String assessmentName, double weight) {
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

        double totalWeight = explicitWeights.values().stream().mapToDouble(Double::doubleValue).sum();

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
