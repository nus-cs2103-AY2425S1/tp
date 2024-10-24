package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_EVENT_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_EVENT_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_EVENT_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_EVENT_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_EVENT_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_EVENT_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_EVENT_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_EVENT_B;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.ChronologicalOrderException;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event EVENT_A;
    public static final Event EVENT_B;
    public static final Event EVENT_C;
    public static final Event EVENT_D;
    public static final Event EVENT_E;

    static {
        try {
            EVENT_A = new EventBuilder().withEventName("Meeting with Client")
                    .withLocation("123 Orchard Road")
                    .withDate("2024-10-30")
                    .withStartTime("09:00")
                    .withEndTime("11:00")
                    .withDescription("Discuss new project plans").build();

            EVENT_B = new EventBuilder().withEventName("Team Lunch")
                    .withLocation("456 Jurong East")
                    .withDate("2024-11-01")
                    .withStartTime("12:00")
                    .withEndTime("14:00")
                    .withDescription("Casual lunch with team").build();

            EVENT_C = new EventBuilder().withEventName("Project Demo")
                    .withLocation("789 Bukit Timah Road")
                    .withDate("2024-12-10")
                    .withStartTime("15:00")
                    .withEndTime("17:00")
                    .withDescription("Final project demo for the client").build();

            EVENT_D = new EventBuilder().withEventName("Conference")
                    .withLocation("Convention Center")
                    .withDate("2025-01-15")
                    .withStartTime("10:00")
                    .withEndTime("18:00")
                    .withDescription("Annual tech conference").build();

            EVENT_E = new EventBuilder().withEventName("Volunteer Meeting")
                    .withLocation("Community Hall")
                    .withDate("2025-02-20")
                    .withStartTime("14:00")
                    .withEndTime("16:00")
                    .withDescription("Planning for upcoming charity event").build();
        } catch (ChronologicalOrderException e) {
            throw new AssertionError("Error creating typical events for testing.", e);
        }
    }

    // Manually added
    public static final Event MANUAL_EVENT_F = new EventBuilder().withEventName("Manual Event F")
            .withLocation("Manual Location F")
            .withDate("2025-03-10")
            .withStartTime("10:00")
            .withEndTime("12:00")
            .withDescription("Manually added event F").build();

    public static final Event MANUAL_EVENT_G = new EventBuilder().withEventName("Manual Event G")
            .withLocation("Manual Location G")
            .withDate("2025-04-01")
            .withStartTime("09:00")
            .withEndTime("11:30")
            .withDescription("Manually added event G").build();

    // Manually added - Event details from CommandTestUtil
    public static final Event TYPICAL_EVENT_A = new EventBuilder().withEventName(VALID_EVENT_NAME_A)
            .withLocation(VALID_LOCATION_EVENT_A)
            .withDate(VALID_DATE_EVENT_A)
            .withStartTime(VALID_START_TIME_EVENT_A)
            .withEndTime(VALID_END_TIME_EVENT_A)
            .withDescription(VALID_DESCRIPTION_EVENT_A).build();

    public static final Event TYPICAL_EVENT_B = new EventBuilder().withEventName(VALID_EVENT_NAME_B)
            .withLocation(VALID_LOCATION_EVENT_B)
            .withDate(VALID_DATE_EVENT_B)
            .withStartTime(VALID_START_TIME_EVENT_B)
            .withEndTime(VALID_END_TIME_EVENT_B)
            .withDescription(VALID_DESCRIPTION_EVENT_B).build();

    private TypicalEvents() {} // Prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook eb = new AddressBook();
        for (Event event : getTypicalEvents()) {
            eb.addEvent(event);
        }
        return eb;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(EVENT_A, EVENT_B, EVENT_C, EVENT_D, EVENT_E));
    }
}
