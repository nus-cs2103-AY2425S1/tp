package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentList;
import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;
import seedu.address.model.tut.Tutorial;
import seedu.address.model.tut.TutorialList;

/**
 * Contains unit tests for {@code AttendCommand}.
 */
public class AttendCommandTest {

    @Test
    public void constructor_nullStudentId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AttendCommand(null, TutorialId.of("1001"), new Date()));
    }

    @Test
    public void constructor_nullTutorialClass_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AttendCommand(new StudentId("1001"), null, new Date()));
    }

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AttendCommand(new StudentId("1001"),
                TutorialId.of("1001"), null));
    }

    @Test
    public void execute_attendanceRecordedSuccessfully() throws Exception {
        // Arrange
        ModelStubAcceptingAttendanceRecorded modelStub = new ModelStubAcceptingAttendanceRecorded();
        StudentId studentId = new StudentId("1001");
        TutorialId tutorialId = TutorialId.of("1001");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse("2024/02/21");

        AttendCommand attendCommand = new AttendCommand(studentId, tutorialId, date);

        // Act
        CommandResult commandResult = attendCommand.execute(modelStub);

        // Assert
        String expectedMessage = AttendCommand.MESSAGE_SUCCESS + "\n" + attendCommand;
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(new AttendanceRecord(studentId, tutorialId, date)),
                modelStub.attendanceRecords);
    }

    @Test
    public void execute_attendanceRecordingFails_throwsCommandException() {
        ModelStubWithoutTutorialClass modelStub = new ModelStubWithoutTutorialClass();
        StudentId studentId = new StudentId("1001");
        TutorialId tutorialId = TutorialId.of("1001");
        Date date = new Date();

        AttendCommand attendCommand = new AttendCommand(studentId, tutorialId, date);

        assertThrows(CommandException.class, AttendCommand.MESSAGE_FAILURE, () -> attendCommand.execute(modelStub));
    }

    @Test
    public void equals_test() throws Exception {
        // Arrange
        StudentId studentId1 = new StudentId("1001");
        StudentId studentId2 = new StudentId("1002");
        TutorialId tutorialId1 = TutorialId.of("1001");
        TutorialId tutorialId2 = TutorialId.of("1002");
        Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse("2024/02/21");
        Date date2 = new SimpleDateFormat("yyyy/MM/dd").parse("2024/02/22");

        AttendCommand attendCommand1 = new AttendCommand(studentId1, tutorialId1, date1);
        AttendCommand attendCommand2 = new AttendCommand(studentId1, tutorialId1, date1);
        AttendCommand attendCommand3 = new AttendCommand(studentId2, tutorialId1, date1);
        AttendCommand attendCommand4 = new AttendCommand(studentId1, tutorialId2, date1);
        AttendCommand attendCommand5 = new AttendCommand(studentId1, tutorialId1, date2);

        assertTrue(attendCommand1.equals(attendCommand1));
        assertTrue(attendCommand1.equals(attendCommand2));
        assertFalse(attendCommand1.equals(attendCommand3));
        assertFalse(attendCommand1.equals(attendCommand4));
        assertFalse(attendCommand1.equals(attendCommand5));
        assertFalse(attendCommand1.equals(1));
        assertFalse(attendCommand1.equals(null));
    }

    @Test
    public void toString_test() throws Exception {
        // Arrange
        StudentId studentId = new StudentId("1001");
        TutorialId tutorialId = TutorialId.of("1001");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse("2024/02/21");

        AttendCommand attendCommand = new AttendCommand(studentId, tutorialId, date);

        // Act
        String expectedString = "Student: " + studentId + "\n"
                + "Date: " + sdf.format(date) + "\n"
                + "Tutorial ID: " + tutorialId;

        // Assert
        assertEquals(expectedString, attendCommand.toString());
    }

    // Helper classes and stubs

    /**
     * A default model stub that has all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTutorial(Tutorial tutorial) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTutorial(TutorialId tutorialId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTutorial(Tutorial tutorial) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TutorialList getTutorialList() {
            return null;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudent(Student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudent(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean setStudentAttendance(StudentId target, TutorialId tut, Date date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean setStudentAbsent(StudentId target, TutorialId tut, Date date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String listAssignments() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAssignments(AssignmentList assignments) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTutorial(Tutorial tutorial) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudentWithId(StudentId studentId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void assignStudent(Student student, TutorialId tutorialId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTutorials(TutorialList tutorials) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public String checkAssignment(Assignment assignment) throws AssignmentNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAssignmentStatus(Assignment assignment, Student targetStudent, boolean newStatus)
                throws AssignmentNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public AssignmentList getAssignmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always records attendance successfully.
     */
    private class ModelStubAcceptingAttendanceRecorded extends ModelStub {
        final ArrayList<AttendanceRecord> attendanceRecords = new ArrayList<>();

        @Override
        public boolean setStudentAttendance(StudentId studentId, TutorialId tutorialId, Date date) {
            requireNonNull(studentId);
            requireNonNull(tutorialId);
            requireNonNull(date);
            attendanceRecords.add(new AttendanceRecord(studentId, tutorialId, date));
            return true;
        }
    }

    /**
     * A Model stub without tutorial class
     */
    private class ModelStubWithoutTutorialClass extends ModelStub {
        private final List<Tutorial> tutorials = new ArrayList<>();

        @Override
        public boolean setStudentAttendance(StudentId studentId, TutorialId tutorialId, Date date) {
            boolean isSuccess = tutorials.stream()
                    .filter(s -> s.getTutorialId().equals(tutorialId))
                    .findFirst()
                    .map(tutorial -> tutorial.setAttendance(date, studentId))
                    .orElse(false);
            updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
            return isSuccess;
        }

        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            // Do nothing
        }
    }

    /**
     * Helper class to represent an attendance record for testing purposes.
     */
    private class AttendanceRecord {
        private final StudentId studentId;
        private final TutorialId tutorialId;
        private final Date date;

        AttendanceRecord(StudentId studentId, TutorialId tutorialId, Date date) {
            this.studentId = studentId;
            this.tutorialId = tutorialId;
            this.date = date;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AttendanceRecord)) {
                return false;
            }
            AttendanceRecord otherRecord = (AttendanceRecord) other;
            return studentId.equals(otherRecord.studentId)
                    && tutorialId.equals(otherRecord.tutorialId)
                    && date.equals(otherRecord.date);
        }
    }
}
