package seedu.address.model.wedding;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents a wedding plan.
 */
public class Wedding {
    private Husband husband;
    private Wife wife;
    private Date date;
    private Venue venue;
    private ContactMap contactList;

    /**
     * Constructs a {@code Wedding}.
     */
    public Wedding(Husband husband, Wife wife, Date date, Venue venue, ContactMap contactList) {
        requireAllNonNull(husband, wife);
        this.husband = husband;
        this.wife = wife;
        this.date = date;
        this.venue = venue;
        this.contactList = contactList;
    }

    public Husband getHusband() {
        return husband;
    }

    public Wife getWife() {
        return wife;
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
     * Returns true if the given person is a spouse in this wedding.
     *
     * @param person The person to check.
     * @return True if the person is either the husband or wife, false otherwise.
     */
    public boolean isSpouse(Person person) {
        return person.equals(husband.getPerson()) || person.equals(wife.getPerson());
    }

    /**
     * Adds a role and person to the ContactMap for this wedding.
     * Validates that the person being added is not a spouse (husband or wife).
     *
     * @param role The role to be added.
     * @param person The person who will have the role.
     * @throws IllegalArgumentException If the person is a spouse or if the role is already assigned.
     */
    public void addRoleToMap(Role role, Person person) {
        if (isSpouse(person)) {
            throw new IllegalArgumentException("This person is a spouse and cannot have another role.");
        }
        contactList.addToMap(role, person);
    }


    public String getWeddingName() {
        return husband + " & " + wife;
    }
    public ContactMap getContactList() {
        return contactList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("husband", husband)
                .add("wife", wife)
                .add("date", date)
                .add("venue", venue)
                .add("contactList", contactList)
                .toString();
    }

    public boolean isSameWedding(Wedding otherWedding) {
        if (otherWedding == this) {
            return true;
        }

        return otherWedding != null
               && otherWedding.getHusband().equals(getHusband())
               && otherWedding.getWife().equals(getWife())
               && otherWedding.getDate().equals(getDate())
               && otherWedding.getVenue().equals(getVenue());
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
        return husband.equals(otherWedding.husband)
               && wife.equals(otherWedding.wife)
               && date.equals(otherWedding.date)
               && venue.equals(otherWedding.venue)
               && contactList.equals(otherWedding.contactList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(husband, wife, date, venue, contactList);
    }
}


