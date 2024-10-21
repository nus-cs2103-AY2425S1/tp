package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Attendance;

public class JsonAdaptedAttendanceTest {
    private static final Attendance VALID_ATTENDANCE = new Attendance(true);
    private static final boolean VALID_HAS_ATTENDED = true;

    @Test
    public void toModelType_validAttendanceDetails_returnAttendance() {
        JsonAdaptedAttendance attendance = new JsonAdaptedAttendance(VALID_HAS_ATTENDED);
        assertEquals(VALID_ATTENDANCE, attendance.toModelType());
    }
}
