package seedu.address.model.types.common;

import java.time.LocalDateTime;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.types.event.Event;

/**
 * Tests that a {@code Event}'s {@code StartDate} between a start and end date.
 */
public class EventInSchedulePredicate implements Predicate<Event> {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    /**
     * Constructs an EventInSchedulePredicate with a positive or negative number of days
     * This constructor is used to create a filter for the past N or next N days of events
     * @param range the number of days in the future/past.
     */
    public EventInSchedulePredicate(Integer range) {
        if (range >= 0) {
            startDate = DateTimeUtil.getCurrentDateTime();
            endDate = DateTimeUtil.getCurrentDateTime().withHour(23).withMinute(59).withSecond(59)
                    .plusDays(range);
        } else {
            startDate = DateTimeUtil.getCurrentDateTime().withHour(0).withMinute(0).withSecond(0)
                    .minusDays(-range);
            endDate = DateTimeUtil.getCurrentDateTime();
        }
    }

    /**
     * Constructs an EventInSchedulePredicate with a specific date
     * This constructor is used to create a filter for events that happen on that date
     * @param date the specific date.
     */
    public EventInSchedulePredicate(DateTime date) {
        startDate = date.toLocalDateTime().withHour(0).withMinute(0).withSecond(0);
        endDate = date.toLocalDateTime().withHour(23).withMinute(59).withSecond(59);
    }

    @Override
    public boolean test(Event event) {
        LocalDateTime eventLocalDateTime = event.getStartTime().toLocalDateTime();
        return eventLocalDateTime.isAfter(startDate) && eventLocalDateTime.isBefore(endDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventInSchedulePredicate)) {
            return false;
        }

        EventInSchedulePredicate otherEventInSchedulePredicate = (EventInSchedulePredicate) other;
        return startDate.equals(otherEventInSchedulePredicate.startDate)
                && endDate.equals(otherEventInSchedulePredicate.endDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("start", startDate).add("end", endDate).toString();
    }
}
