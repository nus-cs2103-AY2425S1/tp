package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.util.Objects;
import java.util.Set;

/**
 * Represents a Guest in the address book.
 * A Guest is a person with an RSVP status indicating their attendance.
 */
public class Guest extends Person {

    private final Rsvp rsvp;

    /**
     * Creates a Guest with the specified details.
     * Every field must be present and not null.
     *
     * @param name The name of the guest.
     * @param phone The phone number of the guest.
     * @param email The email address of the guest.
     * @param address The address of the guest.
     * @param tags The tags associated with the guest.
     * @param rsvp The RSVP status of the guest.
     */
    public Guest(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Rsvp rsvp) {
        super(name, phone, email, address, tags);
        this.rsvp = rsvp;
    }

    /**
     * Returns true if the guest is coming, i.e., if their RSVP status is "ACCEPTED".
     *
     * @return true if the guest's RSVP status is "ACCEPTED"; false otherwise.
     */
    public boolean isComing() {
        return rsvp.toString().equalsIgnoreCase("ACCEPTED");
    }

    /**
     * Returns the RSVP status of the guest.
     *
     * @return The RSVP status of the guest.
     */
    public Rsvp getRsvp() {
        return this.rsvp;
    }

    /**
     * Checks if this guest is the same person as another person.
     * Two guests are considered the same if they have the same attributes
     * and the other person is also a Guest.
     *
     * @param otherPerson The other person to compare against.
     * @return true if the guests are the same; false otherwise.
     */
    @Override
    public boolean isSamePerson(Person otherPerson) {
        return super.isSamePerson(otherPerson)
                && (otherPerson instanceof Guest);
    }

    /**
     * Checks if this guest is equal to another object.
     *
     * @param other The object to compare with.
     * @return true if the other object is an instance of Guest and has
     *         the same attributes; false otherwise.
     */
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

    /**
     * Returns the hash code of this guest.
     *
     * @return The hash code of the guest based on its attributes.
     */
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getPhone(), getEmail(), getAddress(), getTags(), getRsvp());
    }

    /**
     * Returns a string representation of the guest, including their details
     * and RSVP status.
     *
     * @return A string representation of the guest.
     */
    @Override
    public String toString() {
        return super.toStringBuilder()
                .add("RSVP status", rsvp)
                .toString();
    }

}
