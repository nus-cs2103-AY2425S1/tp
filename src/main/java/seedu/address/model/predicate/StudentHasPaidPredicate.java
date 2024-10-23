package seedu.address.model.predicate;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

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
        int overdueAmount = Integer.parseInt(person.getPayment().overdueAmount);

        return paymentUpToDate ? overdueAmount <= 0 : overdueAmount > 0;
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
        return this.paymentUpToDate == otherStudentHasPaidPredicate.paymentUpToDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("payment up to date", this.paymentUpToDate).toString();
    }
}
