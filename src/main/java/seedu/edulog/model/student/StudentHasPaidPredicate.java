package seedu.edulog.model.student;

import java.util.function.Predicate;

import seedu.edulog.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Student}'s {@code hasPaid} matches the payment status specified.
 */
public class StudentHasPaidPredicate implements Predicate<Student> {
    private final boolean hasPaid;

    public StudentHasPaidPredicate(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    public StudentHasPaidPredicate(String hasPaid) {
        this.hasPaid = Boolean.valueOf(hasPaid);
    }

    public boolean getValue() {
        return hasPaid;
    }

    @Override
    public boolean test(Student student) {
        return student.getHasPaid() == hasPaid;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentHasPaidPredicate)) {
            return false;
        }

        StudentHasPaidPredicate otherStudentHasPaidPredicate = (StudentHasPaidPredicate) other;
        return hasPaid == otherStudentHasPaidPredicate.hasPaid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("hasPaid", hasPaid).toString();
    }
}
