package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    // Identity fields
    private final EventName eventName;
    private final Location location;
    private final Date date;
    private final Time startTime;
    private final Time endTime;
    private final Description description;

    /**
     * Every field must be present and not null.
     */
    public Event(EventName eventName, Location location, Date date,
                 Time startTime, Time endTime, Description description) {
        requireAllNonNull(eventName, location, date, startTime, endTime, description);
        this.eventName = eventName;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    /**
     * Constructs an {@code Event} object with the specified event name, location, date, start time, and end time.
     *
     * @param eventName The name of the event.
     * @param location The location of the event.
     * @param date The date of the event.
     * @param startTime The start time of the event.
     * @param endTime The end time of the event.
     *
     * @throws NullPointerException if any of the parameters are null.
     */
    public Event(EventName eventName, Location location, Date date, Time startTime, Time endTime) {
        requireAllNonNull(eventName, location, date, startTime, endTime);
        this.eventName = eventName;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = new Description();
    }

    public EventName getName() {
        return eventName;
    }

    public Location getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns true if both events have the same name.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName());
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return eventName.equals(otherEvent.eventName)
                && location.equals(otherEvent.location)
                && date.equals(otherEvent.date)
                && startTime.equals(otherEvent.startTime)
                && endTime.equals(otherEvent.endTime)
                && description.equals(otherEvent.description);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventName, location, date, startTime, endTime, description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", eventName)
                .add("location", location)
                .add("date", date)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .add("description", description)
                .toString();
    }

}
