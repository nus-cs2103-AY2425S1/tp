package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LAST_SEEN_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LAST_SEEN_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANISATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANISATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_PRETTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_HANDSOME;
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

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253").withOrganisation("National University of Singapore")
            .withLastSeen("02-09-2024").withTags("friends")
            .withPriority("low").withRemark("likes apple").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withRemark("likes apple")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withOrganisation("National University of Singapore")
            .withLastSeen("17-11-2024")
            .withTags("owesMoney", "friends")
            .withPriority("medium")
            .build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withOrganisation("National University of Singapore")
            .withLastSeen("05-09-2022").withRemark("handsome").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withOrganisation("National University of Singapore")
            .withLastSeen("04-04-2023").withTags("friends").withRemark("handsome").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withOrganisation("National University of Singapore")
            .withLastSeen("06-06-2024").withRemark("plays basketball").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withOrganisation("National University of Singapore")
            .withLastSeen("03-07-2024").withRemark("pretty").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withOrganisation("National University of Singapore")
            .withLastSeen("01-09-2024").withRemark("pretty").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withOrganisation("National University of Singapore").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withOrganisation("National University of Singapore").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withOrganisation(VALID_ORGANISATION_AMY).withRemark(VALID_REMARK_PRETTY)
            .withLastSeen(VALID_LAST_SEEN_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withOrganisation(VALID_ORGANISATION_BOB).withRemark(VALID_REMARK_HANDSOME)
            .withLastSeen(VALID_LAST_SEEN_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

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
