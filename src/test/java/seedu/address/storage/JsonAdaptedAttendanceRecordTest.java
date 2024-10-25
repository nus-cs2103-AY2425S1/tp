package seedu.address.storage;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.storage.attendance.JsonAdaptedAttendance;
import seedu.address.storage.attendance.JsonAdaptedAttendanceRecord;

public class JsonAdaptedAttendanceRecordTest {
    private static final String VALID_DATE = "2023-10-01";
    private static final String VALID_STATUS = "p";
    private static final String INVALID_DATE = "invalid-date";
    private static final String INVALID_STATUS = "x";

    @Test
    public void constructor_withAttendanceRecord_success() {
        LocalDate date = LocalDate.parse(VALID_DATE);
        Attendance attendance = new Attendance(VALID_STATUS);
        AttendanceRecord attendanceRecord = new AttendanceRecord(date, attendance);

        JsonAdaptedAttendanceRecord jsonAdaptedAttendanceRecord = new JsonAdaptedAttendanceRecord(attendanceRecord);

        assertEquals(VALID_DATE, jsonAdaptedAttendanceRecord.date);
        assertEquals(VALID_STATUS, jsonAdaptedAttendanceRecord.attendance.status);
    }

    @Test
    public void toModelType_validAttendanceRecordDetails_returnsAttendanceRecord() throws Exception {
        JsonAdaptedAttendance attendance = new StubJsonAdaptedAttendance(VALID_STATUS);
        JsonAdaptedAttendanceRecord record = new JsonAdaptedAttendanceRecord(VALID_DATE, attendance);
        assertEquals(new AttendanceRecord(LocalDate.parse(VALID_DATE), new Attendance(VALID_STATUS)),
                record.toModelType());
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedAttendance attendance = new StubJsonAdaptedAttendance(VALID_STATUS);
        JsonAdaptedAttendanceRecord record = new JsonAdaptedAttendanceRecord(INVALID_DATE, attendance);
        String expectedMessage = "Invalid date format!";
        assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedAttendance attendance = new StubJsonAdaptedAttendance(VALID_STATUS);
        JsonAdaptedAttendanceRecord record = new JsonAdaptedAttendanceRecord(null, attendance);
        String expectedMessage = String.format(JsonAdaptedAttendanceRecord.MISSING_FIELD_MESSAGE_FORMAT, "date");
        assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidAttendanceStatusThrowsIllegalValueException() {
        JsonAdaptedAttendance attendance = new StubJsonAdaptedAttendance(INVALID_STATUS);
        JsonAdaptedAttendanceRecord record = new JsonAdaptedAttendanceRecord(VALID_DATE, attendance);
        String expectedMessage = Attendance.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullAttendance_throwsIllegalValueException() {
        JsonAdaptedAttendance attendance = null;
        JsonAdaptedAttendanceRecord record = new JsonAdaptedAttendanceRecord(VALID_DATE, attendance);
        String expectedMessage = String.format(JsonAdaptedAttendanceRecord.MISSING_FIELD_MESSAGE_FORMAT, "attendance");
        assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }


    public class StubJsonAdaptedAttendance extends JsonAdaptedAttendance {
        private final String status;

        public StubJsonAdaptedAttendance(String status) {
            super(status);
            this.status = status;
        }

        @Override
        public Attendance toModelType() throws IllegalValueException {
            if (status == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "status"));
            }

            if (!Attendance.isValidAttendance(status)) {
                throw new IllegalValueException(Attendance.MESSAGE_CONSTRAINTS);
            }
            return new Attendance(status);
        }
    }
}

