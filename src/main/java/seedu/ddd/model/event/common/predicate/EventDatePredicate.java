package seedu.ddd.model.event.common.predicate;

import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.event.common.Date;
import seedu.ddd.model.event.common.Event;


/**
 * Tests that a {@Code Event} 's {@code Date} matches the Date.
 */
public class EventDatePredicate implements Predicate<Event> {
    private final Date date;

    public EventDatePredicate(Date date) {
        this.date = date;
    }

    @Override
    public boolean test(Event event) {
        return this.date.equals(event.getDate());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventDatePredicate)) {
            return false;
        }

        EventDatePredicate otherEventDatePredicate = (EventDatePredicate) other;
        return date.equals(otherEventDatePredicate.date);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("Date", date).toString();
    }
}
