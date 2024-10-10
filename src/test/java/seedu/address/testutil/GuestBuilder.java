package seedu.address.testutil;

import seedu.address.model.person.Guest;
import seedu.address.model.person.Rsvp;

/**
 * A utility class to help with building Guest objects.
 */
public class GuestBuilder extends PersonBuilder<GuestBuilder> {
    public static final String DEFAULT_RSVP = "PENDING";

    private Rsvp rsvp;

    /**
     * Creates a {@code GuestBuilder} with the default details.
     */
    public GuestBuilder() {
        super();
        rsvp = new Rsvp(DEFAULT_RSVP);
    }

    /**
     * Initializes the {@code GuestBuilder} with the data of {@code guestToCopy}.
     */
    public GuestBuilder(Guest guestToCopy) {
        super(guestToCopy);
        rsvp = guestToCopy.getRsvp();
    }

    /**
     * Sets the {@code Rsvp} of the {@code Guest} that we are building.
     */
    public GuestBuilder withRsvp(String rsvp) {
        this.rsvp = new Rsvp(rsvp);
        return this;
    }

    public Guest build() {
        return new Guest(getName(), getPhone(), getEmail(), getAddress(), getTags(), rsvp);
    }
}
