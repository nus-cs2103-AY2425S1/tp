package seedu.address.ui;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;
import seedu.address.testutil.StudentBuilder;

public class AttendanceWindowTest {

    @Test
    void getAllAttendanceDates_validAttendanceRecords_success() {
        Model model = new ModelManager();

        // Add students with attendance records
        Student student1 = new StudentBuilder().withName("John Doe").withStudentNumber("A1234567M")
                .withTutorialGroup("A01").build_default();
        student1.markAttendance(LocalDate.of(2024, 10, 1), "p");
        student1.markAttendance(LocalDate.of(2024, 10, 15), "p");


        model.addStudent(student1);

        // Create AttendanceWindow and verify
        AttendanceWindow window = new AttendanceWindow(new TutorialGroup("A01"));
        Set<LocalDate> expectedDates = new TreeSet<>();
        expectedDates.add(LocalDate.of(2024, 10, 1));
        expectedDates.add(LocalDate.of(2024, 10, 15));

        Set<LocalDate> actualDates = window.getAllAttendanceDates(model);

        assertEquals(expectedDates, actualDates);
    }

    @Test
    void getStudentAttendanceRows_validData_success() {
        Model model = new ModelManager();

        // Add students with attendance records
        Student student1 = new StudentBuilder().withName("John Doe").withStudentNumber("A1234567M")
                .withTutorialGroup("A01").build();
        student1.markAttendance(LocalDate.of(2024, 10, 1), "p");
        student1.markAttendance(LocalDate.of(2024, 10, 15), "a");

        Student student2 = new StudentBuilder().withName("Jane Doe").withStudentNumber("A7654321M")
                .withTutorialGroup("A01").build();
        student2.markAttendance(LocalDate.of(2024, 10, 1), "a");
        student2.markAttendance(LocalDate.of(2024, 10, 15), "p");

        model.addStudent(student1);
        model.addStudent(student2);

        // Create AttendanceWindow and check the rows
        AttendanceWindow window = new AttendanceWindow(new TutorialGroup("A01"));
        ObservableList<AttendanceRow> rows = window.getStudentAttendanceRows(model);

        // Validate number of rows and content
        assertEquals(2, rows.size());
        assertEquals("John Doe", rows.get(0).getStudentName());
        assertEquals("Jane Doe", rows.get(1).getStudentName());
        assertEquals("Present", rows.get(0).getAttendanceForDate(LocalDate.of(2024, 10, 1)));
        assertEquals("Absent", rows.get(1).getAttendanceForDate(LocalDate.of(2024, 10, 1)));
    }

    @Test
    void getAllAttendanceDates_noAttendanceRecords_returnsEmptySet() {
        Model model = new ModelManager();

        // Add a student without attendance records
        Student student1 = new StudentBuilder().withName("John Doe").withStudentNumber("A1234567M")
                .withTutorialGroup("A01").build_default();
        model.addStudent(student1);

        AttendanceWindow window = new AttendanceWindow(new TutorialGroup("A01"));

        Set<LocalDate> actualDates = window.getAllAttendanceDates(model);
        assertTrue(actualDates.isEmpty(), "Expected no attendance dates, but some were returned.");
    }

    @Test
    void getAllAttendanceDates_noStudentsInGroup_returnsEmptySet() {
        Model model = new ModelManager();

        // No students are added to the model

        AttendanceWindow window = new AttendanceWindow(new TutorialGroup("A01"));

        Set<LocalDate> actualDates = window.getAllAttendanceDates(model);
        assertTrue(actualDates.isEmpty(), "Expected no attendance dates since there are no students.");
    }

    @Test
    void getStudentAttendanceRows_noStudentsInGroup_returnsEmptyList() {
        Model model = new ModelManager();

        // No students are added to the model

        AttendanceWindow window = new AttendanceWindow(new TutorialGroup("A01"));

        ObservableList<AttendanceRow> rows = window.getStudentAttendanceRows(model);
        assertTrue(rows.isEmpty(), "Expected no attendance rows since there are no students.");
    }

    @Test
    void getAllAttendanceDates_multipleStudents_success() {
        Model model = new ModelManager();

        Student student1 = new StudentBuilder().withName("John Doe").withStudentNumber("A1234567M")
                .withTutorialGroup("A01").build_default();
        student1.markAttendance(LocalDate.of(2024, 10, 1), "p");

        Student student2 = new StudentBuilder().withName("Jane Doe").withStudentNumber("A7654321M")
                .withTutorialGroup("A01").build_default();
        student2.markAttendance(LocalDate.of(2024, 10, 15), "a");

        model.addStudent(student1);
        model.addStudent(student2);

        AttendanceWindow window = new AttendanceWindow(new TutorialGroup("A01"));
        Set<LocalDate> expectedDates = new TreeSet<>();
        expectedDates.add(LocalDate.of(2024, 10, 1));
        expectedDates.add(LocalDate.of(2024, 10, 15));

        Set<LocalDate> actualDates = window.getAllAttendanceDates(model);
        assertEquals(expectedDates, actualDates, "Expected all attendance dates for both students.");
    }






    @Test
    void toStringMethod() {
        TutorialGroup tg = new TutorialGroup("A01");
        AttendanceWindow window = new AttendanceWindow(tg);
        String expectedString = AttendanceWindow.class.getCanonicalName()
                + "@" + Integer.toHexString(window.hashCode());
        assertEquals(expectedString, window.toString());
    }


}
