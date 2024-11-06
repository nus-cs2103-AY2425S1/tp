package spleetwaise.address.testutil;

import static spleetwaise.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static spleetwaise.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static spleetwaise.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static spleetwaise.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static spleetwaise.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static spleetwaise.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static spleetwaise.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static spleetwaise.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static spleetwaise.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static spleetwaise.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static spleetwaise.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static spleetwaise.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static spleetwaise.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static spleetwaise.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import spleetwaise.address.model.AddressBook;
import spleetwaise.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withId("5db6c966-11cf-4f7a-82bb-535d6e6a1e4a")
            .withName("Alice Pauline").withPhone("94351253").withEmail("alice@example.com")
            .withAddress("123, Jurong West Ave 6, #08-111").withRemark("alice is poor").withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withId("5db6c966-11cf-4f7a-82bb-535d6e6a1e4b")
            .withName("Benson Meier").withPhone("98765432").withEmail("johnd@example.com")
            .withAddress("311, Clementi Ave 2, #02-25").withRemark("benson is rich").withTags("owesMoney", "friends")
            .build();
    public static final Person CARL = new PersonBuilder().withId("5db6c966-11cf-4f7a-82bb-535d6e6a1e4c")
            .withName("Carl Kurz").withPhone("95352563").withEmail("heinz@example.com").withAddress("wall street")
            .build();
    public static final Person DANIEL = new PersonBuilder().withId("5db6c966-11cf-4f7a-82bb-535d6e6a1e4d")
            .withName("Daniel Meier").withPhone("87652533").withEmail("cornelia@example.com").withAddress("10th street")
            .withRemark("DANIEL IS IN DEBT").withTags("friends")
            .build();
    public static final Person ELLE = new PersonBuilder().withId("5db6c966-11cf-4f7a-82bb-535d6e6a1e4e")
            .withName("Elle Meyer").withPhone("9482224").withEmail("werner@example.com").withAddress("michegan ave")
            .build();
    public static final Person FIONA = new PersonBuilder().withId("5db6c966-11cf-4f7a-82bb-535d6e6a1e4f")
            .withName("Fiona Kunz").withPhone("9482427").withEmail("lydia@example.com").withAddress("little tokyo")
            .build();
    public static final Person GEORGE = new PersonBuilder().withId("6b76c348-7794-4ae9-be28-74e4820bb224")
            .withName("George Best").withPhone("9482442").withEmail("anna@example.com").withAddress("4th street")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withId("ee56885b-eca4-4c35-8587-d5d10161bade")
            .withName("Hoon Meier").withPhone("8482424").withEmail("stefan@example.com").withAddress("little india")
            .build();
    public static final Person IDA = new PersonBuilder().withId("ae56885b-eca4-4c35-8587-d5d10161bade")
            .withName("Ida Mueller").withPhone("8482131").withEmail("hans@example.com").withAddress("chicago ave")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withId(VALID_ID_AMY).withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withRemark(VALID_REMARK_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB =
            new PersonBuilder().withId(VALID_ID_BOB).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                    .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withRemark(VALID_REMARK_BOB)
                    .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                    .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

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
