package seedu.ddd.testutil.event;

import static seedu.ddd.testutil.contact.TypicalContacts.ALICE;
import static seedu.ddd.testutil.contact.TypicalContacts.BENSON;
import static seedu.ddd.testutil.contact.TypicalContacts.CARL;
import static seedu.ddd.testutil.contact.TypicalContacts.DANIEL;
import static seedu.ddd.testutil.contact.TypicalContacts.ELLE;
import static seedu.ddd.testutil.contact.TypicalContacts.FIONA;
import static seedu.ddd.testutil.contact.TypicalContacts.GEORGE;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_CLIENT_LIST;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_VENDOR_LIST;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_DATE;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_DESCRIPTION_1;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_NAME;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.ddd.model.event.common.Event;

/**
 *  A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {
    public static final Event WEDDING_A = new EventBuilder()
            .withName(VALID_EVENT_NAME)
            .withDescription("This is wedding A")
            .withDate(VALID_EVENT_DATE)
            .withClients(ALICE, CARL)
            .withVendors(BENSON, DANIEL)
            .withEventId(0)
            .build();
    public static final Event WEDDING_B = new EventBuilder()
            .withName(VALID_EVENT_NAME)
            .withDescription("This is wedding B")
            .withDate(VALID_EVENT_DATE)
            .withClients(ELLE, FIONA)
            .withVendors(BENSON, DANIEL, GEORGE)
            .withEventId(1)
            .build();
    public static final Event VALID_EVENT = new EventBuilder()
            .withName(VALID_EVENT_NAME)
            .withDescription(VALID_EVENT_DESCRIPTION_1)
            .withDate(VALID_EVENT_DATE)
            .withClients(DEFAULT_EVENT_CLIENT_LIST)
            .withVendors(DEFAULT_EVENT_VENDOR_LIST)
            .build();

    private TypicalEvents() {} // prevents instantiation

    public static List<Event> getTypicalEvents() {
        return Stream.of(WEDDING_A, WEDDING_B)
                .map(event -> new EventBuilder(event).build())
                .collect(Collectors.toList());
    }
}
