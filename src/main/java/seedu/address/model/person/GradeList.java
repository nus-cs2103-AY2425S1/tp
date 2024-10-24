package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents a list of grades for a student in the address book.
 */
public class GradeList {
    private static final String NOT_ALL_WEIGHTAGE = "\nDo note not all weightage has been accounted for."
            + "\nPercentage of tests done: ";
    private static final float FULL_WEIGHTAGE = 1.0f;
    private final Map<String, Grade> grades;

    /**
     * Constructs an empty {@code GradeList}.
     */
    public GradeList() {
        this.grades = Collections.unmodifiableMap(new HashMap<>());
    }

    public GradeList(Map<String, Grade> grades) {
        this.grades = Collections.unmodifiableMap(grades);
    }

    private GradeList(List<Grade> grades) {
        this.grades = grades.stream().collect(Collectors.toMap(Grade::getTestName, grade -> grade));
    }

    /**
     * Adds or updates the grade for a specific test.
     * If a grade for the given test name already exists, it is updated.
     * If the total weightage of all grades exceeds 100%, a runtime exception is thrown.
     *
     * @param grade The grade to be recorded.
     * @return A new GradeList containing the updated grades.
     * @throws RuntimeException if the total weightage exceeds 100%.
     */
    public GradeList addGrade(Grade grade) {
        requireNonNull(grade, "Grade cannot be null");
        Map<String, Grade> newGrades = new HashMap<>(this.grades);

        float totalWeightage = 0;
        for (Grade g : newGrades.values()) {
            totalWeightage += g.getWeightage();
        }
        totalWeightage += grade.getWeightage();

        // Check if the total weightage exceeds 100%
        if (totalWeightage > 100) {
            throw new IllegalStateException("Total weightage exceeds 100%");
        }

        newGrades.merge(grade.getTestName(), grade, (oldGrade, newGrade) -> newGrade);

        return new GradeList(newGrades);
    }

    /**
     * Retrieves the grade for a specific test.
     * Returns the {@code Grade} object if found, or null if no grade is recorded for the test.
     *
     * @param testName The name of the test.
     * @return The {@code Grade} object for the test, or null if no grade is found.
     */
    public Grade getGrade(String testName) {
        requireNonNull(testName);
        return this.grades.get(testName);
    }

    /**
     * Removes the grade for a specific test, if it exists.
     *
     * @param testName The name of the test for which the grade should be removed.
     * @return A new GradeList with the specified grade removed.
     */
    public GradeList removeGrade(String testName) {
        requireNonNull(testName);
        Map<String, Grade> newList = new HashMap<>(this.grades);

        newList.remove(testName);

        return new GradeList(newList);
    }

    public GradeList filter(Predicate<Grade> predicate) {
        return new GradeList(this.grades.values().stream().filter(predicate).toList());
    }

    /**
     * Retrieves the list of grades in this {@code GradeList}.
     *
     * @return A list of {@code Grade} objects representing all the grades in the grade list.
     */
    public Map<String, Grade> getMap() {
        return new HashMap<>(this.grades); // Returning a copy to prevent external modification
    }


    /**
     * Calculates the overall grade summary based on the weightage and scores of all grades.
     *
     * @return A summary string containing the overall score and information on weightage completeness.
     */
    public String getOverallGrade() {
        float totalScore = 0;
        float totalWeightage = 0;

        for (Grade g : this.grades.values()) {
            float currentWeightage = g.getWeightage();
            totalWeightage += currentWeightage / 100;
            totalScore += g.getScore() * currentWeightage / 100;
        }

        String summary = String.format("Overall score: %.2f/100", totalScore);

        if (totalWeightage < FULL_WEIGHTAGE) {
            summary += String.format(NOT_ALL_WEIGHTAGE + "%.2f%%", totalWeightage * 100);
        }

        return summary;
    }

    /**
     * Calculates the total weightage of all the grades in the list.
     *
     * @return The total weightage of all grades as a percentage (0 to 100).
     */
    public float getTotalWeightage() {
        float totalWeightage = 0;

        // Iterate over all grades to sum their weightages
        for (Grade g : this.grades.values()) {
            totalWeightage += g.getWeightage();
        }

        return totalWeightage;
    }

    /**
     * Returns true if the grade list is empty.
     *
     * @return true if there are no grades in the list, false otherwise.
     */
    public boolean isEmpty() {
        return grades.isEmpty();
    }

    @Override
    public String toString() {
        if (this.grades.isEmpty()) {
            return "No grades available";
        }
        StringBuilder result = new StringBuilder();
        for (Grade grade : this.grades.values()) {
            result.append(grade.toString()).append("\n");
        }
        return result.toString().trim();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GradeList)) {
            return false;
        }

        GradeList otherGradeList = (GradeList) other;
        return this.grades.equals(otherGradeList.grades);
    }

    @Override
    public int hashCode() {
        return this.grades.hashCode();
    }
}
