package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {
    public static final Wedding ALICE_WEDDING = new WeddingBuilder().withName("Alice Adam Wedding")
            .withVenue("Marina Bay Sands").withDate("2024-12-12").build();

    public static final Wedding AMY_WEDDING = new WeddingBuilder().withName("Amy Jack Wedding")
            .withDate("2025-12-25").build();

    public static final Wedding ELLE_WEDDING = new WeddingBuilder().withName("Elle George Wedding").build();

    public static final Wedding GEORGE_WEDDING = new WeddingBuilder().withName("George Jane Wedding")
            .withVenue("Sentosa").withDate("2025-01-01").build();

    public static final Wedding JOHN_WEDDING = new WeddingBuilder().withName("John Jane Wedding")
            .withVenue("Fullerton").build();

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withRole("florist")
            .withOwnWedding(ALICE_WEDDING)
            .build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98756432").withRole("caterer")
            .addWeddingJob(GEORGE_WEDDING).build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withRole("florist")
            .addWeddingJob(GEORGE_WEDDING).build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street")
            .withRole("baker").build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94822244")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withRole("florist").build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94824277")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withRole("florist").build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("94824422")
            .withEmail("anna@example.com").withAddress("4th street").withOwnWedding(GEORGE_WEDDING).build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84824244")
            .withEmail("stefan@example.com").withAddress("little india").build();

    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84821311")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    public static final Person CARLDUH = new PersonBuilder().withName("Carl Duh Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street 1")
            .withRole("baker").build();

    public static final Person JOHN = new PersonBuilder().withName("John").withPhone("94351253")
            .withEmail("example@test.com").withAddress("Main St")
            .withRole("tailor").build();


    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withRole(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withRole(VALID_TAG_HUSBAND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static AddressBook getTypicalAddressBookFilter() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersonsFilter()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static AddressBook getTypicalAddressBookFilterWithWeddings() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersonsFilter()) {
            ab.addPerson(person);
        }
        for (Wedding wedding : getTypicalValidWeddings()) {
            ab.addWedding(wedding);
        }
        return ab;
    }

    public static List<Wedding> getTypicalValidWeddings() {
        return new ArrayList<>(Arrays.asList(ALICE_WEDDING, GEORGE_WEDDING));
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Person> getTypicalPersonsFilter() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, JOHN));
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons and an additional person.
     */
    public static AddressBook getAdditionalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getAdditionalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getAdditionalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, CARLDUH, DANIEL, ELLE, FIONA, GEORGE));
    }
}
