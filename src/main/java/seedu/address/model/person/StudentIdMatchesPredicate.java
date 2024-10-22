package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Tests that a {@code Person}'s {@code StudentId} matches any of the given student IDs.
 */
public class StudentIdMatchesPredicate implements Predicate<Person> {

    private final Set<String> studentIds;

    /**
     * Constructs a {@code StudentIdMatchesPredicate} with the given student IDs.
     *
     * @param studentIds A list of student IDs to match.
     */
    public StudentIdMatchesPredicate(List<String> studentIds) {
        // Clean and standardize the input student IDs
        this.studentIds = studentIds.stream()
                .map(id -> id.trim().replaceAll(" ", "").toUpperCase())
                .collect(Collectors.toSet());
    }

    /**
     * Tests if the given person's student ID matches any of the student IDs.
     *
     * @param person The person to test.
     * @return True if the person's student ID matches any of the student IDs.
     */
    @Override
    public boolean test(Person person) {
        // Compare the cleaned input with the person's student ID, ignoring case
        String personId = person.getStudentId().value.toUpperCase();
        return studentIds.contains(personId);
    }

    /**
     * Returns true if the given student IDs are equal.
     *
     * @param other The other object to compare.
     * @return True if the given student IDs are equal.
     */
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
        return studentIds.equals(otherPredicate.studentIds);
    }

}
