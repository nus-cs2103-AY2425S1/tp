package tutorease.address.testutil;

import static tutorease.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_ROLE_ANY;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tutorease.address.model.TutorEase;
import tutorease.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {
    public static final Person ALICE = new StudentBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withRole("Student")
            .withTags("friends").build();
    public static final Person BENSON = new StudentBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").withRole("Student")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new StudentBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withRole("Student").build();
    public static final Person DANIEL = new StudentBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street").withRole("Student").withTags("friends").build();
    public static final Person ELLE = new StudentBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withRole("Student").build();
    public static final Person FIONA = new StudentBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withRole("Student").build();
    public static final Person GEORGE = new StudentBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withRole("Student").build();

    // Manually added
    public static final Person HOON = new StudentBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withRole("Student").build();
    public static final Person IDA = new StudentBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withRole("Student").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new StudentBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withRole(VALID_ROLE_ANY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new StudentBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withRole(VALID_ROLE_ANY).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code TutorEase} with all the typical persons.
     */
    public static TutorEase getTypicalTutorEase() {
        TutorEase ab = new TutorEase();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
