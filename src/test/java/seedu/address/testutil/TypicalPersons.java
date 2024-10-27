package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSES_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
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

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline").withGender("female")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").withSubject("Mathematics").withClasses("7A").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier").withGender("male")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").withSubject("Chemistry", "Physics").withClasses("7A").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withGender("male").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withSubject("Mathematics").withClasses("7B").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withGender("male")
            .withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends")
            .withSubject("Physics").withClasses("8A, 8C").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withGender("female")
            .withPhone("9482224").withEmail("werner@example.com")
            .withAddress("michegan ave").withSubject("Mathematics").withClasses("7A").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withGender("female")
            .withPhone("9482427").withEmail("lydia@example.com")
            .withAddress("little tokyo").withSubject("Physics").withClasses("7B").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withGender("male").withPhone("9482442").withEmail("anna@example.com")
            .withAddress("4th street").withSubject("Mathematics", "Physics").withClasses("7A").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withGender("female")
            .withPhone("8482424").withEmail("stefan@example.com").withAddress("little india")
            .withSubject("Chemistry").withClasses("7A").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withGender("female")
            .withPhone("8482131").withEmail("hans@example.com")
            .withAddress("chicago ave").withSubject("Mathematics").withClasses("7A").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withGender("female")
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_FRIEND).withSubject(VALID_SUBJECT_AMY).withClasses(VALID_CLASSES_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withGender(VALID_GENDER_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withSubject(VALID_SUBJECT_BOB).withClasses(VALID_CLASSES_BOB)
            .build();

    public static final Student MICHAEL = new StudentBuilder().withName("Michael Tan")
            .withGender("male")
            .withPhone("98765432")
            .withEmail("michael@example.com")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withSubjects("Physics")
            .withClasses("7A", "7B")
            .withTags("hardworking", "athlete")
            .withDaysAttended(10).build();

    public static final Student CHRIS = new StudentBuilder().withName("Chris Lim")
            .withGender("male")
            .withPhone("98192727")
            .withEmail("chris@example.com")
            .withAddress("311, Lorong Ave 2, #02-25")
            .withSubjects("Math")
            .withClasses("7C", "7B")
            .withTags("hardworking", "athlete").build();


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
