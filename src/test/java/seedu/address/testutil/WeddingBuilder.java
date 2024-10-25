package seedu.address.testutil;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Client;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;

/**
 * A utility class to help with building Wedding objects.
 */
public class WeddingBuilder {

    public static final String DEFAULT_NAME = "Alice Bob Wedding";
    public static final String DEFAULT_DATE = "2024-12-31";
    public static final String DEFAULT_VENUE = "Grand Hotel Ballroom";

    private Name name;
    private Client client;
    private Date date;
    private Venue venue;

    /**
     * Creates a {@code WeddingBuilder} with the default details.
     */
    public WeddingBuilder() {
        name = new Name(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        venue = new Venue(DEFAULT_VENUE);
    }

    /**
     * Initializes the WeddingBuilder with the data of {@code weddingToCopy}.
     */
    public WeddingBuilder(Wedding weddingToCopy) {
        name = weddingToCopy.getName();
        client = weddingToCopy.getClient();
        date = weddingToCopy.getDate();
        venue = weddingToCopy.getVenue();
    }

    /**
     * Sets the {@code Name} of the {@code Wedding} that we are building.
     */
    public WeddingBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Client} of the {@code Wedding} that we are building.
     */
    public WeddingBuilder withClient(Person person) {
        this.client = new Client(person);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Wedding} that we are building.
     */
    public WeddingBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code Wedding} that we are building.
     */
    public WeddingBuilder withVenue(String venue) {
        this.venue = new Venue(venue);
        return this;
    }

    /**
     * Builds a {@code Wedding} with the current attributes.
     * If no client has been set, builds a temporary wedding.
     */
    public Wedding build() {
        if (client == null) {
            return new Wedding(name, date, venue);
        }
        return new Wedding(name, client, date, venue);
    }
}
