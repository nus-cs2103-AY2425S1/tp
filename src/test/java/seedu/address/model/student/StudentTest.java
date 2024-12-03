package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_DIDDY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_NAME_A;
import static seedu.address.testutil.TypicalAssignments.DEADLINE_C;
import static seedu.address.testutil.TypicalAssignments.GRADE_90;
import static seedu.address.testutil.TypicalAssignments.MATH_ASSIGNMENT_SUBMITTED;
import static seedu.address.testutil.TypicalAssignments.SCIENCE_ASSIGNMENT_GRADED;
import static seedu.address.testutil.TypicalAssignments.STATUS_Y;
import static seedu.address.testutil.TypicalStudents.DIDDY;
import static seedu.address.testutil.TypicalStudents.HUGH;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.AssignmentQuery;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.StudentBuilder;

public class StudentTest {

    private Student student;

    @BeforeEach
    void setUp() {
        student = new StudentBuilder(HUGH).build();

        // Adding assignments to the student
        student.addAssignment(MATH_ASSIGNMENT_SUBMITTED);
        student.addAssignment(SCIENCE_ASSIGNMENT_GRADED);
    }

    @Test
    public void isSameStudent() {
        // same object -> returns true
        assertTrue(HUGH.isSameStudent(HUGH));

        // null -> returns false
        assertFalse(HUGH.isSameStudent(null));

        // same student number, all other attributes different -> returns true
        Student editedHugh = new StudentBuilder(HUGH).withPhone(VALID_PHONE_DIDDY)
                .withTutorialGroup(VALID_TUTORIAL_GROUP_DIDDY).withName(VALID_NAME_DIDDY).build();
        assertTrue(HUGH.isSameStudent(editedHugh));

        // different student number, all other attributes same -> returns false
        editedHugh = new StudentBuilder(HUGH).withStudentNumber("A7654321A").build();
        assertFalse(HUGH.isSameStudent(editedHugh));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student hughCopy = new StudentBuilder(HUGH).build();
        assertTrue(HUGH.equals(hughCopy));

        // same object -> returns true
        assertTrue(HUGH.equals(HUGH));

        // null -> returns false
        assertFalse(HUGH.equals(null));

        // different type -> returns false
        assertFalse(HUGH.equals(5));

        // different student -> returns false
        assertFalse(HUGH.equals(DIDDY));

        // different name -> returns false
        Student editedHugh = new StudentBuilder(HUGH).withName(VALID_NAME_DIDDY).build();
        assertFalse(HUGH.equals(editedHugh));

        // different contact number -> returns false
        editedHugh = new StudentBuilder(HUGH).withPhone(VALID_PHONE_DIDDY).build();
        assertFalse(HUGH.equals(editedHugh));

        // different tutorial group -> returns false
        editedHugh = new StudentBuilder(HUGH).withTutorialGroup(VALID_TUTORIAL_GROUP_DIDDY).build();
        assertFalse(HUGH.equals(editedHugh));

        // different student number -> returns false
        editedHugh = new StudentBuilder(HUGH).withStudentNumber(VALID_STUDENT_NUMBER_DIDDY).build();
        assertFalse(HUGH.equals(editedHugh));
    }

    @Test
    public void toStringMethod() {
        String expected = Student.class.getCanonicalName()
                + "{name=" + HUGH.getName()
                + ", contactNumber=" + HUGH.getPhone()
                + ", tutorialGroup=" + HUGH.getTutorialGroup()
                + ", studentNumber=" + HUGH.getStudentNumber()
                + ", assignments=" + HUGH.getAssignments()
                + ", attendanceRecords=" + HUGH.getAttendanceRecord() + "}";
        assertEquals(expected, HUGH.toString());
    }

    @Test
    void deleteAssignment_validAssignment_success() throws CommandException {
        assertEquals(MATH_ASSIGNMENT_SUBMITTED, student.deleteAssignment(ASSIGNMENT_NAME_A));
    }

    @Test
    void deleteAssignment_nonExistentAssignment_returnsNull() throws CommandException {
        // Execute delete
        Assignment deletedAssignment = student.deleteAssignment(new AssignmentName("Nonexistent assignment"));

        // Verify that the method returns null when the assignment does not exist
        assertEquals(null, deletedAssignment);
    }

    @Test
    void deleteAssignment_nullQuery_throwsException() {
        // Verify that passing a null query throws an exception
        assertThrows(NullPointerException.class, () -> student.deleteAssignment(null));
    }

    @Test
    public void getAttendanceRecord_noRecords_emptyList() {
        // Create a student without attendance records
        Name name = new Name("John Doe");
        Phone phone = new Phone("12345678");
        TutorialGroup tutorialGroup = new TutorialGroup("G01");
        StudentNumber studentNumber = new StudentNumber("S1234567A");
        Student student = new Student(name, phone, tutorialGroup, studentNumber);

        // Verify that getAttendanceRecord() returns an empty list
        assertEquals(0, student.getAttendanceRecord().size());
    }

    @Test
    public void getAttendanceRecord_multipleRecords_success() {
        // Create a student
        Name name = new Name("Jane Doe");
        Phone phone = new Phone("87654321");
        TutorialGroup tutorialGroup = new TutorialGroup("G02");
        StudentNumber studentNumber = new StudentNumber("S7654321B");
        Student student = new Student(name, phone, tutorialGroup, studentNumber);

        // Mark attendance for multiple dates
        student.markAttendance(LocalDate.of(2024, 10, 22), "p");
        student.markAttendance(LocalDate.of(2024, 10, 23), "a");

        // Get the attendance records
        List<AttendanceRecord> attendanceRecords = student.getAttendanceRecord();

        // Verify that the size is correct
        assertEquals(2, attendanceRecords.size());

        // Verify that the records match the expected values
        assertEquals(LocalDate.of(2024, 10, 22), attendanceRecords.get(0).getDate());
        assertEquals(new Attendance("p"), attendanceRecords.get(0).getAttendance());
        assertEquals(LocalDate.of(2024, 10, 23), attendanceRecords.get(1).getDate());
        assertEquals(new Attendance("a"), attendanceRecords.get(1).getAttendance());
    }

    @Test
    void getStudentNumber_validStudent_success() {
        Student student = new StudentBuilder().withStudentNumber("S1234567A").build();
        assertEquals("S1234567A", student.getStudentNumber().toString());
    }

    @Test
    void equalsMethod() {
        Student student1 = new StudentBuilder().withName("John Doe").build();
        Student student2 = new StudentBuilder().withName("John Doe").build();
        Student student3 = new StudentBuilder().withName("Jane Doe").build();

        // Same object
        assertTrue(student1.equals(student1));

        // Different objects, same values
        assertTrue(student1.equals(student2));

        // Different names
        assertFalse(student1.equals(student3));

        // Different types
        assertFalse(student1.equals(new Object()));

        // Null
        assertFalse(student1.equals(null));
    }

    @Test
    void constructor_validInputs_success() {
        Name name = new Name("John Doe");
        Phone phone = new Phone("12345678");
        TutorialGroup tutorialGroup = new TutorialGroup("G01");
        StudentNumber studentNumber = new StudentNumber("S1234567A");
        Student student = new Student(name, phone, tutorialGroup, studentNumber);
        assertNotNull(student);
    }

    @Test
    void constructor_withAssignmentsAndAttendance_success() {
        Name name = new Name("John Doe");
        Phone phone = new Phone("12345678");
        TutorialGroup tutorialGroup = new TutorialGroup("G01");
        StudentNumber studentNumber = new StudentNumber("S1234567A");

        ObservableList<Assignment> assignments = FXCollections.observableArrayList();
        assignments.add(MATH_ASSIGNMENT_SUBMITTED);
        assignments.add(SCIENCE_ASSIGNMENT_GRADED);
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();

        Student student = new Student(name, phone, tutorialGroup, studentNumber, assignments, attendanceRecords);

        assertNotNull(student);
        assertEquals("John Doe", student.getName().toString());
        assertEquals("12345678", student.getPhone().toString());
        assertEquals("G01", student.getTutorialGroup().toString());
        assertEquals("S1234567A", student.getStudentNumber().toString());
        assertEquals(2, student.getAssignments().size());
        assertEquals(MATH_ASSIGNMENT_SUBMITTED, student.getAssignments().get(0));
        assertEquals(SCIENCE_ASSIGNMENT_GRADED, student.getAssignments().get(1));
        assertEquals(new ArrayList<>(), student.getAttendanceRecord());
    }
    @Test
    void getAttendanceRecordsString_noRecords_emptyString() {
        Student student = new StudentBuilder().build();
        assertEquals("", student.getAttendanceRecordsString());
    }

    @Test
    void getAttendanceRecordsString_multipleRecords_success() {
        Student student = new StudentBuilder().build();
        student.markAttendance(LocalDate.of(2024, 10, 22), "p");
        student.markAttendance(LocalDate.of(2024, 10, 23), "a");

        String expectedString = "2024-10-22: Present\n"
                + "2024-10-23: Absent\n";
        assertEquals(expectedString, student.getAttendanceRecordsString());
    }

    @Test
    void editAssignment_nonExistentAssignment_returnsNull() {
        // Attempt to edit an assignment that doesn't exist
        AssignmentName nonExistentName = new AssignmentName("Nonexistent Homework");
        AssignmentQuery query = new AssignmentQuery(null, DEADLINE_C, STATUS_Y, GRADE_90);

        Assignment result = student.editAssignment(nonExistentName, query);

        // Verify that the method returns null
        assertNull(result);
    }

    @Test
    void editAssignment_existingAssignment_editsAssignment() {
        // Create AssignmentQuery with new values to edit
        Assignment oldAssignment = new AssignmentBuilder(MATH_ASSIGNMENT_SUBMITTED).build();
        AssignmentQuery query = new AssignmentQuery(null, DEADLINE_C, STATUS_Y, GRADE_90);

        // Perform the edit operation
        Assignment oldAssignment2 = student.editAssignment(ASSIGNMENT_NAME_A, query);

        // Verify the old assignment details are returned
        assertEquals(oldAssignment, oldAssignment2);

        // Verify that the assignment is now updated
        Assignment updatedAssignment = student.getAssignments().get(0);
        assertEquals(DEADLINE_C, updatedAssignment.getDeadline());
        assertEquals(STATUS_Y, updatedAssignment.getSubmissionStatus());
        assertEquals(GRADE_90, updatedAssignment.getGrade());
    }
}
