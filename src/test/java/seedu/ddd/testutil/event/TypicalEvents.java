package seedu.ddd.testutil.event;

import static seedu.ddd.testutil.contact.TypicalContacts.*;
import static seedu.ddd.testutil.event.TypicalEventFields.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            .withClients(DEFAULT_CLIENT_LIST)
            .withVendors(DEFAULT_VENDOR_LIST)
            .build();

    private TypicalEvents() {} // prevents instantiation

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(WEDDING_A, WEDDING_B));
    }

}
