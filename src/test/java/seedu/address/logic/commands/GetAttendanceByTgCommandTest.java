package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;
import seedu.address.testutil.StudentBuilder;
import seedu.address.ui.AttendanceRow;
import seedu.address.ui.AttendanceWindow;

class GetAttendanceByTgCommandTest {

    private final TutorialGroup validTutorialGroup = new TutorialGroup("A01");
    private final TutorialGroup emptyTutorialGroup = new TutorialGroup("B01");

    private final Student student1 = new StudentBuilder().withName("John Doe")
            .withStudentNumber("A1234567M")
            .withTutorialGroup("A01").build();
    private final Student student2 = new StudentBuilder().withName("Jane Doe")
            .withStudentNumber("A7654321M")
            .withTutorialGroup("A01").build();

    @BeforeEach
    public void setUp() {
        // Ensure no AttendanceWindow is open before each test
        GetAttendanceByTgCommand.closeCurrentWindow();
    }

    @BeforeAll
    public static void initJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.startup(() -> {});
                Platform.setImplicitExit(false);
            } catch (IllegalStateException e) {
                // JavaFX is already initialized, no need to do anything here
            }
        }
    }

    @AfterAll
    public static void closeJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.exit();
        }
    }

    @Test
    void execute_validTutorialGroupWithStub_success() throws CommandException {
        Model model = new ModelManager();
        model.addStudent(student1);
        model.addStudent(student2);

        AttendanceWindowStub attendanceWindowStub = new AttendanceWindowStub(validTutorialGroup);
        attendanceWindowStub.addStudent(student1);
        attendanceWindowStub.addStudent(student2);

        GetAttendanceByTgCommand command = new GetAttendanceByTgCommand(validTutorialGroup);
        command.setAttendanceWindow(attendanceWindowStub);

        CommandResult result = command.execute(model);

        String expectedMessage = "Attendance window opened for Tutorial Group: " + validTutorialGroup;
        assertEquals(expectedMessage, result.getFeedbackToUser());


    }
    @Test
    void execute_emptyTutorialGroup_throwsCommandException() {
        Model model = new ModelManager();

        GetAttendanceByTgCommand command = new GetAttendanceByTgCommand(emptyTutorialGroup);

        assertThrows(CommandException.class,
                String.format(GetAttendanceByTgCommand.MESSAGE_NO_STUDENTS, emptyTutorialGroup), ()
                        -> command.execute(model));
    }

    @Test
    public void equalsMethod() {
        TutorialGroup tg1 = new TutorialGroup("A01");
        TutorialGroup tg2 = new TutorialGroup("A02");

        GetAttendanceByTgCommand command1 = new GetAttendanceByTgCommand(tg1);
        GetAttendanceByTgCommand command2 = new GetAttendanceByTgCommand(tg1);
        GetAttendanceByTgCommand command3 = new GetAttendanceByTgCommand(tg2);

        // Same object
        assertEquals(command1, command1);

        // Different objects, same values
        assertEquals(command1, command2);

        // Different values
        assertNotEquals(command1, command3);

        // Different types
        assertNotEquals(command1, new Object());

        // Null
        assertNotEquals(command1, null);
    }

    @Test
    public void toStringMethod() {
        TutorialGroup tg = new TutorialGroup("A01");
        GetAttendanceByTgCommand command = new GetAttendanceByTgCommand(tg);
        String expectedString = GetAttendanceByTgCommand.class.getCanonicalName() + "{tutorialGroup=" + tg + "}";
        assertEquals(expectedString, command.toString());
    }

    @Test
    void closeCurrentWindow_windowIsOpen_success() {
        // Set up an open AttendanceWindow
        AttendanceWindow attendanceWindow = new AttendanceWindow(validTutorialGroup);
        GetAttendanceByTgCommand command = new GetAttendanceByTgCommand(validTutorialGroup);
        command.setAttendanceWindow(attendanceWindow);

        // Call closeCurrentWindow and check if it returns true
        boolean result = GetAttendanceByTgCommand.closeCurrentWindow();
        assertTrue(result, "Expected closeCurrentWindow to return true when a window is open.");
    }

    @Test
    void closeCurrentWindow_noWindowOpen_failure() {
        // Ensure no window is open
        GetAttendanceByTgCommand.closeCurrentWindow();

        // Call closeCurrentWindow and check if it returns false
        boolean result = GetAttendanceByTgCommand.closeCurrentWindow();
        assertFalse(result, "Expected closeCurrentWindow to return false when no window is open.");
    }

    public class AttendanceWindowStub extends AttendanceWindow {

        private final List<Student> students;
        private final Set<LocalDate> attendanceDates;
        private ObservableList<AttendanceRow> data;

        public AttendanceWindowStub(TutorialGroup tutorialGroup) {
            super(tutorialGroup);
            this.students = new ArrayList<>();
            this.attendanceDates = new TreeSet<>();
        }

        @Override
        public void show(Model model) {
            // Simulate showing the attendance window
            System.out.println("Showing attendance window for: " + super.getTutorialGroup());
            for (Student student : students) {
                System.out.println("Student: " + student.getName());
                for (AttendanceRecord record : student.getAttendanceRecord()) {
                    System.out.println("Date: " + record.getDate() + ", Attendance: " + record.getAttendance());
                }
            }
        }

        @Override
        public void close() {
            // Simulate closing the attendance window
            System.out.println("Closing attendance window for: " + super.getTutorialGroup());
        }

        public void addStudent(Student student) {
            students.add(student);
            for (AttendanceRecord record : student.getAttendanceRecord()) {
                attendanceDates.add(record.getDate());
            }
        }

        public Set<LocalDate> getAttendanceDates() {
            return attendanceDates;
        }

        public ObservableList<AttendanceRow> getStudentAttendanceRows(Model model) {
            ObservableList<AttendanceRow> rows = FXCollections.observableArrayList();
            for (Student student : students) {
                AttendanceRow row = new AttendanceRow(student);
                for (AttendanceRecord record : student.getAttendanceRecord()) {
                    row.addAttendance(record.getDate(), record.getAttendance().toString());
                }
                rows.add(row);
            }
            return rows;
        }

        private void refreshTable(Model model) {
            data.clear();
            data.addAll(getStudentAttendanceRows(model));
            sortAndRefreshDates(model);
        }

        private void sortAndRefreshDates(Model model) {
            List<LocalDate> sortedDates = new ArrayList<>(getAttendanceDates());
            sortedDates.sort(LocalDate::compareTo);
        }
    }

}
