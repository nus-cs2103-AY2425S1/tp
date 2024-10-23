package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_SCORE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_SCORE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REGISTER_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REGISTER_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_CLASS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_CLASS_BOB;
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
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withRegisterNumber("3").withSex("F").withStudentClass("1A")
            .withEcName("Joe Hardy").withEcNumber("26283728").withExams("Midterm", "Final")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").withRegisterNumber("4").withSex("M")
            .withStudentClass("2B").withEcName("John Boone").withEcNumber("92837201").withExams("Midterm")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withRegisterNumber("5").withSex("M")
            .withStudentClass("3C").withEcName("James Dol").withEcNumber("").withExams("Midterm").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withRegisterNumber("6").withSex("M")
            .withStudentClass("4D").withEcName("").withEcNumber("").withExams("Midterm")
            .withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withRegisterNumber("7").withSex("F")
            .withStudentClass("3E").withEcName("").withEcNumber("").withExams("Midterm").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withRegisterNumber("8").withSex("F")
            .withStudentClass("2A").withEcName("").withEcNumber("").withExams("Midterm").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withRegisterNumber("9").withSex("M")
            .withStudentClass("1A").withEcName("").withEcNumber("").withExams("Midterm").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withRegisterNumber("10").withSex("M")
            .withStudentClass("3C").withEcName("").withEcNumber("").withExams("Midterm").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withRegisterNumber("11").withSex("F")
            .withStudentClass("4D").withEcName("").withEcNumber("").withExams("Midterm").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withRegisterNumber(VALID_REGISTER_NUMBER_AMY)
            .withSex(VALID_SEX_AMY).withStudentClass(VALID_STUDENT_CLASS_AMY)
            .withEcName(VALID_ECNAME_AMY).withEcNumber(VALID_ECNUMBER_AMY)
            .withExams(VALID_EXAM_MIDTERM).withExamScore(VALID_EXAM_MIDTERM, VALID_EXAM_SCORE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withRegisterNumber(VALID_REGISTER_NUMBER_BOB)
            .withSex(VALID_SEX_BOB).withStudentClass(VALID_STUDENT_CLASS_BOB)
            .withEcName(VALID_ECNAME_BOB).withEcNumber(VALID_ECNUMBER_BOB)
            .withExams(VALID_EXAM_MIDTERM).withExamScore(VALID_EXAM_MIDTERM, VALID_EXAM_SCORE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

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
