package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.JOHN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * A utility class containing a list of {@code Wedding} objects to be used in tests.
 */
public class TypicalWeddings {

    public static final Wedding ALICE_WEDDING = new WeddingBuilder().withName("Alice's Wedding")
            .withVenue("Marina Bay Sands").withDate("2024-12-12").withClient(ALICE).build();

    public static final Wedding AMY_WEDDING = new WeddingBuilder().withName("Amy's Wedding")
            .withDate("2025-12-25").withClient(AMY).build();

    public static final Wedding ELLE_WEDDING = new WeddingBuilder().withName("Elle's Wedding")
            .withClient(ELLE).build();

    public static final Wedding GEORGE_WEDDING = new WeddingBuilder().withName("George's Wedding")
            .withVenue("Sentosa").withDate("2025-01-01").withClient(GEORGE).build();

    public static final Wedding JOHN_WEDDING = new WeddingBuilder().withName("John's Wedding")
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
        for (Person person : TypicalPersons.getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Wedding> getTypicalWeddings() {
        return new ArrayList<>(Arrays.asList(ALICE_WEDDING, AMY_WEDDING, ELLE_WEDDING, GEORGE_WEDDING, JOHN_WEDDING));
    }
}
