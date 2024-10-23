package tahub.contacts.model.grade;

import java.util.Objects;

/**
 * Represents a Grade in the grading system.
 * A grade consists of an assessment name, a score percentage, and a weight.
 * This class is immutable to ensure consistency of grade data.
 */
public class Grade {
    private final String assessmentName;
    private final double scorePercentage;
    private final double weight;

    /**
     * Constructs a new {@code Grade} instance.
     *
     * @param assessmentName The name of the assessment. Must not be null or empty.
     * @param scorePercentage The score as a percentage. Must be between 0 and 100, inclusive.
     * @param weight The weight of this grade in the overall grading scheme. Must be between 0 and 1, inclusive.
     * @throws IllegalArgumentException if any of the parameters are invalid.
     */
    public Grade(String assessmentName, double scorePercentage, double weight) {
        if (assessmentName == null || assessmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Assessment name cannot be null or empty.");
        }
        if (scorePercentage < 0 || scorePercentage > 100) {
            throw new IllegalArgumentException("Score percentage must be between 0 and 100.");
        }
        if (weight < 0 || weight > 1) {
            throw new IllegalArgumentException("Weight must be between 0 and 1.");
        }
        this.assessmentName = assessmentName;
        this.scorePercentage = scorePercentage;
        this.weight = weight;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public double getScorePercentage() {
        return scorePercentage;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Grade grade = (Grade) o;
        return Double.compare(grade.scorePercentage, scorePercentage) == 0
                && Double.compare(grade.weight, weight) == 0
                && Objects.equals(assessmentName, grade.assessmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessmentName, scorePercentage, weight);
    }

    @Override
    public String toString() {
        return String.format("Grade{assessmentName='%s', scorePercentage=%.2f, weight=%.2f}",
                             assessmentName, scorePercentage, weight);
    }
}
