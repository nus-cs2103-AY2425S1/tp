package seedu.address.model.link;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Owner in PawPatrol.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Link {
    // Identity fields
    private final Linkable from;
    private final Linkable to;

    /**
     * Every field must be present and not null.
     */
    public Link(Linkable from, Linkable to) {
        requireAllNonNull(from, to);
        this.from = from;
        this.to = to;
    }

    public Linkable getFrom() {
        return from;
    }

    public Linkable getTo() {
        return to;
    }

    /**
     * @return a String description suitable for displaying in a list
     */
    public String description() {
        assert(to.getUniqueID().length() > 1);
        return "Link from owner " + from.getUniqueID() + " to pet " + to.getUniqueID().substring(1);
    }

    /**
     * Returns true if both links have the same fields.
     * This defines a strong notion of equality between two links.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Link)) {
            return false;
        }

        Link otherLink = (Link) other;
        return otherLink.getFrom().getUniqueID().equals(getFrom().getUniqueID())
            && otherLink.getTo().getUniqueID().equals(getTo().getUniqueID());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(from.getUniqueID(), to.getUniqueID());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("from", from.getUniqueID())
            .add("to", to.getUniqueID())
            .toString();
    }
}
