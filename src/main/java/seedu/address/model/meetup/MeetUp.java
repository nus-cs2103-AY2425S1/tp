package seedu.address.model.meetup;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a MeetUp in the meet-up list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MeetUp {

    // Identity fields
    private final Subject subject;
    private final Info info;
    private final From from;
    private final To to;
    private final Set<AddedBuyer> addedBuyers = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public MeetUp(Subject subject, Info info, From from, To to, Set<AddedBuyer> addedBuyers) {
        requireAllNonNull(subject, info, from, to, addedBuyers);
        this.subject = subject;
        this.info = info;
        this.from = from;
        this.to = to;
        this.addedBuyers.addAll(addedBuyers);
    }

    /**
     * Returns true if both meetups have the same name, from and to.
     * This defines a weaker notion of equality between two meet-ups.
     */
    public boolean isSameMeetUp(MeetUp otherMeetUp) {
        if (otherMeetUp == this) {
            return true;
        }

        return otherMeetUp != null
                && otherMeetUp.getSubject().equals(getSubject())
                && otherMeetUp.getFrom().equals(getFrom())
                && otherMeetUp.getTo().equals(getTo());
    }

    /**
     * Returns true if the relationship between To and From is valid.
     */
    public boolean hasValidToFrom() {
        return to.isValidToFrom(from);
    }

    /**
     * Returns true if both meetups have the same identity and data fields.
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
        return subject.equals(otherMeetUp.subject)
                && info.equals(otherMeetUp.info)
                && from.equals(otherMeetUp.from)
                && to.equals(otherMeetUp.to)
                && addedBuyers.equals(otherMeetUp.addedBuyers);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(subject, info, from, to, addedBuyers);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("subject", subject)
                .add("info", info)
                .add("from", from)
                .add("to", to)
                .add("addedBuyers", addedBuyers)
                .toString();
    }

    public Subject getSubject() {
        return this.subject;
    }

    public Info getInfo() {
        return this.info;
    }

    public From getFrom() {
        return this.from;
    }

    public To getTo() {
        return this.to;
    }

    public Set<AddedBuyer> getAddedBuyers() {
        return this.addedBuyers;
    }

}
