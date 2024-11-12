package tahub.contacts.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tahub.contacts.model.course.AttendanceSession;

@DisplayName("JsonAdaptedAttendanceSession")
public class JsonAdaptedAttendanceSessionTest {

    @Test
    @DisplayName("toModelType() returns the correct AttendanceSession after being created")
    public void toModelType_validInputs_correctAttendanceSession() {
        JsonAdaptedAttendanceSession as1 = new JsonAdaptedAttendanceSession(true);
        JsonAdaptedAttendanceSession as2 = new JsonAdaptedAttendanceSession(false);
        assertEquals(as1.toModelType(), new AttendanceSession(true));
        assertEquals(as2.toModelType(), new AttendanceSession(false));
    }
}
