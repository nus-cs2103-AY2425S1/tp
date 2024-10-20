package tahub.contacts.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import tahub.contacts.model.course.Attendance;
import tahub.contacts.model.course.AttendanceSession;
import tahub.contacts.model.course.AttendanceTest;

@DisplayName("JsonAdaptedAttendance")
public class JsonAdaptedAttendanceTest {
    public static final List<AttendanceSession> EMPTY_ATTENDANCE_LIST = List.of();
    public static final List<AttendanceSession> EXAMPLE_ATTENDANCE_LIST =
            AttendanceTest.EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5;
    @Nested
    @DisplayName("toModelType()")
    class ToModelType {
        @Test
        @DisplayName("Returns correct list when created with an empty list")
        public void toModelType_validInputs_emptyList() {
            Attendance attendance = new Attendance(EMPTY_ATTENDANCE_LIST);

            JsonAdaptedAttendance adaptedAttendance = new JsonAdaptedAttendance(attendance);
            assertEquals(adaptedAttendance.toModelType(), attendance);
        }

        @Test
        @DisplayName("Returns correct list when created with a sample list")
        public void toModelType_validInputs_sampleList() {
            Attendance attendance = new Attendance(EXAMPLE_ATTENDANCE_LIST);

            JsonAdaptedAttendance adaptedAttendance = new JsonAdaptedAttendance(attendance);
            assertEquals(adaptedAttendance.toModelType(), attendance);
        }
    }

}
