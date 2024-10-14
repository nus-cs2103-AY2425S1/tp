package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIALCLASS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIALCLASS_BOB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.student.PresentDates;
import seedu.address.model.student.Student;
import seedu.address.model.tut.TutDate;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    // Date formatter
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    // Create sample Dates
    private static final Date DATE1;
    private static final Date DATE2;

    static {
        try {
            DATE1 = DATE_FORMATTER.parse("12/10/2023");
            DATE2 = DATE_FORMATTER.parse("19/10/2023");
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing dates in TypicalStudents", e);
        }
    }

    // Create sample TutDates
    private static final TutDate TUT_DATE1 = new TutDate(DATE1);
    private static final TutDate TUT_DATE2 = new TutDate(DATE2);

    // Create PresentDates for the students
    private static final PresentDates SAMPLE_PRESENT_DATES =
            new PresentDates(new ArrayList<>(Arrays.asList(TUT_DATE1, TUT_DATE2)));

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withStudentId("1001").withTutorialClass("1001")
            .withPresentDates(SAMPLE_PRESENT_DATES)
            .build();

    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
            .withStudentId("1002").withTutorialClass("1001")
            .withPresentDates(SAMPLE_PRESENT_DATES).build();

    public static final Student CARL = new StudentBuilder().withName("Carl Kurz")
            .withStudentId("1003").withTutorialClass("1001")
            .withPresentDates(SAMPLE_PRESENT_DATES).build();

    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier")
            .withStudentId("1004").withTutorialClass("1001")
            .withPresentDates(SAMPLE_PRESENT_DATES).build();

    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer")
            .withStudentId("1005").withTutorialClass("1001")
            .withPresentDates(SAMPLE_PRESENT_DATES).build();

    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz")
            .withStudentId("1006").withTutorialClass("1001")
            .withPresentDates(SAMPLE_PRESENT_DATES).build();

    public static final Student GEORGE = new StudentBuilder().withName("George Best")
            .withStudentId("1007").withTutorialClass("1001")
            .withPresentDates(SAMPLE_PRESENT_DATES).build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier")
            .withStudentId("1008").withTutorialClass("1001")
            .withPresentDates(SAMPLE_PRESENT_DATES).build();

    public static final Student IDA = new StudentBuilder().withName("Ida Mueller")
            .withStudentId("1009").withTutorialClass("1001")
            .withPresentDates(SAMPLE_PRESENT_DATES).build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY)
            .withStudentId(VALID_STUDENTID_AMY).withTutorialClass(VALID_TUTORIALCLASS_AMY)
            .withPresentDates(SAMPLE_PRESENT_DATES)
            .build();

    public static final Student BOB = new StudentBuilder().withName(VALID_NAME_BOB)
            .withStudentId(VALID_STUDENTID_BOB).withTutorialClass(VALID_TUTORIALCLASS_BOB)
            .withPresentDates(SAMPLE_PRESENT_DATES)
            .build();

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

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(
                ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE
        ));
    }
}
