package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;
import seedu.address.storage.attendance.JsonAdaptedAttendance;

public class JsonAdaptedAttendanceTest {
    private static final String INVALID_ATTENDANCE = "x";
    private static final String VALID_ATTENDANCE = "p";

    @Test
    public void toModelType_validAttendanceDetails_returnsAttendance() throws Exception {
        JsonAdaptedAttendance attendance = new JsonAdaptedAttendance(VALID_ATTENDANCE);
        assertEquals(new Attendance(VALID_ATTENDANCE), attendance.toModelType());
    }

    @Test
    public void toModelType_invalidAttendance_throwsIllegalValueException() {
        JsonAdaptedAttendance attendance = new JsonAdaptedAttendance(INVALID_ATTENDANCE);
        String expectedMessage = Attendance.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, attendance::toModelType);
    }

    @Test
    public void toModelType_nullAttendance_throwsIllegalValueException() {
        JsonAdaptedAttendance attendance = new JsonAdaptedAttendance((String) null);
        String expectedMessage = String.format(JsonAdaptedAttendance.MISSING_FIELD_MESSAGE_FORMAT, "status");
        assertThrows(IllegalValueException.class, expectedMessage, attendance::toModelType);
    }

    @Test
    public void constructor_validAttendance_createsJsonAdaptedAttendance() throws IllegalValueException {
        Attendance validAttendance = new Attendance(VALID_ATTENDANCE);
        JsonAdaptedAttendance jsonAdaptedAttendance = new JsonAdaptedAttendance(validAttendance);
        assertEquals(validAttendance, jsonAdaptedAttendance.toModelType());
    }
}
