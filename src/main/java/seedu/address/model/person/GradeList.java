package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.index.Index;

/**
 * Represents a list of grades for a student in the address book.
 */
public class GradeList {
    private static final String NOT_ALL_WEIGHTAGE = "\nDo note not all weightage has been accounted for."
            + "\nPercentage of tests done: ";
    private static final float FULL_WEIGHTAGE = 1.0f;
    private final List<Grade> grades;

    /**
     * Constructs an empty {@code GradeList}.
     */
    public GradeList() {
        this.grades = Collections.unmodifiableList(new ArrayList<>());
    }

    public GradeList(List<Grade> grades) {
        this.grades = Collections.unmodifiableList(grades);
    }

    /**
     * Adds or updates the grade for a specific test.
     * If a grade for the given test name already exists, it is updated.
     *
     * @param grade The grade to be recorded.
     */
    public GradeList addGrade(Grade grade) {
        requireNonNull(grade, "Grade cannot be null");

        List<Grade> newGrades = new ArrayList<>(this.grades);
        newGrades.removeIf(existingGrade -> existingGrade.getTestName().equalsIgnoreCase(grade.getTestName()));
        newGrades.add(grade);

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
        for (Grade grade : this.grades) {
            if (grade.getTestName().equalsIgnoreCase(testName)) {
                return grade;
            }
        }
        return null;
    }

    /**
     * Removes the grade for a specific test, if it exists.
     *
     * @param testName The name of the test for which the grade should be removed.
     * @return A new GradeList with the specified grade removed.
     */
    public GradeList removeGrade(String testName) {
        requireNonNull(testName);
        List<Grade> newList = new ArrayList<>(this.grades);

        newList.removeIf(grade -> grade.getTestName().equalsIgnoreCase(testName));

        return new GradeList(newList);
    }

    /**
     * Retrieves the list of grades in this {@code GradeList}.
     *
     * @return A list of {@code Grade} objects representing all the grades in the grade list.
     */
    public List<Grade> getList() {
        return new ArrayList<>(this.grades); // Returning a copy to prevent external modification
    }


    /**
     * Returns true if index passed is out of bounds
     *
     * @param index The index in question
     * @return True if the index is in bounds, false otherwise.
     */
    public boolean checkIndexBounds(Index index) {
        return index.getZeroBased() < getList().size();
    }

    /**
     * Calculates the overall grade summary based on the weightage and scores of all grades.
     *
     * @return A summary string containing the overall score and information on weightage completeness.
     */
    public String getOverallGrade() {
        float totalScore = 0;
        float totalWeightage = 0;

        for (Grade g : this.grades) {
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

    // TODO: Check if total weightage has crossed 100%

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
        for (Grade grade : this.grades) {
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
