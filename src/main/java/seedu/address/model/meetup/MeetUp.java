package seedu.address.model.meetup;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;
import java.util.Objects;

/**
 * Represents a Meetup in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MeetUp { //TODO

    // Identity fields
    private final String desc;
    private final Date from;
    private final Date to;

    /**
     * Every field must be present and not null.
     */
    public MeetUp(String desc, Date from, Date to) {
        requireAllNonNull(desc, from, to);
        this.desc = desc;
        this.from = from;
        this.to = to;
    }


    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two meetUps.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetUp)) {
            return false;
        }

        MeetUp otherMeetUp = (MeetUp) other;
        return desc.equals(otherMeetUp.desc)
                && from.equals(otherMeetUp.from)
                && to.equals(otherMeetUp.to);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(desc, from, to);
    }

    @Override
    public String toString() {
        //TODO
        return "";
    }

}
