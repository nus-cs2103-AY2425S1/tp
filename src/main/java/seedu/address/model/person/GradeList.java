package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of grades for a student in the address book.
 */
public class GradeList {
    private final List<Grade> grades;

    /**
     * Constructs an empty {@code GradeList}.
     */
    public GradeList() {
        grades = new ArrayList<>();
    }

    /**
     * Adds or updates the grade for a specific test.
     * If a grade for the given test name already exists, it is updated.
     *
     * @param grade The grade to be recorded.
     */
    public void addGrade(Grade grade) {
        requireNonNull(grade, "Grade cannot be null");
        removeGrade(grade.getTestName()); // Remove any existing grade for the test (to allow updating)
        grades.add(grade);
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
        for (Grade grade : grades) {
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
     */
    private void removeGrade(String testName) {
        requireNonNull(testName);
        grades.removeIf(grade -> grade.getTestName().equalsIgnoreCase(testName));
    }

    /**
     * Returns true if there is a grade recorded for the specified test.
     *
     * @param testName The name of the test.
     * @return True if the grade exists for the test, false otherwise.
     */
    public boolean hasGrade(String testName) {
        requireNonNull(testName);
        return getGrade(testName) != null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Grade grade : grades) {
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
        return grades.equals(otherGradeList.grades);
    }

    @Override
    public int hashCode() {
        return grades.hashCode();
    }
}
