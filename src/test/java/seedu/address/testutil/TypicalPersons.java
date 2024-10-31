package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder()
            .withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withNric("S1234567A")
            .withRemark("she likes puppies")
            .withTags("friends")
            .withAppointment("02-11-2024 09:19")
            .build();
    public static final Person BENSON = new PersonBuilder()
            .withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withNric("S1234567B")
            .withRemark("He can't handle his alcohol")
            .withTags("owesMoney", "friends")
            .withAppointment("29-10-2024 20:24")
            .build();
    public static final Person CARL = new PersonBuilder()
            .withName("Carl Kurz")
            .withPhone("95352563")
            .withNric("S1234567C")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withAppointment("01-11-2024 20:45")
            .build();
    public static final Person DANIEL = new PersonBuilder()
            .withName("Daniel Meier")
            .withPhone("87652533")
            .withNric("S1234567D")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withTags("friends")
            .withAppointment("08-11-2024 10:10")
            .build();
    public static final Person ELLE = new PersonBuilder()
            .withName("Elle Meyer")
            .withPhone("9482224")
            .withNric("S1234567E")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withTags("Diabetic", "G6PD")
            .withAppointment("10-11-2024 05:31")
            .build();
    public static final Person FIONA = new PersonBuilder()
            .withName("Fiona Kunz")
            .withPhone("9482427")
            .withNric("S1234567F")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withTags("Diabetic", "G6PD")
            .withAppointment("08-11-2024 13:22")
            .build();
    public static final Person GEORGE = new PersonBuilder()
            .withName("George Best")
            .withPhone("9482442")
            .withNric("S1234567G")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withAppointment("07-11-2024 22:33")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder()
            .withName("Hoon Meier")
            .withPhone("8482424")
            .withNric("S1234567H")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withAppointment("12-11-2024 06:22")
            .build();
    public static final Person IDA = new PersonBuilder()
            .withName("Ida Mueller")
            .withPhone("8482131")
            .withNric("S1234567I")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withAppointment("22-10-2024 01:14")
            .build();
    public static final Person PERSONNONRICPRESENT = new PersonBuilder()
            .withName("NO NRIC FOUND")
            .withPhone("00000000")
            .withNric("A0000000A")
            .withEmail("nonric@nonric.com")
            .withAddress("no nric found")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder()
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withNric(VALID_NRIC_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_FRIEND)
            .withAppointment("23-10-2024 22:50")
            .build();
    public static final Person BOB = new PersonBuilder()
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withNric(VALID_NRIC_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withAppointment("16-11-2024 20:44")
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

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
