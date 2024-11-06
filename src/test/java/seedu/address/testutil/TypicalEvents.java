package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_FROM_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TO_DATE_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.id.counter.list.IdCounterList;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {
    public static final Event MEETING = new EventBuilder().withEventName(VALID_EVENT_NAME_MEETING)
            .withEventDescription(VALID_EVENT_DESCRIPTION_MEETING)
            .withEventDuration(VALID_EVENT_FROM_DATE_1, VALID_EVENT_TO_DATE_1).build();
    public static final Event WORKSHOP = new EventBuilder().withEventName(VALID_EVENT_NAME_WORKSHOP)
            .withEventDescription(VALID_EVENT_DESCRIPTION_WORKSHOP)
            .withEventDuration(VALID_EVENT_FROM_DATE_1, VALID_EVENT_TO_DATE_1).build();

    private TypicalEvents() {} // prevents instantiation

    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        IdCounterList idCounterList = new IdCounterList();
        int largestEventId = 0;
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
            if (event.getEventId() > largestEventId) {
                largestEventId = event.getEventId();
            }
        }
        idCounterList.setEventIdCounter(largestEventId);
        ab.setIdCounterList(idCounterList);
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(MEETING, WORKSHOP));
    }
}
