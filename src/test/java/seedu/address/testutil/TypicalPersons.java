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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {
    public static final Student ALICE = new StudentBuilder()
            .withName("Alice Pauline")
            .withSex("F")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withRole("student")
            .withTags("friends")
            .withAttendanceCount("3").build();

    public static final Student BENSON = new StudentBuilder()
            .withName("Benson Meier")
            .withSex("M")
            .withRole("Student")
            .withAttendanceCount("3")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Student CARL = new StudentBuilder()
            .withName("Carl Kurz")
            .withSex("M")
            .withPhone("95352563")
            .withRole("Student")
            .withAttendanceCount("3")
            .withEmail("heinz@example.com")
            .withAddress("wall street").build();
    public static final Person DANIEL = new PersonBuilder()
            .withName("Daniel Meier")
            .withSex("M")
            .withPhone("87652533")
            .withRole("Parent")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withTags("friends").build();
    public static final Student ELLE = new StudentBuilder()
            .withName("Elle Meyer")
            .withSex("F")
            .withPhone("94822244")
            .withRole("student")
            .withAttendanceCount("3")
            .withEmail("cornelia@example.com")
            .withAddress("michegan ave").build();
    public static final Person CARLMEIER = new PersonBuilder()
            .withName("Carl Meier")
            .withSex("M")
            .withPhone("87645253")
            .withRole("Parent")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withTags("omg").build();
    public static final Student FIONA = new StudentBuilder()
            .withName("Fiona Kunz")
            .withSex("F")
            .withPhone("94824272")
            .withRole("sTuDenT")
            .withAttendanceCount("3")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder()
            .withName("George Best")
            .withSex("M")
            .withPhone("94822442")
            .withRole("parent")
            .withEmail("anna@example.com")
            .withAddress("4th street").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withSex("M").withPhone("84824242")
            .withRole("parent")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller").withSex("F").withPhone("84821311")
            .withRole("student").withAttendanceCount("5")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY).withSex("F")
            .withPhone(VALID_PHONE_AMY).withRole("student").withAttendanceCount("5")
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withSex("M")
            .withPhone(VALID_PHONE_BOB).withRole("parent")
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, CARLMEIER, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static AddressBook getParentOnlyAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getParents()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getParents() {
        return new ArrayList<>(Arrays.asList(CARLMEIER, GEORGE, BOB));
    }
}
