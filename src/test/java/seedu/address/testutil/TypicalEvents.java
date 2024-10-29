package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event TECH_CONFERENCE = new EventBuilder().withName("Tech Conference 2024")
            .withAttendees(getPersonSet(TypicalPersons.ALICE, TypicalPersons.FIONA))
            .withVendors(getPersonSet(TypicalPersons.DANIEL))
            .withSponsors(getPersonSet(TypicalPersons.BENSON, TypicalPersons.CARL))
            .withVolunteers(getPersonSet(TypicalPersons.ELLE))
            .build();

    public static final Event ART_EXHIBITION = new EventBuilder().withName("Art Exhibition 2024")
            .withAttendees(getPersonSet(TypicalPersons.BOB))
            .withVendors(getPersonSet(TypicalPersons.AMY, TypicalPersons.GEORGE))
            .withSponsors(getPersonSet(TypicalPersons.BENSON))
            .withVolunteers(getPersonSet(TypicalPersons.HOON, TypicalPersons.IDA))
            .build();

    // Manually added events
    public static final Event SPORTS_FESTIVAL = new EventBuilder().withName("Sports Festival")
            .withAttendees(getPersonSet(TypicalPersons.ALICE))
            .withVendors(getPersonSet(TypicalPersons.DANIEL))
            .withSponsors(getPersonSet(TypicalPersons.BENSON))
            .withVolunteers(getPersonSet(TypicalPersons.ELLE))
            .build();

    // Manually added events
    public static final Event TEST_EVENT = new EventBuilder().withName("Test Event")
            .withAttendees(getPersonSet(TypicalPersons.ALICE))
            .withVendors(getPersonSet(TypicalPersons.DANIEL))
            .withSponsors(getPersonSet(TypicalPersons.BENSON))
            .withVolunteers(getPersonSet(TypicalPersons.ELLE))
            .build();

    public static final Event TEST_EVENT_2 = new EventBuilder().withName("Test Event 2")
            .withAttendees(getPersonSet(TypicalPersons.ALICE))
            .withVendors(getPersonSet(TypicalPersons.DANIEL))
            .withSponsors(getPersonSet(TypicalPersons.BENSON))
            .withVolunteers(getPersonSet(TypicalPersons.ELLE))
            .build();


    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns a list of typical events.
     */
    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(TECH_CONFERENCE, ART_EXHIBITION, SPORTS_FESTIVAL));
    }

    /**
     * Returns an {@code EventManager} with all the typical events.
     */
    public static EventManager getTypicalEventManager() {
        EventManager em = new EventManager();
        for (Event event : getTypicalEvents()) {
            em.addEvent(event);
        }
        return em;
    }

    /**
     * Helper method that returns a set of persons.
     */
    public static Set<Person> getPersonSet(Person... persons) {
        return Arrays.stream(persons).collect(Collectors.toSet());
    }
}
