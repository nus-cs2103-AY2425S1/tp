package bizbook.testutil;

import static bizbook.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static bizbook.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static bizbook.logic.commands.CommandTestUtil.VALID_ADDRESS_CHARLIE;
import static bizbook.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static bizbook.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static bizbook.logic.commands.CommandTestUtil.VALID_EMAIL_CHARLIE;
import static bizbook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static bizbook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static bizbook.logic.commands.CommandTestUtil.VALID_NAME_CHARLIE;
import static bizbook.logic.commands.CommandTestUtil.VALID_NOTE_LIKES_CATS;
import static bizbook.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static bizbook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static bizbook.logic.commands.CommandTestUtil.VALID_PHONE_CHARLIE;
import static bizbook.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static bizbook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bizbook.model.AddressBook;
import bizbook.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .withNotes("High profile client", "Likes dumplings").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends")
            .withNotes("High profile client", "Likes dumplings").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withNotes("High profile client", "Likes dumplings").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends")
            .withNotes("High profile client", "Likes dumplings").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94822245")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withNotes("High profile client", "Likes dumplings").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94824275")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withNotes("High profile client", "Likes dumplings").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("94824423")
            .withEmail("anna@example.com").withAddress("4th street")
            .withNotes("High profile client", "Likes dumplings").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84824245")
            .withEmail("stefan@example.com").withAddress("little india")
            .withNotes("High profile client", "Likes dumplings").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84821314")
            .withEmail("hans@example.com").withAddress("chicago ave")
            .withNotes("High profile client", "Likes dumplings").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();
    public static final Person CHARLIE = new PersonBuilder().withName(VALID_NAME_CHARLIE).withPhone(VALID_PHONE_CHARLIE)
            .withEmail(VALID_EMAIL_CHARLIE).withAddress(VALID_ADDRESS_CHARLIE).withTags(VALID_TAG_FRIEND)
            .withNotes(VALID_NOTE_LIKES_CATS).build();

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

    /**
     * Returns an {@code AddressBook} with all the typical persons and a pinned person.
     */
    public static AddressBook getTypicalAddressBookWithPinned() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        ab.addPinnedPerson(ALICE);
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
