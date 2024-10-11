package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code StudentId} matches the given student ID.
 */
public class StudentIdMatchesPredicate implements Predicate<Person> {
    private final String studentId;

    /**
     * Constructs a {@code StudentIdMatchesPredicate} with the given student ID.
     *
     * @param studentId The student ID to match.
     */
    public StudentIdMatchesPredicate(String studentId) {
        // Clean and standardize the input student ID
        this.studentId = studentId.trim().replaceAll(" ", "").toUpperCase();
    }

    @Override
    public boolean test(Person person) {
        // Compare the cleaned input with the person's student ID, ignoring case
        return person.getStudentId().value.equalsIgnoreCase(studentId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentIdMatchesPredicate)) {
            return false;
        }

        StudentIdMatchesPredicate otherPredicate = (StudentIdMatchesPredicate) other;
        return studentId.equals(otherPredicate.studentId);
    }

}
