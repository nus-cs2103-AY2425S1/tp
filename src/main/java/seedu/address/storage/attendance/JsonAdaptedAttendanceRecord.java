package seedu.address.storage.attendance;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceRecord;

/**
 * Jackson-friendly version of {@link AttendanceRecord}.
 */
public class JsonAdaptedAttendanceRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "AttendanceRecord's %s field is missing!";

    public final String date;
    public final JsonAdaptedAttendance attendance;

    /**
     * Constructs a {@code JsonAdaptedAttendanceRecord} with the given attendance record details.
     */
    @JsonCreator
    public JsonAdaptedAttendanceRecord(@JsonProperty("date") String date,
                                       @JsonProperty("attendance") JsonAdaptedAttendance attendance) {
        this.date = date;
        this.attendance = attendance;
    }

    /**
     * Converts a given {@code AttendanceRecord} into this class for Jackson use.
     */
    public JsonAdaptedAttendanceRecord(AttendanceRecord source) {
        date = source.getDate().toString();
        attendance = new JsonAdaptedAttendance(source.getAttendance());
    }

    /**
     * Converts this Jackson-friendly adapted attendance record object into the model's {@code AttendanceRecord} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted attendance record.
     */
    public AttendanceRecord toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Invalid date format!");
        }
        final LocalDate modelDate = LocalDate.parse(date);

        if (attendance == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "attendance"));
        }
        if (!Attendance.isValidAttendance(attendance.status)) {
            throw new IllegalValueException(Attendance.MESSAGE_CONSTRAINTS);
        }
        final Attendance modelAttendance = attendance.toModelType();
        return new AttendanceRecord(modelDate, modelAttendance);
    }
}
