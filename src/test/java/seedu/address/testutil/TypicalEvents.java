package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ART_EXHIBIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOOK_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ART_EXHIBIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOOK_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_ART_EXHIBIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_BOOK_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CULTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LITERATURE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.types.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {
    public static final Event ANIME = new EventBuilder().withName("Anime Expo")
            .withAddress("123, Jurong West Ave 6, #08-111").withStartTime("2024-10-15 14:30")
            .withTags("hobby").build();
    public static final Event BARBEQUE = new EventBuilder().withName("Barbeque Party")
            .withAddress("231, Yishun Ave 2, #10-03").withStartTime("2024-12-20 16:30")
            .withTags("hobby", "friends").build();
    public static final Event CONCERT = new EventBuilder().withName("Concert Night")
            .withAddress("123, Orchard Rd, #05-01").withStartTime("2024-11-15 19:00")
            .withTags("music").build();

    public static final Event DINNER = new EventBuilder().withName("Family Dinner")
            .withAddress("45, Changi Rd, #02-02").withStartTime("2024-11-30 18:00").build();

    public static final Event EXHIBITION = new EventBuilder().withName("Art Exhibition")
            .withAddress("88, Bukit Timah Rd, #03-04").withStartTime("2024-10-28 10:00").build();

    public static final Event FASHION_SHOW = new EventBuilder().withName("Fashion Show")
            .withAddress("12, Marina Blvd, #01-01").withStartTime("2024-11-10 20:00").build();

    public static final Event GALA = new EventBuilder().withName("Charity Gala")
            .withAddress("77, Raffles Ave, #10-07").withStartTime("2024-12-05 19:30").build();

    public static final Event HIKING_TRIP = new EventBuilder().withName("Weekend Hiking Trip")
            .withAddress("Trailhead at Bukit Timah").withStartTime("2024-11-22 08:00").build();

    public static final Event ICE_CREAM_FEST = new EventBuilder().withName("Ice Cream Festival")
            .withAddress("5, Clarke Quay").withStartTime("2024-12-01 14:00").build();

    public static final Event ART_EXHIBIT = new EventBuilder().withName(VALID_NAME_ART_EXHIBIT)
            .withAddress(VALID_ADDRESS_ART_EXHIBIT).withStartTime(VALID_START_TIME_ART_EXHIBIT)
            .withTags(VALID_TAG_CULTURE).build();

    public static final Event BOOK_FAIR = new EventBuilder().withName(VALID_NAME_BOOK_FAIR)
            .withAddress(VALID_ADDRESS_BOOK_FAIR).withStartTime(VALID_START_TIME_BOOK_FAIR)
            .withTags(VALID_TAG_CULTURE, VALID_TAG_LITERATURE).build();

    private TypicalEvents() {} //prevents instantiation

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
        return new ArrayList<>(Arrays.asList(ANIME, BARBEQUE, CONCERT, DINNER, EXHIBITION, FASHION_SHOW, GALA));
    }
}
