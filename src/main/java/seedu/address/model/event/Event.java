package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.id.UniqueId;

/**
 * Represents an Event in EventTory.
 * Guarantees: Name and date are not null.
 */
public class Event {
    private final Name name;
    private final Date date;
    private final UniqueId id;

    /**
     * Constructor for an Event with an automatically generated UniqueId.
     *
     * @param name The name of the event.
     * @param date The date of the event.
     */
    public Event(Name name, Date date) {
        requireAllNonNull(name, date);
        this.id = new UniqueId();
        this.name = name;
        this.date = date;
    }

    /**
     * Constructor for an Event with a specified UniqueId.
     *
     * @param id The unique identifier for the event.
     * @param name The name of the event.
     * @param date The date of the event.
     */
    public Event(UniqueId id, Name name, Date date) {
        requireAllNonNull(id, name, date);
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public UniqueId getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Date getDate() {
        return date;
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return name.equals(otherEvent.name) && date.equals(otherEvent.date);
    }

    @Override
    public int hashCode() {
        return id.hashCode() + name.hashCode() + date.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", id)
                .add("name", name)
                .add("date", date)
                .toString();
    }
}

