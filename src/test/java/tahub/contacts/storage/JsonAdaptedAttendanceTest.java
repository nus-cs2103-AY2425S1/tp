package tahub.contacts.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tahub.contacts.testutil.AttendanceExamples.EMPTY_ATTENDANCE_LIST;
import static tahub.contacts.testutil.AttendanceExamples.EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import tahub.contacts.model.course.Attendance;

@DisplayName("JsonAdaptedAttendance")
public class JsonAdaptedAttendanceTest {
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
            Attendance attendance = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);

            JsonAdaptedAttendance adaptedAttendance = new JsonAdaptedAttendance(attendance);
            assertEquals(adaptedAttendance.toModelType(), attendance);
        }
    }

}
