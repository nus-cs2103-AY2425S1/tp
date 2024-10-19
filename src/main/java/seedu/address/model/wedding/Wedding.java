package seedu.address.model.wedding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents a wedding that has a name, venue, datetime and a list of participants
 */
public class Wedding {
    private final WeddingName weddingName;
    private final Venue venue;
    private final Datetime datetime;
    private final List<Person> participants;

    /**
     * Constructs a {@code Wedding}.
     *
     * @param weddingName A valid wedding name.
     */
    public Wedding(WeddingName weddingName, Venue venue, Datetime datetime) {
        this.weddingName = weddingName;
        this.venue = venue;
        this.datetime = datetime;
        this.participants = new ArrayList<>();
    }
    public WeddingName getWeddingName() {
        return this.weddingName;
    }
    public Venue getVenue() {
        return this.venue;
    }
    public Datetime getDatetime() {
        return this.datetime;
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
                && datetime.equals(otherWedding.datetime);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(weddingName, venue, datetime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("weddingName", weddingName)
                .add("venue", venue)
                .add("datetime", datetime)
                .toString();
    }
}
