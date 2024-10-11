package seedu.address.model.meetup;

import seedu.address.commons.util.ToStringBuilder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;
import java.util.Objects;

/**
 * Represents a Meetup in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MeetUp { //TODO

    // Identity fields
    private final String name;
    private final String info;
    private final Date from;
    private final Date to;

    /**
     * Every field must be present and not null.
     */
    public MeetUp(String name, String info, Date from, Date to) {
        requireAllNonNull(name, info, from, to);
        this.name = name;
        this.info = info;
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
        return name.equals(otherMeetUp.name)
                && info.equals(otherMeetUp.info)
                && from.equals(otherMeetUp.from)
                && to.equals(otherMeetUp.to);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, info, from, to);
    }

    @Override
    public String toString() {
        //TODO
        return new ToStringBuilder(this)
                .add("name", name)
                .add("info", info)
                .add("from", from)
                .add("to", to)
                .toString();
    }

    public String getName() {
        return this.name;
    }

    public String getInfo() {
        return this.info;
    }

    public Date getStart() {
        return this.from;
    }

    public Date getEnd() {
        return this.to;
    }
}
