package tahub.contacts.model.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Attendance")
public class AttendanceTest {
    public static final List<Boolean> EMPTY_ATTENDANCE_LIST = new ArrayList<>();
    public static final List<Boolean> SINGULAR_ATTENDED_ATTENDANCE_LIST = List.of(true);
    public static final List<Boolean> SINGULAR_ABSENT_ATTENDANCE_LIST = List.of(false);
    public static final List<Boolean> EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5 = List.of(false, true, true, false, true);

    // Constructor-only tests
    @Test
    @DisplayName("Returns correct values after constructor takes in no list")
    public void constructor_noList_correctValues() {
        Attendance a = new Attendance();
        assertEquals(a.getAttendanceAttendedCount(), 0);
        assertEquals(a.getAttendanceTotalCount(), 0);
    }

    @Test
    @DisplayName("Returns correct values after constructor takes in empty list")
    public void constructor_emptyList_correctValues() {
        Attendance a = new Attendance(EMPTY_ATTENDANCE_LIST);
        assertEquals(a.getAttendanceAttendedCount(), 0);
        assertEquals(a.getAttendanceTotalCount(), 0);
    }

    @Test
    @DisplayName("Returns correct values after constructor takes in list with one absent session")
    public void constructor_oneAbsenceList_correctValues() {
        Attendance a = new Attendance(SINGULAR_ABSENT_ATTENDANCE_LIST);
        assertEquals(a.getAttendanceAttendedCount(), 0);
        assertEquals(a.getAttendanceTotalCount(), 1);
    }

    @Test
    @DisplayName("Returns correct values after constructor takes in list with one attended session")
    public void constructor_oneAttendedList_correctValues() {
        Attendance a = new Attendance(SINGULAR_ATTENDED_ATTENDANCE_LIST);
        assertEquals(a.getAttendanceAttendedCount(), 1);
        assertEquals(a.getAttendanceTotalCount(), 1);
    }

    @Test
    @DisplayName("Returns correct values after constructor takes in sample attendance list")
    public void constructor_exampleList_correctValues() {
        Attendance a = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
        assertEquals(a.getAttendanceAttendedCount(), 3);
        assertEquals(a.getAttendanceTotalCount(), 5);
    }

    // Attendance-marking tests
    @Test
    @DisplayName("Returns correct values after absent sessions added")
    public void addAbsences_correctValues() {
        Attendance a = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
        a.addAbsent();
        a.addAbsent();
        a.addAbsent();
        a.addAbsent();
        assertEquals(a.getAttendanceAttendedCount(), 3);
        assertEquals(a.getAttendanceTotalCount(), 9);
    }

    @Test
    @DisplayName("Returns correct values after attended sessions added")
    public void addAttendances_correctValues() {
        Attendance a = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
        a.addAttended();
        a.addAttended();
        a.addAttended();
        assertEquals(a.getAttendanceAttendedCount(), 6);
        assertEquals(a.getAttendanceTotalCount(), 8);
    }

    @Test
    @DisplayName("Returns correct values after a series of sessions added")
    public void addSessions_correctValues() {
        Attendance a = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
        a.addAttended();
        a.addAbsent();
        a.addAbsent();
        a.addAttended();
        a.addAttended();
        a.addAbsent();
        a.addAttended();
        a.addAttended();
        assertEquals(a.getAttendanceAttendedCount(), 8);
        assertEquals(a.getAttendanceTotalCount(), 13);
    }

    // equals tests
    @Nested
    @DisplayName("Equals")
    class Equals {
        @Nested
        @DisplayName("returns true for")
        class TrueEquals {
            @Test
            @DisplayName("same Attendance object")
            public void sameAttendanceObject() {
                Attendance a1 = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
                assertEquals(a1, a1);
            }

            @Test
            @DisplayName("same attendance list")
            public void sameAttendanceList() {
                Attendance a1 = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
                Attendance a2 = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
                assertEquals(a1, a2);
            }

            @Test
            @DisplayName("both empty attendance list")
            public void bothEmptyAttendanceList() {
                Attendance a1 = new Attendance(EMPTY_ATTENDANCE_LIST);
                Attendance a2 = new Attendance(EMPTY_ATTENDANCE_LIST);
                assertEquals(a1, a2);
            }
        }

        @Nested
        @DisplayName("returns false for")
        class FalseEquals {
            @Test
            @DisplayName("different attendance list")
            public void sameAttendanceList() {
                Attendance a1 = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
                Attendance a2 = new Attendance(SINGULAR_ATTENDED_ATTENDANCE_LIST);
                assertNotEquals(a1, a2);
            }
        }
    }

    // toString tests
    @Nested
    @DisplayName("toString")
    class ToString {
        @Test
        @DisplayName("shows correct string for empty attendance")
        public void emptyAttendance_correctString() {
            Attendance a = new Attendance(EMPTY_ATTENDANCE_LIST);
            assertEquals(a.toString(), "[0/0]");
        }

        @Test
        @DisplayName("shows correct string for example attendance")
        public void exampleAttendance_correctString() {
            Attendance a = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
            assertEquals(a.toString(), "[3/5]");
        }
    }
}
