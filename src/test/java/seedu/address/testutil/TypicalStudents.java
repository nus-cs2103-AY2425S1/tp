package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDUCATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDUCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withEducation("Primary")
            .withGrade("0")
            .withParentName("Test parent")
            .withParentPhone("91234567")
            .withParentEmail("testparent@example.com")
            .withTags("friends").build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withEducation("Primary")
            .withGrade("0")
            .withParentName("Test parent")
            .withParentPhone("91234567")
            .withParentEmail("testparent@example.com")
            .withTags("owesMoney", "friends").build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withEducation("Primary")
            .withGrade("0")
            .withParentName("Test parent")
            .withParentPhone("91234567")
            .withParentEmail("testparent@example.com").build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withEducation("Primary")
            .withGrade("0")
            .withParentName("Test parent")
            .withParentPhone("91234567")
            .withParentEmail("testparent@example.com")
            .withTags("friends").build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withEducation("Primary")
            .withGrade("0")
            .withParentName("Test parent")
            .withParentPhone("91234567")
            .withParentEmail("testparent@example.com").build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withEducation("Primary")
            .withGrade("0")
            .withParentName("Test parent")
            .withParentPhone("91234567")
            .withParentEmail("testparent@example.com").build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withEducation("Primary")
            .withGrade("0")
            .withParentName("Test parent")
            .withParentPhone("91234567")
            .withParentEmail("testparent@example.com").build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india")
            .withEducation("Primary")
            .withGrade("0")
            .withParentName("Test parent")
            .withParentPhone("91234567")
            .withParentEmail("testparent@example.com").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave")
            .withEducation("Primary")
            .withGrade("0")
            .withParentName("Test parent")
            .withParentPhone("91234567")
            .withParentEmail("testparent@example.com").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withEducation(VALID_EDUCATION_AMY).withGrade(VALID_GRADE_AMY)
            .withParentName(VALID_PARENT_NAME_AMY).withParentPhone(VALID_PARENT_PHONE_AMY)
            .withParentEmail(VALID_PARENT_EMAIL_AMY).build();
    public static final Student BOB = new StudentBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withEducation(VALID_EDUCATION_BOB).withGrade(VALID_GRADE_BOB).withParentName(VALID_PARENT_NAME_BOB)
            .withParentPhone(VALID_PARENT_PHONE_BOB).withParentEmail(VALID_PARENT_EMAIL_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Student person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Student> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
