package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an {@code Event} in the address book.
 */
public class Event {
    // Identity fields
    private final EventName name;

    /**
     * Every field must be present and not null.
     */
    public Event(EventName name) {
        requireAllNonNull(name);
        this.name = name;
    }

    /**
     * Returns the name of the event.
     */
    public EventName getName() {
        return name;
    }

    /**
     * Returns true if both events have the same name.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event otherEvent)) {
            return false;
        }

        return name.equals(otherEvent.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .toString();
    }
}
