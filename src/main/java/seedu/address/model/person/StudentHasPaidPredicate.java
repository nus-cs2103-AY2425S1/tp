package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests a {@code Person}'s hasPaid status
 */
public class StudentHasPaidPredicate implements Predicate<Person> {
    private final boolean hasPaid;

    public StudentHasPaidPredicate(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    @Override
    public boolean test(Person person) {
        return this.hasPaid == person.getPayment().hasPaid;
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
