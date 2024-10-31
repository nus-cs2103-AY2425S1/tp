package seedu.address.testutil;

import static seedu.address.testutil.EventBuilder.DEFAULT_ATTENDEES;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EventBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Address;



/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event EVENT_A = new EventBuilder()
            .withName("Event A").withDate(LocalDate.of(2023, 10, 01),
                    LocalDate.of(2023, 10, 01)).build();
    public static final Event EVENT_B = new EventBuilder()
            .withName("Event B").withDate(LocalDate.of(2023, 10, 02),
                    LocalDate.of(2023, 10, 10)).build();
    public static final Event EVENT_C = new EventBuilder()
            .withName("Event C").withDate(LocalDate.of(2023, 10, 03),
                    LocalDate.of(2023, 10, 11)).build();
    // Event with no attendees
    public static final Event EVENT_NO_ATTENDEE = new EventBuilder()
            .withName("Workshop")
            .withDate(LocalDate.of(2023, 10, 01))
            .withLocation(new Address("123 Main Street"))
            .build();

    // Event with multiple attendees
    public static final Event EVENT_MULTIPLE_ATTENDEE = new EventBuilder()
            .withName("Family Gathering")
            .withDate(LocalDate.of(2023, 10, 03))
            .withLocation(new Address("89 City Hall"))
            .withAttendees(DEFAULT_ATTENDEES)
            .build();

    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code EventBook} with all the typical events.
     */
    public static EventBook getTypicalEventBook() {
        EventBook eb = new EventBook();
        for (Event event : getTypicalEvents()) {
            eb.addEvent(event);
        }
        return eb;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(EVENT_NO_ATTENDEE, EVENT_MULTIPLE_ATTENDEE));
    }
}
