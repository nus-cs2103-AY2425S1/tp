package seedu.academyassist.testutil;

import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_IC_ALICE;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_IC_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_IC_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_YEAR_GROUP_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_YEAR_GROUP_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.academyassist.model.AcademyAssist;
import seedu.academyassist.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withIc(VALID_IC_ALICE).withYearGroup("1").withStudentId("S10001")
            .withSubjects("English").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com")
            .withPhone("98765432").withIc("F1264567X").withYearGroup("1").withStudentId("S10002")
            .withSubjects("Math").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withAddress("wall street").withEmail("heinz@example.com")
            .withPhone("95352563").withIc("F1254567X").withYearGroup("1").withStudentId("S10003")
            .withSubjects("Science").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withAddress("10th street").withEmail("cornelia@example.com")
            .withPhone("87652533").withIc("F1234569X").withYearGroup("1").withStudentId("S10004")
            .withSubjects("English").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withAddress("michegan ave").withEmail("werner@example.com")
            .withPhone("9482224").withIc("F2238567X").withYearGroup("1").withStudentId("S10005")
            .withSubjects("English").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withAddress("little tokyo").withEmail("lydia@example.com")
            .withPhone("9482427").withIc("F1334567X").withYearGroup("1").withStudentId("S10006")
            .withSubjects("English").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withAddress("4th street").withEmail("anna@example.com")
            .withPhone("9482442").withIc("F1294667X").withYearGroup("1").withStudentId("S10007")
            .withSubjects("Science").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withAddress("little india").withEmail("stefan@example.com")
            .withPhone("8482424").withIc("F1236667X").withYearGroup("1").withStudentId("S10008")
            .withSubjects("English").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withAddress("chicago ave").withEmail("hans@example.com")
            .withPhone("8482131").withIc("F1888567X").withYearGroup("1").withStudentId("S10009")
            .withSubjects("Science").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withAddress(VALID_ADDRESS_AMY).withEmail(VALID_EMAIL_AMY)
            .withPhone(VALID_PHONE_AMY).withStudentId(VALID_STUDENT_ID_AMY)
            .withSubjects(VALID_SUBJECT_AMY).withIc(VALID_IC_AMY).withYearGroup(VALID_YEAR_GROUP_AMY)
            .build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withAddress(VALID_ADDRESS_BOB).withIc(VALID_IC_BOB).withEmail(VALID_EMAIL_BOB)
            .withPhone(VALID_PHONE_BOB).withStudentId(VALID_STUDENT_ID_BOB)
            .withSubjects(VALID_SUBJECT_BOB).withYearGroup(VALID_YEAR_GROUP_BOB).build();
    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AcademyAssist} with all the typical persons.
     */
    public static AcademyAssist getTypicalAcademyAssist() {
        AcademyAssist ab = new AcademyAssist();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        ab.setStudentCount(7);
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
