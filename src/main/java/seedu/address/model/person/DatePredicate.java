package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s appointment date and time matches any in the list
 */
public class DatePredicate implements Predicate<Person> {
    private Date date;

    public DatePredicate(Date date) {
        this.date = date;
    }

    @Override
    public boolean test(Person person) {
        return isDateTrue(person.getDate());
    }

    /**
     * Checks if the specified date and time matches the current date and time.
     * If the current date and time is null, all dates are considered acceptable.
     *
     * @param otherDate The date and time to compare.
     * @return {@code true} if the date and time match or if the current date and time is null, {@code false} otherwise.
     */
    public boolean isDateTrue(Date otherDate) {
        return date == null || date.equals(otherDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DatePredicate)) {
            return false;
        }

        DatePredicate otherDatePredicate = (DatePredicate) other;
        return date.equals(otherDatePredicate.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("date", date).toString();
    }

}
