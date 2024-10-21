package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests a {@code Person}'s hasPaid status
 */
public class StudentHasPaidPredicate implements Predicate<Person> {
    private final boolean paymentUpToDate;

    public StudentHasPaidPredicate(boolean paymentUpToDate) {
        this.paymentUpToDate = paymentUpToDate;
    }

    @Override
    public boolean test(Person person) {
        if (paymentUpToDate) {
            return Integer.parseInt(person.getPayment().overdueAmount) <= 0;
        } else {
            return Integer.parseInt(person.getPayment().overdueAmount) > 0;
        }
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
        return this.paymentUpToDate == studentHasPaidPredicate.paymentUpToDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("payment up to date", this.paymentUpToDate).toString();
    }
}
