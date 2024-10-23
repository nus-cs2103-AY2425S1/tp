package seedu.ddd.testutil;

import static seedu.ddd.testutil.TypicalContacts.ALICE;
import static seedu.ddd.testutil.TypicalContacts.BENSON;
import static seedu.ddd.testutil.TypicalContacts.CARL;
import static seedu.ddd.testutil.TypicalContacts.DANIEL;
import static seedu.ddd.testutil.TypicalContacts.ELLE;
import static seedu.ddd.testutil.TypicalContacts.FIONA;
import static seedu.ddd.testutil.TypicalContacts.GEORGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ddd.model.event.common.Event;

/**
 *  A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {
    public static final Event WEDDING_A = new EventBuilder()
            .withDescription("This is wedding A")
            .withEventId(0)
            .withClients(new ArrayList<>(List.of(ALICE, CARL)))
            .withVendors(new ArrayList<>(List.of(BENSON, DANIEL)))
            .build();

    public static final Event WEDDING_B = new EventBuilder()
            .withDescription("This is wedding B")
            .withEventId(1)
            .withClients(new ArrayList<>(List.of(ELLE, FIONA)))
            .withVendors(new ArrayList<>(List.of(BENSON, DANIEL, GEORGE)))
            .build();

    private TypicalEvents() {} // prevents instantiation
    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(WEDDING_A, WEDDING_B));
    }
}
