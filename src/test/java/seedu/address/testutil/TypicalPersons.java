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
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORKEXP_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORKEXP_BOB;

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
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withWorkExp("Intern,Google,2024")
            .withTags("friends").withUniversity("NUS").withMajor("Computer Science")
            .withInterests("Swimming", "Reading").withBirthday("11-12-2000").build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com")
            .withPhone("98765432").withWorkExp("Intern,Google,2024")
            .withTags("owesMoney", "friends").withUniversity("NTU").withMajor("Computer Science")
            .withInterests("Football", "Reading").withBirthday("01-11-2002").build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withPhone("95352563").withEmail("heinz@example.com").withAddress("wall street")
            .withWorkExp("").withUniversity("NUS").withMajor("Computer Science")
            .withInterests("Cycling").withBirthday("20-04-2005").build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533").withEmail("cornelia@example.com").withAddress("10th street")
            .withWorkExp("").withTags("friends").withUniversity("NTU")
            .withMajor("Business").withInterests("Traveling").withBirthday("11-05-2003").build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("9482224").withEmail("werner@example.com").withAddress("michegan ave")
            .withWorkExp("").withUniversity("NUS").withMajor("Engineering")
            .withInterests("Photography").withBirthday("12-12-2003").build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("9482427").withEmail("lydia@example.com").withAddress("little tokyo")
            .withWorkExp("").withUniversity("NUS").withMajor("Architecture")
            .withInterests("Painting", "Yoga").withBirthday("12-12-2003").build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("9482442").withEmail("anna@example.com").withAddress("4th street")
            .withWorkExp("").withUniversity("NTU").withMajor("Economics")
            .withInterests("Running", "Chess").withBirthday("23-09-2004").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withPhone("8482424").withEmail("stefan@example.com").withAddress("little india")
            .withInterests("Singing").withBirthday("22-10-1999").build();

    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withPhone("8482131").withEmail("hans@example.com").withAddress("chicago ave")
            .withInterests("Gardening").withBirthday("24-09-2003").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withWorkExp(VALID_WORKEXP_AMY).withTags(VALID_TAG_FRIEND).withUniversity("NUS")
            .withMajor("Computer Science").withInterests("Swimming", "Reading").withBirthday("01-02-2005")
            .build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withWorkExp(VALID_WORKEXP_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withUniversity("NTU").withMajor("Business").withInterests("Swimming").withBirthday("12-12-2000").build();

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
