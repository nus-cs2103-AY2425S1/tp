package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

/**
 * Represents an Event's start date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDuration(LocalDate, LocalDate)}
 */
public class EventDuration {
    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in {YYYY-MM-DD} format, and end date cannot be earlier than start date.";
    public final LocalDate eventFrom;
    public final LocalDate eventTo;

    /**
     * Constructs a {@code EventDuration}.
     *
     * @param eventFrom A valid Event start date.
     * @param eventTo A valid Event end date.
     */
    public EventDuration(LocalDate eventFrom, LocalDate eventTo) {
        requireNonNull(eventFrom);
        requireNonNull(eventTo);
        checkArgument(isValidDuration(eventFrom, eventTo), MESSAGE_CONSTRAINTS);
        this.eventFrom = eventFrom;
        this.eventTo = eventTo;
    }

    /**
     * Returns true if the end date is after the start date.
     */
    public static boolean isValidDuration(LocalDate eventFrom, LocalDate eventTo) {
        return !(eventFrom.isAfter(eventTo));
    }

    @Override
    public String toString() {
        return "from: " + eventFrom.toString() + "; to: " + eventTo.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventDuration)) {
            return false;
        }

        EventDuration otherEventDuration = (EventDuration) other;
        boolean sameStart = eventFrom.isEqual(otherEventDuration.eventFrom);
        boolean sameEnd = eventTo.isEqual(otherEventDuration.eventTo);
        return sameStart && sameEnd;
    }

    @Override
    public int hashCode() {
        return eventFrom.hashCode();
    }

    public LocalDate getStartDate() {
        return eventFrom;
    }

    public LocalDate getEndDate() {
        return eventTo;
    }

}
