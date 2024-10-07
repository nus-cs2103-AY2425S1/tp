package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Event in EventTory.
 * Guarantees: Name, location, and start time are not null. End time is optional.
 */
public class Event {
    private final Name name;
    private final TimeRange timeRange;
    private final Location location;
    private final Description description;

    /**
     * Constructor for an Event.
     */
    public Event(Name name, TimeRange timeRange, Location location, Description description) {
        requireAllNonNull(name, timeRange, location, description);
        this.name = name;
        this.timeRange = timeRange;
        this.location = location;
        this.description = description;
    }

    // Basic Getters
    public Name getName() {
        return name;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public Location getLocation() {
        return location;
    }

    public Description getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return name.equals(otherEvent.name)
                && timeRange.equals(otherEvent.timeRange)
                && location.equals(otherEvent.location)
                && description.equals(otherEvent.description);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + timeRange.hashCode() + location.hashCode() + description.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("timeRange", timeRange)
                .add("location", location)
                .add("description", description)
                .toString();
    }
}

