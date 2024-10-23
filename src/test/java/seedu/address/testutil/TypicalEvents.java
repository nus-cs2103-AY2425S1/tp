package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event HAIRCUT = new EventBuilder().withName("Haircut")
            .withTime("2021-10-02 14:00").withVenue("Nice Salon")
            .withCelebrity(TypicalPersons.ALICE).build();
    public static final Event PHOTOSHOOT = new EventBuilder().withName("Photoshoot")
            .withTime("2021-10-03 14:00").withVenue("Nice Studio")
            .withCelebrity(TypicalPersons.ALICE).build();
    public static final Event MOVIE_PREMIERE = new EventBuilder().withName("Movie Premiere")
            .withTime("2021-10-04 14:00").withVenue("Nice Cinema")
            .withCelebrity(TypicalPersons.BENSON).build();
    public static final Event FAN_MEET = new EventBuilder().withName("Fan Meet")
            .withTime("2021-10-05 14:00").withVenue("Nice Hall")
            .withCelebrity(TypicalPersons.CARL).build();
    public static final Event OSCARS = new EventBuilder().withName("Oscars")
            .withTime("2021-10-06 14:00").withVenue("Nice Cinema")
            .withCelebrity(TypicalPersons.DANIEL).build();

    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(HAIRCUT, PHOTOSHOOT, MOVIE_PREMIERE, FAN_MEET, OSCARS));
    }
}
