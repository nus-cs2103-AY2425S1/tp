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
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

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
    private static final Property aliceProperty = Property.of("123 Maple St", "Springfield", "Condo", 85.0, 2, 1, 250000);
    private static final Property bensonProperty = Property.of("456 Oak Ave", "Springfield", "HDB", 75.0, 3, 2, 200000);
    private static final Property carlProperty = Property.of("789 Pine Rd", "Springfield", "Landed", 120.0, 4, 3, 500000);
    private static final Property danielProperty = Property.of("321 Elm St", "Springfield", "Condo", 90.0, 2, 2, 300000);
    private static final Property elleProperty = Property.of("654 Birch Blvd", "Springfield", "HDB", 70.0, 2, 1, 180000);
    private static final Property fionaProperty = Property.of("987 Cedar Ct", "Springfield", "Landed", 110.0, 4, 3, 550000);
    private static final Property georgeProperty = Property.of("234 Spruce Dr", "Springfield", "Condo", 80.0, 2, 2, 275000);
    private static final Property hoonProperty = Property.of("876 Fir Way", "Springfield", "HDB", 65.0, 1, 1, 150000);
    private static final Property idaProperty = Property.of("543 Willow Ln", "Springfield", "Landed", 100.0, 3, 2, 450000);
    private static final Property amyProperty = Property.of("321 Cherry Pl", "Springfield", "Condo", 95.0, 3, 2, 320000);
    private static final Property bobProperty = Property.of("654 Poplar St", "Springfield", "HDB", 80.0, 2, 1, 190000);

    private static final PropertyList alicePropertyList = new PropertyList(aliceProperty);
    private static final PropertyList bensonPropertyList = new PropertyList(bensonProperty);
    private static final PropertyList carlPropertyList = new PropertyList(carlProperty);
    private static final PropertyList danielPropertyList = new PropertyList(danielProperty);
    private static final PropertyList ellePropertyList = new PropertyList(elleProperty);
    private static final PropertyList fionaPropertyList = new PropertyList(fionaProperty);
    private static final PropertyList georgePropertyList = new PropertyList(georgeProperty);
    private static final PropertyList hoonPropertyList = new PropertyList(hoonProperty);
    private static final PropertyList idaPropertyList = new PropertyList(idaProperty);
    private static final PropertyList amyPropertyList = new PropertyList(amyProperty);
    private static final PropertyList bobPropertyList = new PropertyList(bobProperty);

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withDateOfCreation("2024-01-12")
            .withHistory(LocalDate.of(2024, 1, 12), "Created")
            .withHistory(LocalDate.of(2024, 10, 10), "Attended seminar")
            .withHistory(LocalDate.of(2024, 10, 11), "Met with client")
            .withRemark("").withBirthday("2001-01-01")
            .withTags("friends")
            .withPropertyList(alicePropertyList) // Using static property list
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
            .withPropertyList(bensonPropertyList) // Using static property list
            .build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withRemark("")
            .withBirthday("2001-03-03")
            .withDateOfCreation("2021-09-10")
            .withHistory(LocalDate.of(2021, 9, 10), "Created")
            .withPropertyList(carlPropertyList) // Using static property list
            .build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends")
            .withRemark("")
            .withBirthday("2001-04-04")
            .withDateOfCreation("2012-02-19")
            .withHistory(LocalDate.of(2012, 2, 19), "Created")
            .withPropertyList(danielPropertyList) // Using static property list
            .build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withRemark("")
            .withBirthday("2001-05-05")
            .withDateOfCreation("2024-02-20")
            .withHistory(LocalDate.of(2024, 2, 20), "Created")
            .withPropertyList(ellePropertyList) // Using static property list
            .build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withRemark("")
            .withBirthday("2001-06-06")
            .withDateOfCreation("2018-01-29")
            .withHistory(LocalDate.of(2018, 1, 29), "Created")
            .withPropertyList(fionaPropertyList) // Using static property list
            .build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street")
            .withRemark("")
            .withBirthday("2001-07-07")
            .withDateOfCreation("2019-12-11")
            .withHistory(LocalDate.of(2019, 12, 11), "Created")
            .withPropertyList(georgePropertyList) // Using static property list
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("4th street")
            .withRemark("")
            .withBirthday("2001-08-08")
            .withDateOfCreation("2020-03-03")
            .withHistory(LocalDate.of(2020, 3, 3), "Created")
            .withPropertyList(hoonPropertyList) // Using static property list
            .build();

    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("9482448")
            .withEmail("frank@example.com").withAddress("high street")
            .withRemark("")
            .withBirthday("2001-09-09")
            .withDateOfCreation("2022-06-15")
            .withHistory(LocalDate.of(2022, 6, 15), "Created")
            .withPropertyList(idaPropertyList) // Using static property list
            .build();

    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withRemark(VALID_REMARK_AMY).withBirthday(VALID_BIRTHDAY_AMY)
            .withDateOfCreation(VALID_DATE_OF_CREATION)
            .withHistory(LocalDate.of(2024, 1, 12), "Created")
            .withHistory(LocalDate.of(2024, 10, 10), "Attended seminar")
            .withHistory(LocalDate.of(2024, 10, 11), "Met with client")
            .withPropertyList(amyPropertyList) // Using static property list
            .build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withRemark(VALID_REMARK_BOB).withBirthday(VALID_BIRTHDAY_BOB)
            .withDateOfCreation(VALID_DATE_OF_CREATION)
            .withHistory(LocalDate.of(2024, 1, 12), "Created")
            .withHistory(LocalDate.of(2024, 10, 10), "Attended seminar")
            .withHistory(LocalDate.of(2024, 10, 11), "Met with client")
            .withPropertyList(bobPropertyList) // Using static property list
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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, HOON, IDA, AMY, BOB));
    }
}
