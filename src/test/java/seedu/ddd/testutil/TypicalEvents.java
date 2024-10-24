package seedu.ddd.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.model.event.common.EventId;

/**
 *  A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {
    public static final Event WEDDING_A = new Event(new Description("This is wedding A"), new EventId(0));

    public static final Event WEDDING_B = new EventBuilder()
            .withDescription("This is wedding B")
            .withEventId(1)
            .build();

    private TypicalEvents() {} // prevents instantiation
    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(WEDDING_A, WEDDING_B));
    }
}
