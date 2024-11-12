package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_CREATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOG_MESSAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;
import seedu.address.model.person.PropertyList;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {
    // Create properties for each person
    public static final Property ALICE_PROPERTY = Property.of(
            "Block 14 Clementi Ave 3 #10-10", "Clementi", "HDB", 100.0, 2, 2, 600000);
    public static final Property BENSON_PROPERTY = Property.of(
            "23, Holland Road", "Holland Village", "Condo", 85.0, 1, 1, 800000);
    public static final Property CARL_PROPERTY = Property.of(
            "45, Marine Parade", "Marine Parade", "Landed", 300.0, 5, 4, 2500000);
    public static final Property DANIEL_PROPERTY = Property.of(
            "88, Orchard Road", "Orchard", "Condo", 120.0, 3, 2, 1500000);
    public static final Property ELLE_PROPERTY = Property.of(
            "99, Yishun Ave 11", "Yishun", "HDB", 95.0, 3, 2, 500000);
    public static final Property FIONA_PROPERTY = Property.of(
            "10, Serangoon Ave 5", "Serangoon", "Landed", 350.0, 4, 3, 2200000);
    public static final Property GEORGE_PROPERTY = Property.of(
            "77, Bukit Timah Road", "Bukit Timah", "Condo", 70.0, 2, 1, 900000);
    public static final Property HOON_PROPERTY = Property.of("876 Fir Way",
            "Springfield", "HDB", 65.0, 1, 1, 150000);
    public static final Property IDA_PROPERTY = Property.of("543 Willow Ln",
            "Springfield", "Landed", 100.0, 3, 2, 450000);
    public static final Property AMY_PROPERTY = Property.of("321 Cherry Pl",
            "Springfield", "Condo", 95.0, 3, 2, 320000);
    public static final Property BOB_PROPERTY = Property.of("654 Poplar St",
            "Springfield", "HDB", 80.0, 2, 1, 190000);

    // Assign properties to each person’s property list
    public static final PropertyList ALICE_PROPERTY_LIST = new PropertyList(ALICE_PROPERTY);
    public static final PropertyList BENSON_PROPERTY_LIST = new PropertyList(BENSON_PROPERTY);
    public static final PropertyList CARL_PROPERTY_LIST = new PropertyList(CARL_PROPERTY);
    public static final PropertyList DANIEL_PROPERTY_LIST = new PropertyList(DANIEL_PROPERTY);
    public static final PropertyList ELLE_PROPERTY_LIST = new PropertyList(ELLE_PROPERTY);
    public static final PropertyList FIONA_PROPERTY_LIST = new PropertyList(FIONA_PROPERTY);
    public static final PropertyList GEORGE_PROPERTY_LIST = new PropertyList(GEORGE_PROPERTY);
    public static final PropertyList HOON_PROPERTY_LIST = new PropertyList(HOON_PROPERTY);
    public static final PropertyList IDA_PROPERTY_LIST = new PropertyList(IDA_PROPERTY);
    public static final PropertyList AMY_PROPERTY_LIST = new PropertyList(AMY_PROPERTY);
    public static final PropertyList BOB_PROPERTY_LIST = new PropertyList(BOB_PROPERTY);

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withDateOfCreation("2024-01-12")
            .withHistory(LocalDate.of(2024, 1, 12), "Created")
            .withHistory(LocalDate.of(2024, 10, 10), "Attended seminar")
            .withHistory(LocalDate.of(2024, 10, 11), "Met with client")
            .withRemark("").withBirthday("2001-01-01")
            .withTags("friends")
            .withPropertyList(ALICE_PROPERTY_LIST) // Using static property list
            .build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends")
            .withRemark("")
            .withBirthday("2001-02-02")
            .withDateOfCreation("2022-09-19")
            .withHistory(LocalDate.of(2022, 9, 19), "Created")
            .withHistory(LocalDate.of(2024, 9, 25), "Completed project A")
            .withHistory(LocalDate.of(2024, 10, 1), "Attended team meeting")
            .withPropertyList(BENSON_PROPERTY_LIST) // Using static property list
            .build();

    public static final Person NO_BIRTHDAY_BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends")
            .withRemark("")
            .withBirthday("")
            .withDateOfCreation("2022-09-19")
            .withHistory(LocalDate.of(2022, 9, 19), "Created")
            .withHistory(LocalDate.of(2024, 9, 25), "Completed project A")
            .withHistory(LocalDate.of(2024, 10, 1), "Attended team meeting")
            .withPropertyList(BENSON_PROPERTY_LIST) // Using static property list
            .build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("Wall Street")
            .withRemark("")
            .withBirthday("2001-03-03")
            .withDateOfCreation("2021-09-10")
            .withHistory(LocalDate.of(2021, 9, 10), "Created")
            .withPropertyList(CARL_PROPERTY_LIST) // Using static property list
            .build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th Street").withTags("friends")
            .withRemark("")
            .withBirthday("2001-04-04")
            .withDateOfCreation("2012-02-19")
            .withHistory(LocalDate.of(2012, 2, 19), "Created")
            .withPropertyList(DANIEL_PROPERTY_LIST) // Using static property list
            .build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("Michegan Ave")
            .withRemark("")
            .withBirthday("2001-05-05")
            .withDateOfCreation("2024-02-20")
            .withHistory(LocalDate.of(2024, 2, 20), "Created")
            .withPropertyList(ELLE_PROPERTY_LIST) // Using static property list
            .build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("Little Tokyo")
            .withRemark("")
            .withBirthday("2001-06-06")
            .withDateOfCreation("2018-01-29")
            .withHistory(LocalDate.of(2018, 1, 29), "Created")
            .withPropertyList(FIONA_PROPERTY_LIST) // Using static property list
            .build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th Street")
            .withRemark("")
            .withBirthday("2001-07-07")
            .withDateOfCreation("2019-12-11")
            .withHistory(LocalDate.of(2019, 12, 11), "Created")
            .withPropertyList(GEORGE_PROPERTY_LIST) // Using static property list
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("4th Street")
            .withRemark("")
            .withBirthday("2001-08-08")
            .withDateOfCreation("2020-03-03")
            .withHistory(LocalDate.of(2020, 3, 3), "Created")
            .withPropertyList(HOON_PROPERTY_LIST) // Using static property list
            .build();

    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("9482448")
            .withEmail("frank@example.com").withAddress("High Street")
            .withRemark("")
            .withBirthday("2001-09-09")
            .withDateOfCreation("2022-06-15")
            .withHistory(LocalDate.of(2022, 6, 15), "Created")
            .withPropertyList(IDA_PROPERTY_LIST) // Using static property list
            .build();

    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_FRIEND).withRemark(VALID_REMARK_AMY)
            .withBirthday(VALID_BIRTHDAY_AMY)
            .withDateOfCreation(VALID_DATE_OF_CREATION)
            .withHistory(LocalDate.now(), VALID_LOG_MESSAGE)
            .build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withRemark(VALID_REMARK_BOB).withBirthday(VALID_BIRTHDAY_BOB)
            .build();

    // Prevent instantiation
    private TypicalPersons() {}

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

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
