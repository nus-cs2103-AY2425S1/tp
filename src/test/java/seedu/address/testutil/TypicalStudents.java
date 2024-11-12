package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_S1_EXPRESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.testutil.TypicalTasks.GRADING_TASK;
import static seedu.address.testutil.TypicalTasks.MARKING_TASK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withPhone("94351253").withEmergencyContact("94351253").withNote("Needs Jesus rn")
            .withSubjects("PHYSICS").withLevel("S3 NA").build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier").withNote("Needs Jesus rn")
            .withAddress("311, Clementi Ave 2, #02-25").withEmergencyContact("94351253")
            .withPhone("98765432")
            .withSubjects("CHEMISTRY", "MATH").withLevel("S2 NT").withTaskList(
                    MARKING_TASK, GRADING_TASK
            ).withLessonTimes("WED-17:00-19:00", "SUN-11:00-13:00").build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz").withPhone("95352563")
            .withNote("Needs Jesus rn").withAddress("wall street").withEmergencyContact("94351253").build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmergencyContact("94351253").withAddress("10th street")
            .withSubjects("MATH").withNote("Needs Jesus rn").withLevel("S1 NT").build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer").withPhone("9482224")
            .withNote("Needs Jesus rn").withEmergencyContact("94351253").withAddress("michegan ave")
            .withLevel("S2 NA").build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withNote("Needs Jesus rn").withEmergencyContact("94351253").withAddress("little tokyo")
            .withLevel("S2 NT").build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best").withPhone("9482442")
            .withNote("y am i in cs").withEmergencyContact("94351253")
            .withAddress("4th street").withLevel("S4 NA").build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmergencyContact("94351253").withAddress("little india").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmergencyContact("94351253").withAddress("chicago ave").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmergencyContact(VALID_EMERGENCY_CONTACT_AMY)
            .withAddress(VALID_ADDRESS_AMY).withSubjects(VALID_SUBJECT_MATH).build();
    public static final Student BOB = new StudentBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withEmergencyContact(VALID_EMERGENCY_CONTACT_BOB)
            .withAddress(VALID_ADDRESS_BOB).withSubjects(VALID_SUBJECT_MATH, VALID_SUBJECT_ENGLISH)
            .withLevel(VALID_LEVEL_S1_EXPRESS)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical students.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }

    /**
     * Returns an {code AddressBook} with all the typical students as new objects each time
     */
    public static AddressBook getUniqueTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Student student : getTypicalStudents()) {
            ab.addStudent(new StudentBuilder(student).build());
        }
        return ab;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
