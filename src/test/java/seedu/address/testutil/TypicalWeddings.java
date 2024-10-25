package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Wedding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.JOHN;

/**
 * A utility class containing a list of {@code Wedding} objects to be used in tests.
 */
public class TypicalWeddings {


    public static final Wedding ALICE_WEDDING = new WeddingBuilder().withName("Alice Bob")
            .withVenue("Marina Bay Sands").withDate(new Date("2024-12-12")).withClient(ALICE).build();

    public static final Wedding AMY_WEDDING = new WeddingBuilder().withName("Amy Jack")
            .withDate("2025-12-25").withClient(AMY).build();

    public static final Wedding ELLE_WEDDING = new WeddingBuilder().withName("ELLE George")
            .withClient(ELLE).build();

    public static final Wedding GEORGE_WEDDING = new WeddingBuilder().withName("George Jane")
            .withVenue("Sentosa").withDate("2025-01-01").withClient(GEORGE).build();

    public static final Wedding JOHN_WEDDING = new WeddingBuilder().withName("John Jane")
            .withVenue("Fullerton").withClient(JOHN).build();

    private TypicalWeddings() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Wedding wedding : getTypicalWeddings()) {
            ab.addWedding(wedding);
        }
        return ab;
    }

    public static List<Wedding> getTypicalWeddings() {
        return new ArrayList<>(Arrays.asList(ALICE_WEDDING, AMY_WEDDING, ELLE_WEDDING, GEORGE_WEDDING, JOHN_WEDDING));
    }
}
