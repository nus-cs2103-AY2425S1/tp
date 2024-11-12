package tahub.contacts.testutil;

import static tahub.contacts.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_MATRICULATION_NUMBER_AMY;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_MATRICULATION_NUMBER_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tahub.contacts.model.AddressBook;
import tahub.contacts.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder()
            .withMatriculationNumber("A1234567X")
            .withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .build();

    public static final Person BENSON = new PersonBuilder()
            .withMatriculationNumber("A2345678Y")
            .withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withTags("owesMoney", "friends")
            .build();

    public static final Person CARL = new PersonBuilder()
            .withMatriculationNumber("A3456789X")
            .withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .build();

    public static final Person DANIEL = new PersonBuilder()
            .withMatriculationNumber("A4567890Y")
            .withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withTags("friends")
            .build();

    public static final Person ELLE = new PersonBuilder()
            .withMatriculationNumber("A5678901X")
            .withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .build();

    public static final Person FIONA = new PersonBuilder()
            .withMatriculationNumber("A6789012Y")
            .withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .build();

    public static final Person GEORGE = new PersonBuilder()
            .withMatriculationNumber("A7890123X")
            .withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder()
            .withMatriculationNumber("A8901234Y")
            .withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .build();

    public static final Person IDA = new PersonBuilder()
            .withMatriculationNumber("A9012345X")
            .withName("Ida Mueller")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withMatriculationNumber(VALID_MATRICULATION_NUMBER_AMY)
            .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withMatriculationNumber(VALID_MATRICULATION_NUMBER_BOB)
            .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
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
