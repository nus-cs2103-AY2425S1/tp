package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.wedding.Datetime;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * A utility class to help with building wedding
 */
public class WeddingBuilder {

    public static final String DEFAULT_WEDDING_NAME = "Jonus Ho & Izzat Syazani";
    public static final String DEFAULT_VENUE = "Pasir Ris Hotel Ball Room";
    public static final String DEFAULT_DATETIME = "02/11/2024";

    private WeddingName weddingName;
    private Venue venue;
    private Datetime datetime;
    private Set<Person> participants;

    /**
     * Creates a {@code WeddingBuilder} with the default details.
     */
    public WeddingBuilder() {
        weddingName = new WeddingName(DEFAULT_WEDDING_NAME);
        venue = new Venue(DEFAULT_VENUE);
        datetime = new Datetime(DEFAULT_DATETIME);
        participants = new HashSet<>();
    }

    /**
     * Initializes the WeddingBuilder with the data of {@code weddingToCopy}.
     */
    public WeddingBuilder(Wedding weddingToCopy) {
        weddingName = weddingToCopy.getWeddingName();
        venue = weddingToCopy.getVenue();
        datetime = weddingToCopy.getDatetime();
        participants = weddingToCopy.getParticipants();
    }

    /**
     * Sets the {@code weddingName} of the {@code Wedding} that we are building.
     */
    public WeddingBuilder withWeddingName(String name) {
        this.weddingName = new WeddingName(name);
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
     * Sets the {@code Datetime} of the {@code Wedding} that we are building.
     */
    public WeddingBuilder withDatetime(String datetime) {
        this.datetime = new Datetime(datetime);
        return this;
    }

    /**
     * Parses the {@code participants} into a {@code Set<Person>} and set it to the {@code Wedding} that we
     * are building.
     */
    public WeddingBuilder withParticipant(Person ... participants) {
        this.participants = SampleDataUtil.getParticipantSet(participants);
        return this;
    }

    public Wedding build() {
        return new Wedding(weddingName, venue, datetime, participants);
    }

}
