package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
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
            .withMajor("Computer Science").withEmail("e1234567@u.nus.edu")
            .withStudentId("A1234567P").withYear("2")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withMajor("Mechanical Engineering")
            .withEmail("e1224567@u.nus.edu").withStudentId("A0000000P")
            .withYear("1")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withStudentId("A0100000P")
            .withEmail("e1234467@u.nus.edu").withMajor("Business").withYear("3").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withStudentId("A0005000P")
            .withEmail("e1234667@u.nus.edu").withMajor("English Literature").withYear("5").withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withStudentId("A0004000P")
            .withEmail("e1234557@u.nus.edu").withMajor("China Studies").withYear("2").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withStudentId("A0200000P")
            .withEmail("e1234577@u.nus.edu").withMajor("Electrical Engineering").withYear("1").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withStudentId("A1000000P")
            .withEmail("e1134567@u.nus.edu").withMajor("Religious Studies").withYear("1").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withStudentId("A1030000P")
            .withEmail("e2134567@u.nus.edu").withMajor("Data Science").withYear("1").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withStudentId("A1000000R")
            .withEmail("e1114567@u.nus.edu").withMajor("Pharmaceutical Science").withYear("3").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withStudentId(VALID_STUDENTID_AMY)
            .withEmail(VALID_EMAIL_AMY).withMajor(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withStudentId(VALID_STUDENTID_BOB)
            .withEmail(VALID_EMAIL_BOB).withMajor(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
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
