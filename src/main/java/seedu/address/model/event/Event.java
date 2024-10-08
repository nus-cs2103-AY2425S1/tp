package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    // Identity fields
    private final Name name;
    private final Location location;
    private final Date date;
    private final Time startTime;
    private final Time endTime;
    private final Description description;

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Location location, Date date, Time start_time, Time end_time, Description description) {
        requireAllNonNull(name, location, date, start_time, end_time, description);
        this.name = name;
        this.location = location;
        this.date = date;
        this.startTime = start_time;
        this.endTime = end_time;
        this.description = description;
    }

    public Event(Name name, Location location, Date date, Time start_time, Time end_time) {
        requireAllNonNull(name, location, date, start_time, end_time);
        this.name = name;
        this.location = location;
        this.date = date;
        this.startTime = start_time;
        this.endTime = end_time;
        this.description = new Description();
    }

    public Name getName() {
        return name;
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
        if (otherEvent== this) {
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
        return name.equals(otherEvent.name)
                && location.equals(otherEvent.location)
                && date.equals(otherEvent.date)
                && startTime.equals(otherEvent.startTime)
                && endTime.equals(otherEvent.endTime)
                && description.equals(otherEvent.description);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, location, date, startTime, endTime, description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("location", location)
                .add("date", date)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .add("description", description)
                .toString();
    }

}
