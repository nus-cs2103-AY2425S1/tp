package seedu.academyassist.model.person;

import java.util.function.Predicate;

import seedu.academyassist.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code StudentId} matches the given student id.
 */
public class StudentIdMatchesPredicate implements Predicate<Person> {
    private final StudentId studentId;

    public StudentIdMatchesPredicate(StudentId studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean test(Person person) {
        return person.getStudentId().equals(studentId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentIdMatchesPredicate otherStudentIdMatchesPredicate)) {
            return false;
        }

        return this.studentId.equals(otherStudentIdMatchesPredicate.studentId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("StudentId", studentId).toString();
    }
}
