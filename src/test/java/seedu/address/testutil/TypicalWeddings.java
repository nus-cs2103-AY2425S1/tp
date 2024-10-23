package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.WeddingBook;
import seedu.address.model.wedding.Wedding;

/**
 * A utility class containing a list of {@code Wedding} objects to be used in tests.
 */
public class TypicalWeddings {

    public static final Wedding WEDDING_ONE = new WeddingBuilder().withWeddingName("John Loh & Jean Tan")
            .withVenue("Orchard Hotel")
            .withDatetime("12/11/2024").build();
    public static final Wedding WEDDING_TWO = new WeddingBuilder().withWeddingName("David Lee & Clara Wong")
            .withVenue("Shangri-La Hotel")
            .withDatetime("24/12/2023").build();
    public static final Wedding WEDDING_THREE = new WeddingBuilder().withWeddingName("Alex Ng & Michelle Lim")
            .withVenue("Marina Bay Sands")
            .withDatetime("01/01/2024").build();

    // Manually added
    public static final Wedding WEDDING_FOUR = new WeddingBuilder().withWeddingName("James Tan & Emily Koh")
            .withVenue("Sentosa Resort")
            .withDatetime("05/02/2024").build();
    public static final Wedding WEDDING_FIVE = new WeddingBuilder().withWeddingName("George Lee & Susan Tay")
            .withVenue("Botanic Gardens")
            .withDatetime("15/03/2024").build();

    private TypicalWeddings() {} // prevents instantiation

    public static List<Wedding> getTypicalWeddings() {
        return new ArrayList<>(Arrays.asList(WEDDING_ONE, WEDDING_TWO, WEDDING_THREE));
    }

    /**
     * Returns an {@code WeddingBook} with all the typical weddings.
     */
    public static WeddingBook getTypicalWeddingBook() {
        WeddingBook wb = new WeddingBook();
        for (Wedding wedding : getTypicalWeddings()) {
            wb.addWedding(wedding);
        }
        return wb;
    }
}
