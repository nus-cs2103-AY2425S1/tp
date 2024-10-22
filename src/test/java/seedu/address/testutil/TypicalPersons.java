package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {
    private static final List<Grade> EMPTY_SAMPLE_GRADES = List.of();
    private static final List<Grade> SAMPLE_GRADES_1 = List.of(
            new Grade("Math Test", 85, 20),
            new Grade("Science Exam", 90, 30)
    );
    private static final List<Grade> SAMPLE_GRADES_2 = List.of(
            new Grade("English Test", 78, 25),
            new Grade("History Exam", 92, 25)
    );
    private static final Map<LocalDateTime, Attendance> EMPTY_SAMPLE_ATTENDANCES = Map.ofEntries();
    private static final Map<LocalDateTime, Attendance> SAMPLE_ATTENDANCES_1 = Map.ofEntries(
            Map.entry(LocalDateTime.of(2024, 1, 1, 12, 0), new Attendance(true)),
            Map.entry(LocalDateTime.of(2024, 1, 8, 12, 0), new Attendance(false))
    );
    private static final Map<LocalDateTime, Attendance> SAMPLE_ATTENDANCES_2 = Map.ofEntries(
            Map.entry(LocalDateTime.of(2024, 2, 14, 10, 0), new Attendance(false)),
            Map.entry(LocalDateTime.of(2024, 2, 21, 10, 0), new Attendance(true))
    );
    private static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER
    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .withGrades(EMPTY_SAMPLE_GRADES)
            .withAttendances(EMPTY_SAMPLE_ATTENDANCES).build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends")
            .withGrades(EMPTY_SAMPLE_GRADES)
            .withAttendances(EMPTY_SAMPLE_ATTENDANCES).build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withGrades(EMPTY_SAMPLE_GRADES)
            .withAttendances(SAMPLE_ATTENDANCES_1).build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends")
            .withGrades(SAMPLE_GRADES_1)
            .withAttendances(SAMPLE_ATTENDANCES_1).build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withGrades(SAMPLE_GRADES_1)
            .withAttendances(SAMPLE_ATTENDANCES_1).build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withGrades(SAMPLE_GRADES_1)
            .withAttendances(SAMPLE_ATTENDANCES_2).build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street")
            .withGrades(SAMPLE_GRADES_2)
            .withAttendances(SAMPLE_ATTENDANCES_2).build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india")
            .withGrades(SAMPLE_GRADES_2)
            .withAttendances(EMPTY_SAMPLE_ATTENDANCES).build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave")
            .withGrades(SAMPLE_GRADES_2)
            .withAttendances(SAMPLE_ATTENDANCES_2).build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withGrades(EMPTY_SAMPLE_GRADES)
            .withAttendances(EMPTY_SAMPLE_ATTENDANCES).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withGrades(EMPTY_SAMPLE_GRADES)
            .withAttendances(EMPTY_SAMPLE_ATTENDANCES).build();

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
