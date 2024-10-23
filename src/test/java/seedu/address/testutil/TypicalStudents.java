package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_HUGH;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.student.Student;



/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student DIDDY = new StudentBuilder().withName(VALID_NAME_DIDDY)
            .withPhone(VALID_PHONE_DIDDY).withTutorialGroup(VALID_TUTORIAL_GROUP_DIDDY)
            .withStudentNumber(VALID_STUDENT_NUMBER_DIDDY).build();

    public static final Student HUGH = new StudentBuilder().withName(VALID_NAME_HUGH)
            .withPhone(VALID_PHONE_HUGH).withTutorialGroup(VALID_TUTORIAL_GROUP_HUGH)
            .withStudentNumber(VALID_STUDENT_NUMBER_HUGH).build();


    //============================Students with Assignments============================================================
    public static final Student ALICE = new StudentBuilder().withName("Alice Smith")
            .withPhone("81234567").withTutorialGroup("B11")
            .withStudentNumber("A0123456M")
            .withAssignment("Assignment 1", "2021-10-10",
                    "Y", "Y", "95").build();

    static {
        //Adding attendance records for ALICE
        AttendanceRecord attendanceRecord = new AttendanceRecord(LocalDate.parse("2023-10-01"),
                new Attendance("p"));
        ALICE.addAttendanceRecord(attendanceRecord);
    }

    public static final Student BOB = new StudentBuilder().withName("Bob Tan")
            .withPhone("91234567").withTutorialGroup("C21")
            .withStudentNumber("A0654321X")
            .withAssignment("Assignment 2", "2021-10-11",
                    "N", "N", "10").build();

    public static final Student CHARLIE = new StudentBuilder().withName("Charlie Lim")
            .withPhone("83312233").withTutorialGroup("A10")
            .withStudentNumber("A0987654Z")
            .withAssignment("Assignment 3", "2021-10-12",
                    "Y", "N", "20").build();

    public static final Student DAVID = new StudentBuilder().withName("David Lee")
            .withPhone("89994444").withTutorialGroup("D14")
            .withStudentNumber("A0345678K")
            .withAssignment("Assignment 4", "2021-10-13",
                    "N", "Y", "30").build();

    public static final Student ELLA = new StudentBuilder().withName("Ella Wong")
            .withPhone("84443322").withTutorialGroup("E31")
            .withStudentNumber("A0543217J")
            .withAssignment("Assignment 5", "2021-10-14",
                    "Y", "Y", "40").build();

    public static final Student FRANK = new StudentBuilder().withName("Frank Ho")
            .withPhone("88881111").withTutorialGroup("F12")
            .withStudentNumber("A0135792H")
            .withAssignment("Assignment 6", "2021-10-15",
                    "N", "N", "50").build();

    public static final Student GRACE = new StudentBuilder().withName("Grace Ng")
            .withPhone("81119988").withTutorialGroup("G23")
            .withStudentNumber("A0988765T")
            .withAssignment("Assignment 7", "2021-10-16",
                    "Y", "N", "60").build();


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
        return new ArrayList<>(Arrays.asList(DIDDY, HUGH));
    }
}
