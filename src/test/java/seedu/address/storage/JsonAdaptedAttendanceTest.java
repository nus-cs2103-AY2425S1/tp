package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Attendance;

public class JsonAdaptedAttendanceTest {

    @Test
    public void toModelType_invalidAttendance_throwsIllegalValueException() throws Exception {
        JsonAdaptedAttendance jsonAdaptedAttendance = new JsonAdaptedAttendance("1/1");
        String expectedMessage = Attendance.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedAttendance::toModelType);
    }
}
