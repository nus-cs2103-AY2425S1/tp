package seedu.address.ui;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

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
                .withTutorialGroup("A01").build();
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
    void toStringMethod() {
        TutorialGroup tg = new TutorialGroup("A01");
        AttendanceWindow window = new AttendanceWindow(tg);
        String expectedString = AttendanceWindow.class.getCanonicalName()
                + "@" + Integer.toHexString(window.hashCode());
        assertEquals(expectedString, window.toString());
    }
}
