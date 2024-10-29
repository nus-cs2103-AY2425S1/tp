package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_CHRIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSES_CHRIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSES_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CHRIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CHRIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_CHRIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CHRIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Teacher;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withGender(VALID_GENDER_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withSubject(VALID_SUBJECT_BOB).withClasses(VALID_CLASSES_BOB)
            .build();

    public static final Teacher TEACHER_ALICE = new TeacherBuilder().withName("Alice Pauline").withGender("female")
        .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
        .withPhone("94351253")
        .withTags("friends").withSubject("Mathematics").withClasses("7A").build();
    public static final Teacher TEACHER_DANIEL = new TeacherBuilder().withName("Daniel Meier").withGender("male")
        .withPhone("87652533")
        .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends")
        .withSubject("Physics").withClasses("8A, 8C").build();
    public static final Teacher TEACHER_GEORGE = new TeacherBuilder().withName("George Best")
        .withGender("male").withPhone("9482442").withEmail("anna@example.com")
        .withAddress("4th street").withSubject("Mathematics", "Physics").withClasses("7A").build();

    public static final Student STUDENT_BENSON = new StudentBuilder().withName("Benson Meier").withGender("male")
        .withAddress("311, Clementi Ave 2, #02-25")
        .withEmail("johnd@example.com").withPhone("98765432")
        .withTags("owesMoney", "friends").withSubjects("Chemistry", "Physics").withClasses("7A").build();
    public static final Student STUDENT_CARL = new StudentBuilder().withName("Carl Kurz").withGender("male")
        .withPhone("95352563")
        .withEmail("heinz@example.com").withAddress("wall street")
        .withSubjects("Mathematics").withClasses("7B").build();
    public static final Student STUDENT_ELLE = new StudentBuilder().withName("Elle Meyer").withGender("female")
        .withPhone("9482224").withEmail("werner@example.com")
        .withAddress("michegan ave").withSubjects("Mathematics").withClasses("7A").build();
    public static final Student STUDENT_FIONA = new StudentBuilder().withName("Fiona Kunz").withGender("female")
        .withPhone("9482427").withEmail("lydia@example.com")
        .withAddress("little tokyo").withSubjects("Physics").withClasses("7B").build();

    public static final Student STUDENT_ALICE = new StudentBuilder().withName("Alice Pauline").withGender("female")
        .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
        .withPhone("94351253")
        .withTags("friends").withSubjects("Mathematics").withClasses("7A").build();
    public static final Student STUDENT_CHRIS = new StudentBuilder().withName(VALID_NAME_CHRIS)
        .withGender(VALID_GENDER_BOB)
        .withPhone(VALID_PHONE_CHRIS).withEmail(VALID_EMAIL_CHRIS).withAddress(VALID_ADDRESS_CHRIS)
        .withSubjects(VALID_SUBJECT_CHRIS).withClasses(VALID_CLASSES_CHRIS).build();
    public static final Student STUDENT_MICHAEL = new StudentBuilder().withName(VALID_NAME_MICHAEL)
        .withGender(VALID_GENDER_BOB).withAddress(VALID_ADDRESS_MICHAEL).withPhone(VALID_PHONE_MICHAEL)
        .withEmail(VALID_EMAIL_MICHAEL).withSubjects(VALID_SUBJECT_MICHAEL).withClasses(VALID_CLASSES_MICHAEL)
        .withDaysAttended(VALID_ATTENDANCE_MICHAEL).build();

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
        return new ArrayList<>(Arrays.asList(TEACHER_ALICE, STUDENT_BENSON, STUDENT_CARL, TEACHER_DANIEL, STUDENT_ELLE,
                STUDENT_FIONA));
    }
}
