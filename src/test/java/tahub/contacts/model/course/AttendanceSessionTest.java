package tahub.contacts.model.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Session")
public class AttendanceSessionTest {
    @Test
    @DisplayName("Returns attendance correctly after created using constructor")
    public void constructor_createSession_returnCorrect() {
        AttendanceSession s1 = new AttendanceSession(true);
        AttendanceSession s2 = new AttendanceSession(false);
        assertTrue(s1.getIsSessionAttended());
        assertFalse(s2.getIsSessionAttended());
    }

    @Test
    @DisplayName("Returns attendance as false after created as an absent session")
    public void factory_absentSession_returnFalse() {
        AttendanceSession s = AttendanceSession.createAbsent();
        assertFalse(s.getIsSessionAttended());
    }

    @Test
    @DisplayName("Returns attendance as true after created as an attended session")
    public void factory_attendedSession_returnTrue() {
        AttendanceSession s = AttendanceSession.createAttended();
        assertTrue(s.getIsSessionAttended());
    }

    // utility tests
    @Nested
    @DisplayName("utility tests")
    class Util {
        @Test
        @DisplayName("equals")
        public void equals_correct() {
            AttendanceSession s1 = AttendanceSession.createAttended();
            AttendanceSession s2 = AttendanceSession.createAbsent();
            AttendanceSession s3 = AttendanceSession.createAbsent();
            String notSession = "not";
            assertEquals(s2, s3);
            assertEquals(s1, s1);
            assertNotEquals(s1, s2);
            assertNotEquals(s1, notSession);
        }

        @Test
        @DisplayName("equals")
        public void hashcode_correct() {
            AttendanceSession s1 = AttendanceSession.createAttended();
            AttendanceSession s2 = AttendanceSession.createAbsent();
            AttendanceSession s3 = AttendanceSession.createAbsent();
            assertEquals(s2.hashCode(), s3.hashCode());
            assertNotEquals(s1.hashCode(), s2.hashCode());
        }

        @Test
        @DisplayName("toString")
        public void toString_correct() {
            AttendanceSession s1 = AttendanceSession.createAttended();
            AttendanceSession s2 = AttendanceSession.createAbsent();
            assertEquals(s1.toString(), "V");
            assertEquals(s2.toString(), "X");
        }
    }

}
