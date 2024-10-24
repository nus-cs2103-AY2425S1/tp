package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACTTYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACTTYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEHANDLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEHANDLE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTelegramHandle("@alicepauline")
            .withTags("friends")
            .withModuleName("CFG1002")
            .withContactType("Work").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withTelegramHandle("@bensonmeier")
            .withTags("owesMoney", "friends")
            .withModuleName("SC1101E")
            .withContactType("Work").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withTelegramHandle("@carlkurz")
            .withModuleName("GEA1000")
            .withContactType("Work").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withTelegramHandle("@danielmeier")
            .withTags("friends")
            .withModuleName("HSI1000")
            .withContactType("Personal").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withTelegramHandle("@ellemeyer")
            .withModuleName("CS2103T")
            .withContactType("Personal").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withTelegramHandle("@fionakunz")
            .withModuleName("CS2101")
            .withContactType("Personal").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withTelegramHandle("@georgebest")
            .withModuleName("CS2109")
            .withContactType("Personal").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withTelegramHandle("@hoonmeier")
            .withModuleName("CS2106")
            .withContactType("Work").build();
    public static final Person IDA = new PersonBuilder()
            .withName("Ida Mueller")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withTelegramHandle("@idamueller")
            .withModuleName("ST1131")
            .withContactType("Work").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder()
            .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withTelegramHandle(VALID_TELEHANDLE_AMY)
            .withTags(VALID_TAG_FRIEND)
            .withContactType(VALID_CONTACTTYPE_AMY)
            .withModuleName(VALID_MODNAME_AMY).build();
    public static final Person BOB = new PersonBuilder()
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withTelegramHandle(VALID_TELEHANDLE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withModuleName(VALID_MODNAME_BOB)
            .withContactType(VALID_CONTACTTYPE_BOB).build();

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
