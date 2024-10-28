package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Teacher;

/**
 * A utility class containing a list of {@code Teacher} objects to be used in tests.
 */
public class TypicalTeachers {

    public static final Teacher ALICE = new TeacherBuilder().withName("Alice Pauline")
        .withGender("female").withAddress("123, Jurong West Ave 6, #08-111")
        .withEmail("alice@example.com").withPhone("94351253")
        .withTags("friends").build();
    public static final Teacher BENSON = new TeacherBuilder().withName("Benson Meier")
        .withGender("male").withAddress("311, Clementi Ave 2, #02-25")
        .withEmail("johnd@example.com").withPhone("98765432")
        .withTags("owesMoney", "friends").build();
    public static final Teacher CARL = new TeacherBuilder().withName("Carl Kurz")
        .withGender("male").withPhone("95352563")
        .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Teacher DANIEL = new TeacherBuilder().withName("Daniel Meier")
        .withGender("male").withPhone("87652533")
        .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Teacher ELLE = new TeacherBuilder().withName("Elle Meyer")
        .withGender("female").withPhone("9482224")
        .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Teacher FIONA = new TeacherBuilder().withName("Fiona Kunz")
        .withGender("female").withPhone("9482427")
        .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Teacher GEORGE = new TeacherBuilder().withName("George Best")
        .withGender("male").withPhone("9482442")
        .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Teacher HOON = new TeacherBuilder().withName("Hoon Meier")
        .withGender("male").withPhone("8482424")
        .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Teacher IDA = new TeacherBuilder().withName("Ida Mueller")
        .withGender("female").withPhone("8482131")
        .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Teacher's details found in {@code CommandTestUtil}
    public static final Teacher TEACHER_BOB = new TeacherBuilder().withName(VALID_NAME_BOB).withGender(VALID_GENDER_BOB)
        .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
        .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTeachers() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical teachers.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Teacher teacher : getTypicalTeachers()) {
            ab.addTeacher(teacher);
        }
        return ab;
    }

    public static List<Teacher> getTypicalTeachers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
