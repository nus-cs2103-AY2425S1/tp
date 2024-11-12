package seedu.address.model.person;

import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s next appointment date {@code Date} is on the specified date.
 */
public class SchedulePredicate implements Predicate<Person> {
    private LocalDate date;

    public SchedulePredicate(Date dateAndTime) {
        this.date = dateAndTime.getDateOnly();
    }

    @Override
    public boolean test(Person person) {
        return person.getDate().getDateOnly().equals(date);
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
