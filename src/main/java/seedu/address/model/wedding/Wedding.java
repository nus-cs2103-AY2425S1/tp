package seedu.address.model.wedding;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

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
    public Wedding(Husband husband, Wife wife, Date date, Venue venue) {
        requireAllNonNull(husband, wife);
        this.husband = husband;
        this.wife = wife;
        this.date = date;
        this.venue = venue;
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


