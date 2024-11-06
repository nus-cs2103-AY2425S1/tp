package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Attendance(null));
    }

    @Test
    public void constructor_validAttendanceDateFormat_success() {

        LocalDate attendanceDate1 = LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT);
        Attendance attendancePresent1 = new Attendance(attendanceDate1);
        assertEquals("12/12/2024", attendancePresent1.toString());

        LocalDate attendanceDate2 = LocalDate.parse("31/12/2024", Attendance.VALID_DATE_FORMAT);
        Attendance attendancePresent2 = new Attendance(attendanceDate2);
        assertEquals("31/12/2024", attendancePresent2.toString());
    }

    @Test
    void isValidAttendance_validInput_success() {
        // Valid inputs
        assertTrue(Attendance.isValidAttendance("10/10/2024"));
        assertTrue(Attendance.isValidAttendance("31/10/2024"));
    }

    @Test
    void isValidAttendance_invalidInput_failure() {
        // Invalid inputs
        assertFalse(Attendance.isValidAttendance("32/10/2024")); // invalid day
        assertFalse(Attendance.isValidAttendance("30/13/2024")); // invalid month
        assertFalse(Attendance.isValidAttendance("3/10/2024")); // no leading 0 for day
        assertFalse(Attendance.isValidAttendance("30/3/2024")); // no leading 0 for month
        assertFalse(Attendance.isValidAttendance("3/10/24")); // wrong format for year
        assertFalse(Attendance.isValidAttendance("/10/2024")); // missing day
        assertFalse(Attendance.isValidAttendance("30//2024")); // missing month
        assertFalse(Attendance.isValidAttendance("30/10/")); // missing year
        assertFalse(Attendance.isValidAttendance("//2024")); // missing day & month
        assertFalse(Attendance.isValidAttendance("//")); // missing field
        assertFalse(Attendance.isValidAttendance("1 Jan")); // non-numerical input
        assertFalse(Attendance.isValidAttendance("")); // empty string
        assertFalse(Attendance.isValidAttendance(" ")); // spaces only
    }

    @Test
    public void equals() {
        Attendance attendance1 = new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT));
        Attendance attendance2 = new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT));
        Attendance attendance3 = new Attendance(LocalDate.parse("13/12/2024", Attendance.VALID_DATE_FORMAT));

        assertEquals(attendance1, attendance2);
        assertNotEquals(attendance1, attendance3);
        assertNotEquals(attendance2, attendance3);
    }
}
