package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Student ALICE = new PersonBuilder().withName("Alice Pauline")
        .withEmail("alice@example.com").withTags("friends").build();
    public static final Student BENSON = new PersonBuilder().withName("Benson Meier")
        .withEmail("johnd@example.com").withTags("owesMoney", "friends").build();
    public static final Student CARL = new PersonBuilder().withName("Carl Kurz").withEmail("heinz@example.com").build();
    public static final Student DANIEL = new PersonBuilder().withName("Daniel Meier")
        .withEmail("cornelia@example.com").withTags("friends").build();
    public static final Student ELLE = new PersonBuilder().withName("Elle Meyer")
        .withEmail("werner@example.com").build();
    public static final Student FIONA = new PersonBuilder().withName("Fiona Kunz")
        .withEmail("lydia@example.com").build();
    public static final Student GEORGE = new PersonBuilder().withName("George Best")
        .withEmail("anna@example.com").build();
    public static final Student JASON = new PersonBuilder().withName("Jason Derulo")
            .withEmail("jason@example.com").withGroup("Group 1").build();

    // Manually added
    public static final Student HOON = new PersonBuilder().withName("Hoon Meier")
        .withEmail("stefan@example.com").build();
    public static final Student IDA = new PersonBuilder().withName("Ida Mueller")
        .withEmail("hans@example.com").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new PersonBuilder().withName(VALID_NAME_AMY)
        .withEmail(VALID_EMAIL_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Student BOB = new PersonBuilder().withName(VALID_NAME_BOB)
        .withEmail(VALID_EMAIL_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
        .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Student student : getTypicalPersons()) {
            ab.addStudent(student);
        }
        return ab;
    }

    public static List<Student> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
