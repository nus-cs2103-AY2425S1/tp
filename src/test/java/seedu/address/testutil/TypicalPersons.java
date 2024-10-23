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
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withPhone("94351253").withEmergencyContact("94351253").withNote("Needs Jesus rn")
            .withSubjects("PHYSICS").withLevel("S3 NA").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier").withNote("Needs Jesus rn")
            .withAddress("311, Clementi Ave 2, #02-25").withEmergencyContact("94351253")
            .withPhone("98765432")
            .withSubjects("CHEMISTRY", "MATH").withLevel("S2 NT").withTaskList(
                    MARKING_TASK, GRADING_TASK
            ).withLessonTimes("WED-17:00-19:00", "SUN-11:00-13:00").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withNote("Needs Jesus rn").withAddress("wall street").withEmergencyContact("94351253").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmergencyContact("94351253").withAddress("10th street")
            .withSubjects("MATH").withNote("Needs Jesus rn").withLevel("S1 NT").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withNote("Needs Jesus rn").withEmergencyContact("94351253").withAddress("michegan ave")
            .withLevel("S2 NA").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withNote("Needs Jesus rn").withEmergencyContact("94351253").withAddress("little tokyo")
            .withLevel("S2 NT").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withNote("y am i in cs").withEmergencyContact("94351253")
            .withAddress("4th street").withLevel("S4 NA").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmergencyContact("94351253").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmergencyContact("94351253").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmergencyContact(VALID_EMERGENCY_CONTACT_AMY)
            .withAddress(VALID_ADDRESS_AMY).withSubjects(VALID_SUBJECT_MATH).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withEmergencyContact(VALID_EMERGENCY_CONTACT_BOB)
            .withAddress(VALID_ADDRESS_BOB).withSubjects(VALID_SUBJECT_MATH, VALID_SUBJECT_ENGLISH)
            .withLevel(VALID_LEVEL_S1_EXPRESS)
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

    /**
     * Returns an {code AddressBook} with all the typical persons as new objects each time
     */
    public static AddressBook getUniqueTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(new PersonBuilder(person).build());
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
