package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

import java.util.Objects;
import java.util.Set;

public class Guest extends Person {

    private final Rsvp rsvp;

    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param tags
     */
    public Guest(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Rsvp rsvp) {
        super(name, phone, email, address, tags);
        this.rsvp = rsvp;
    }

    public boolean isComing() {
        return rsvp.toString().equalsIgnoreCase("ACCEPTED");
    }

    public Rsvp getRsvp() {
        return this.rsvp;
    }

    @Override
    public boolean isSamePerson(Person otherPerson) {
        return super.isSamePerson(otherPerson)
                && (otherPerson instanceof Guest);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Guest)) {
            return false;
        }

        Guest otherGuest = (Guest) other;
        return super.equals(other) &&
                getRsvp().equals(otherGuest.getRsvp());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getPhone(), getEmail(), getAddress(), getTags(), getRsvp());
    }

    @Override
    public String toString() {
        return super.toStringBuilder()
                .add("RSVP status", rsvp)
                .toString();
    }

}
