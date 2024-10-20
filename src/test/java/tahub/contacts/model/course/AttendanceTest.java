package tahub.contacts.model.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static tahub.contacts.testutil.Assert.assertThrows;
import static tahub.contacts.testutil.AttendanceExamples.ABSENT;
import static tahub.contacts.testutil.AttendanceExamples.ATTENDED;
import static tahub.contacts.testutil.AttendanceExamples.EMPTY_ATTENDANCE_LIST;
import static tahub.contacts.testutil.AttendanceExamples.EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5;
import static tahub.contacts.testutil.AttendanceExamples.SINGULAR_ABSENT_ATTENDANCE_LIST;
import static tahub.contacts.testutil.AttendanceExamples.SINGULAR_ATTENDED_ATTENDANCE_LIST;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import tahub.contacts.model.course.exceptions.AttendanceOperationException;

@DisplayName("Attendance")
public class AttendanceTest {

    // Constructor-only tests
    @Test
    @DisplayName("Returns correct values after constructor takes in no list")
    public void constructor_noList_correctValues() {
        Attendance a = new Attendance();
        assertEquals(a.getAttendanceAttendedCount(), 0);
        assertEquals(a.getAttendanceTotalCount(), 0);
        assertEquals(a.getAttendanceList(), EMPTY_ATTENDANCE_LIST);
    }

    @Test
    @DisplayName("Returns correct values after constructor takes in empty list")
    public void constructor_emptyList_correctValues() {
        Attendance a = new Attendance(EMPTY_ATTENDANCE_LIST);
        assertEquals(a.getAttendanceAttendedCount(), 0);
        assertEquals(a.getAttendanceTotalCount(), 0);
        assertEquals(a.getAttendanceList(), EMPTY_ATTENDANCE_LIST);
    }

    @Test
    @DisplayName("Returns correct values after constructor takes in list with one absent session")
    public void constructor_oneAbsenceList_correctValues() {
        Attendance a = new Attendance(SINGULAR_ABSENT_ATTENDANCE_LIST);
        assertEquals(a.getAttendanceAttendedCount(), 0);
        assertEquals(a.getAttendanceTotalCount(), 1);
        assertEquals(a.getAttendanceList(), SINGULAR_ABSENT_ATTENDANCE_LIST);
    }

    @Test
    @DisplayName("Returns correct values after constructor takes in list with one attended session")
    public void constructor_oneAttendedList_correctValues() {
        Attendance a = new Attendance(SINGULAR_ATTENDED_ATTENDANCE_LIST);
        assertEquals(a.getAttendanceAttendedCount(), 1);
        assertEquals(a.getAttendanceTotalCount(), 1);
        assertEquals(a.getAttendanceList(), SINGULAR_ATTENDED_ATTENDANCE_LIST);

    }

    @Test
    @DisplayName("Returns correct values after constructor takes in sample attendance list")
    public void constructor_exampleList_correctValues() {
        Attendance a = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
        assertEquals(a.getAttendanceAttendedCount(), 3);
        assertEquals(a.getAttendanceTotalCount(), 5);
        assertEquals(a.getAttendanceList(), EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);

    }

    // Attendance-marking tests
    @Test
    @DisplayName("Returns correct values after absent sessions added")
    public void addAbsences_correctValues() {
        Attendance a = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
        a.addAbsentLesson();
        a.addAbsentLesson();
        a.addAbsentLesson();
        a.addAbsentLesson();
        assertEquals(a.getAttendanceAttendedCount(), 3);
        assertEquals(a.getAttendanceTotalCount(), 9);
        assertEquals(a.getAttendanceList(),
                List.of(ABSENT, ATTENDED, ATTENDED, ABSENT, ATTENDED, ABSENT, ABSENT, ABSENT, ABSENT));
    }

    @Test
    @DisplayName("Returns correct values after attended sessions added")
    public void addAttendances_correctValues() {
        Attendance a = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
        a.addAttendedLesson();
        a.addAttendedLesson();
        a.addAttendedLesson();
        assertEquals(a.getAttendanceAttendedCount(), 6);
        assertEquals(a.getAttendanceTotalCount(), 8);
        assertEquals(a.getAttendanceList(),
                List.of(ABSENT, ATTENDED, ATTENDED, ABSENT, ATTENDED, ATTENDED, ATTENDED, ATTENDED));
    }

    @Test
    @DisplayName("Returns correct values after a series of sessions added")
    public void addSessions_correctValues() {
        Attendance a = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
        a.addAttendedLesson();
        a.addAbsentLesson();
        a.addAbsentLesson();
        a.addAttendedLesson();
        a.addAttendedLesson();
        a.addAbsentLesson();
        a.addAttendedLesson();
        a.addAttendedLesson();
        assertEquals(a.getAttendanceAttendedCount(), 8);
        assertEquals(a.getAttendanceTotalCount(), 13);
        assertEquals(a.getAttendanceList(),
                List.of(ABSENT, ATTENDED, ATTENDED, ABSENT, ATTENDED, ATTENDED, ABSENT,
                        ABSENT, ATTENDED, ATTENDED, ABSENT, ATTENDED, ATTENDED));
    }

    // removal tests
    @Test
    @DisplayName("Returns correct empty list after clearing an empty attendance list")
    public void clear_emptyList_emptyList() {
        Attendance a = new Attendance(EMPTY_ATTENDANCE_LIST);
        a.clear();
        assertEquals(a.getAttendanceAttendedCount(), 0);
        assertEquals(a.getAttendanceTotalCount(), 0);
        assertEquals(a.getAttendanceList(), EMPTY_ATTENDANCE_LIST);
    }

    @Test
    @DisplayName("Returns correct empty list after clearing a sample attendance list")
    public void clear_sampleList_emptyList() {
        Attendance a = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
        a.clear();
        assertEquals(a.getAttendanceAttendedCount(), 0);
        assertEquals(a.getAttendanceTotalCount(), 0);
        assertEquals(a.getAttendanceList(), EMPTY_ATTENDANCE_LIST);
    }

    @Test
    @DisplayName("Returns correct values after removing the last session successively")
    public void removeLastMultiple_nonzeroList_correctValues() throws AttendanceOperationException {
        Attendance a = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
        a.removeLast(); // remove attended session
        a.removeLast(); // remove absent session
        a.removeLast(); // remove attended session
        assertEquals(a.getAttendanceAttendedCount(), 1);
        assertEquals(a.getAttendanceTotalCount(), 2);
        assertEquals(a.getAttendanceList(), List.of(ABSENT, ATTENDED));
    }

    @Test
    @DisplayName("Returns correct values after removing the last session once")
    public void removeLast_nonzeroList_correctValues() throws AttendanceOperationException {
        Attendance a = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
        a.removeLast(); // remove attended session
        assertEquals(a.getAttendanceAttendedCount(), 2);
        assertEquals(a.getAttendanceTotalCount(), 4);
        assertEquals(a.getAttendanceList(), List.of(ABSENT, ATTENDED, ATTENDED, ABSENT));
    }

    @Test
    @DisplayName("Throws correct exception after removing the last session from an empty list")
    public void removeLast_emptyList_throwsException() {
        Attendance a = new Attendance(EMPTY_ATTENDANCE_LIST);
        assertThrows(AttendanceOperationException.class, a::removeLast);
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
            public void differentAttendanceList() {
                Attendance a1 = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
                Attendance a2 = new Attendance(SINGULAR_ATTENDED_ATTENDANCE_LIST);
                assertNotEquals(a1, a2);
            }

            @Test
            @DisplayName("different type")
            public void differentType() {
                Attendance a1 = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
                String a2 = "different-type";
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

    // Hashcode
    @Test
    @DisplayName("same attendance list has same hashcode")
    public void hashCode_sameAttendanceList_sameHashCode() {
        Attendance a1 = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
        Attendance a2 = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);

        assertEquals(a1.hashCode(), a2.hashCode());
    }
}
