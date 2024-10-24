package seedu.address.model.wedding;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Represents a wedding plan.
 */
public class Wedding {
    private Name name;
    private Client client;
    private Date date;
    private Venue venue;
    private ContactSet contactSet;

    /**
     * Constructs a {@code Wedding}.
     */
    public Wedding(Name name, Client client, Date date, Venue venue) {
        requireAllNonNull(name, client);
        this.name = name;
        this.client = client;
        this.date = date;
        this.venue = venue;
        this.contactSet = new ContactSet();
    }

    public Name getName() {
        return name;
    }

    public Client getClient() {
        return client;
    }

    public Date getDate() {
        return date;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    /**
     * Adds a role and person to the ContactMap for this wedding.
     * Validates that the person being added is not a spouse (husband or wife).
     *
     * @param person The person who will have the role.
     * @throws IllegalArgumentException If the person is a spouse or if the role is already assigned.
     */
    public void addRoleToSet(Person person) {
        if (person.isSamePerson(client.getPerson())) {
            throw new IllegalArgumentException("This person is a client and cannot have another role.");
        }

        if (person.getRole() == null) {
            throw new IllegalArgumentException("This person does not have a role.");
        }

        contactSet.addToMap(person.getRole(), person);
    }
    /**
     * Returns true if both weddings have the same identity.
     */
    public boolean isSameWedding(Wedding otherWedding) {
        return this.equals(otherWedding);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("client", client)
                .add("date", date)
                .add("venue", venue)
                .add("contactSet", contactSet)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Wedding)) {
            return false;
        }

        Wedding otherWedding = (Wedding) other;
        return name.equals(otherWedding.name)
               && client.equals(otherWedding.client)
               && date.equals(otherWedding.date)
               && venue.equals(otherWedding.venue)
               && contactSet.equals(otherWedding.contactSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, client, date, venue, contactSet);
    }
}
