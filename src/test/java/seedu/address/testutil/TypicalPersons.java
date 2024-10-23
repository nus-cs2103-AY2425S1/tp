package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SENIOR;

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
            .withCourse("CS2101").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .withGrades(EMPTY_SAMPLE_GRADES)
            .withAttendances(EMPTY_SAMPLE_ATTENDANCES).build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withCourse("CS2103/T")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends")
            .withGrades(EMPTY_SAMPLE_GRADES)
            .withAttendances(EMPTY_SAMPLE_ATTENDANCES).build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withCourse("CS2102")
            .withGrades(EMPTY_SAMPLE_GRADES)
            .withAttendances(SAMPLE_ATTENDANCES_1).build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withCourse("CS2105").withTags("friends")
            .withGrades(SAMPLE_GRADES_1)
            .withAttendances(SAMPLE_ATTENDANCES_1).build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withCourse("CS3210")
            .withGrades(SAMPLE_GRADES_1)
            .withAttendances(SAMPLE_ATTENDANCES_1).build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withCourse("ES2660")
            .withGrades(SAMPLE_GRADES_1)
            .withAttendances(SAMPLE_ATTENDANCES_2).build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withCourse("IS1108")
            .withGrades(SAMPLE_GRADES_2)
            .withAttendances(SAMPLE_ATTENDANCES_2).build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withCourse("EG1311")
            .withGrades(SAMPLE_GRADES_2)
            .withAttendances(EMPTY_SAMPLE_ATTENDANCES).build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withCourse("CS2109S")
            .withGrades(SAMPLE_GRADES_2)
            .withAttendances(SAMPLE_ATTENDANCES_2).build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withCourse(VALID_COURSE_AMY).withTags(VALID_TAG_FRIEND)
            .withGrades(EMPTY_SAMPLE_GRADES)
            .withAttendances(EMPTY_SAMPLE_ATTENDANCES).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withCourse(VALID_COURSE_BOB).withTags(VALID_TAG_SENIOR, VALID_TAG_FRIEND)
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
