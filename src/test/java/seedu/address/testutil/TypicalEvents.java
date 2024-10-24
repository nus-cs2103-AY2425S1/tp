package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_WEDDING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in
 * tests.
 */
public class TypicalEvents {

    public static final Event ALICE = new EventBuilder().withName("Alice Pauline Wedding")
            .withDate("2021-10-10").build();
    public static final Event BENSON = new EventBuilder().withName("Benson Meier Birthday")
            .withDate("2021-11-11").withTags("party", "alcohol", "classmates").build();
    public static final Event CARL = new EventBuilder().withName("Carl Kurz Tea Party")
            .withDate("2021-12-12").build();
    public static final Event DANIEL = new EventBuilder().withName("Daniel Meier Farewell")
            .withDate("2022-01-01").build();
    public static final Event ELLE = new EventBuilder().withName("Elle Meyer Banquet")
            .withDate("2022-02-02").build();
    public static final Event FIONA = new EventBuilder().withName("Fiona Kunz Reunion")
            .withDate("2022-03-03").build();
    public static final Event GEORGE = new EventBuilder().withName("George Best Concert")
            .withDate("2022-04-04").build();
    public static final Event HOON = new EventBuilder().withName("Hoon Meier Seminar")
            .withDate("2022-05-05").build();
    public static final Event IDA = new EventBuilder().withName("Ida Mueller Workshop")
            .withDate("2022-06-06").build();

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final Event WEDDING = new EventBuilder().withName(VALID_NAME_WEDDING)
            .withDate(VALID_DATE_WEDDING).build();
    public static final Event BIRTHDAY = new EventBuilder().withName(VALID_NAME_BIRTHDAY)
            .withDate(VALID_DATE_BIRTHDAY).build();

    private TypicalEvents() {
    } // prevents instantiation

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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
