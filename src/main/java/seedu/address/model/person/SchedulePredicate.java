package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s next appointment date {@code Date} is on the specified date.
 */
public class SchedulePredicate implements Predicate<Person> {
    private Date date;

    public SchedulePredicate(Date date) {
        this.date = date;
    }

    @Override
    public boolean test(Person person) {
        return person.getDate().equals(date);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SchedulePredicate)) {
            return false;
        }

        SchedulePredicate otherSchedulePredicate = (SchedulePredicate) other;
        return date.equals(otherSchedulePredicate.date);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("date", date).toString();
    }
}
