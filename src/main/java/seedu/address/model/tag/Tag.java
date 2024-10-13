package seedu.address.model.tag;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;

/**
 * Represents a Tag representing Vendors and Events in the address book.
 * Guarantees: unique for each pair of vendor and event, vendor and event
 * are not null, immutable.
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";

    public final Vendor vendor;
    public final Event event;

    /**
     * Constructs a {@code Tag}.
     *
     * @param vendor A valid vendor.
     * @param event A valid event.
     */
    public Tag(Vendor vendor, Event event) {
        requireAllNonNull(vendor, event);
        this.vendor = vendor;
        this.event = event;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return vendor.equals(otherTag.vendor)
                && event.equals(otherTag.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendor, event);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("vendor", vendor)
                .add("event", event)
                .toString();
    }
}
