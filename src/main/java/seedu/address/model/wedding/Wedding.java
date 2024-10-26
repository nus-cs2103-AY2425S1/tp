package seedu.address.model.wedding;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents a wedding that has a name, venue, date and a list of participants
 */
public class Wedding {
    private final WeddingName weddingName;
    private final Venue venue;
    private final Date date;
    private final Set<Person> participants = new HashSet<>();

    /**
     * Constructs a {@code Wedding}.
     *
     * @param weddingName A valid wedding name.
     * @param venue A venue as inputted by the user.
     * @param date A valid date format.
     * @param participants A list of particpants.
     */
    public Wedding(WeddingName weddingName, Venue venue, Date date, Set<Person> participants) {
        this.weddingName = weddingName;
        this.venue = venue;
        this.date = date;
        this.participants.addAll(participants);
    }

    /**
     * Constructs a {@code Wedding}.
     *
     * @param weddingName A valid wedding name.
     * @param venue A venue as inputted by the user.
     * @param date A valid date format.
     */
    public Wedding(WeddingName weddingName, Venue venue, Date date) {
        this.weddingName = weddingName;
        this.venue = venue;
        this.date = date;
    }

    public WeddingName getWeddingName() {
        return this.weddingName;
    }

    public Venue getVenue() {
        return this.venue;
    }

    public Date getDate() {
        return this.date;
    }

    public Set<Person> getParticipants() {
        return this.participants;
    }

    /**
     * Returns true if both weddings have the same name.
     * This defines a weaker notion of equality between two weddings.
     */
    public boolean isSameWedding(Wedding otherWedding) {
        if (otherWedding == this) {
            return true;
        }

        return otherWedding != null
                && otherWedding.getWeddingName().equals(getWeddingName());
    }

    /**
     * Returns true if both weddings have the same identity and data fields.
     * This defines a stronger notion of equality between two weddings.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Wedding)) {
            return false;
        }

        Wedding otherWedding = (Wedding) other;
        return weddingName.equals(otherWedding.weddingName)
                && venue.equals(otherWedding.venue)
                && date.equals(otherWedding.date);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(weddingName, venue, date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("weddingName", weddingName)
                .add("venue", venue)
                .add("date", date)
                .toString();
    }
}
