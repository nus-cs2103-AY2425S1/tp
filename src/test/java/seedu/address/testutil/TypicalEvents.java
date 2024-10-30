package seedu.address.testutil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EventBook;
import seedu.address.model.event.Event;

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
        return new ArrayList<>(Arrays.asList(EVENT_A, EVENT_B, EVENT_C));
    }
}
