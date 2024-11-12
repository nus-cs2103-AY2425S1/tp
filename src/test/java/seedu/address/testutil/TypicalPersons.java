package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_TUTOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EduContacts;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder()
            .withStudentId("22223333")
            .withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withCourse("Math")
            .withRole("Student")
            .addGradedModule("MA1100", "A")
            .addUngradedModule("MA2202")
            .build();
    public static final Person BENSON = new PersonBuilder()
            .withStudentId("19191919")
            .withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withCourse("Medicine")
            .withRole("Student")
            .addUngradedModule("GEC1044")
            .build();
    public static final Person CARL = new PersonBuilder()
            .withStudentId("21212121")
            .withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withCourse("nursing")
            .withRole("Tutor")
            .build();
    public static final Person DANIEL = new PersonBuilder()
            .withStudentId("53289012")
            .withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withCourse("Dentistry")
            .withRole("Student")
            .addUngradedModule("DI5100")
            .addUngradedModule("DI5200")
            .build();
    public static final Person ELLE = new PersonBuilder()
            .withStudentId("25252525")
            .withName("Elle Meyer")
            .withPhone("94822244")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withCourse("biomedical engineering")
            .withRole("Student")
            .addGradedModule("BN1111", "D")
            .addGradedModule("BN2102", "C+")
            .build();
    public static final Person FIONA = new PersonBuilder()
            .withStudentId("98989898")
            .withName("Fiona Kunz")
            .withPhone("94824272")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withCourse("Data Science")
            .withRole("Tutor")
            .build();
    public static final Person GEORGE = new PersonBuilder()
            .withStudentId("28184003")
            .withName("George Best")
            .withPhone("94824422")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withCourse("Dentistry")
            .withRole("Student")
            .addUngradedModule("DI5100")
            .addUngradedModule("DI5200")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder()
            .withStudentId("15159888")
            .withName("Hoon Meier")
            .withPhone("84842424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withCourse("Mechanical engineering")
            .withRole("Tutor")
            .build();
    public static final Person IDA = new PersonBuilder()
            .withStudentId("48883999")
            .withName("Ida Mueller")
            .withPhone("84842131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withCourse("Architecture")
            .withRole("Student")
            .addGradedModule("AR1101", "A+")
            .addUngradedModule("AR1102")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder()
            .withStudentId(VALID_STUDENTID_AMY)
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withCourse(VALID_COURSE_AMY)
            .withRole(VALID_ROLE_STUDENT)
            .addGradedModule(VALID_MODULE_AMY, VALID_GRADE_AMY)
            .build();
    public static final Person BOB = new PersonBuilder()
            .withStudentId(VALID_STUDENTID_BOB)
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withCourse(VALID_COURSE_BOB)
            .withRole(VALID_ROLE_TUTOR)
            .addGradedModule(VALID_MODULE_BOB, VALID_GRADE_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code EduContacts} with all the typical persons.
     */
    public static EduContacts getTypicalEduContacts() {
        EduContacts ab = new EduContacts();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
