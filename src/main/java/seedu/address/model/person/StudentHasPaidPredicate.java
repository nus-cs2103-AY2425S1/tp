package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;

import java.util.function.Predicate;

/**
 * Tests a {@code Person}'s hasPaid status
 */
public class StudentHasPaidPredicate implements Predicate<Person> {
    private final boolean hasPaid;
    public static final String MESSAGE_NOT_IMPLEMENTED_YET =
            "StudentHasPaidPredicate not implemented yet";

    public StudentHasPaidPredicate(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    //TODO Implement test
    @Override
    public boolean test(Person person) {
        throw new UnsupportedOperationException(MESSAGE_NOT_IMPLEMENTED_YET);
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

        StudentHasPaidPredicate studentHasPaidPredicate = (StudentHasPaidPredicate) other;
        return this.hasPaid == studentHasPaidPredicate.hasPaid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("hasPaid", this.hasPaid).toString();
    }
}
