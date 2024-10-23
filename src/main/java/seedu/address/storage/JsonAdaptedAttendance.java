package seedu.address.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Attendance;

/**
 * Jackson-friendly version of {@link Attendance}.
 * This saves LocalDate as {@link Attendance#toString}
 */
public class JsonAdaptedAttendance {
    private final String attendanceDate;

    /**
     * Constructs a {@code JsonAdaptedAttendance} with the given attendance details.
     */
    @JsonCreator
    public JsonAdaptedAttendance(@JsonProperty("attendanceDate") String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    /**
     * Converts a given {@code Attendance} into this class for Jackson use.
     */
    public JsonAdaptedAttendance(Attendance source) {
        attendanceDate = source.toString();
    }

    /**
     * Converts this Jackson-friendly adapted attendance object into the model's {@code Attendance} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted attendance.
     */
    public Attendance toModelType() throws IllegalValueException {
        if (!Attendance.isValidAttendance(attendanceDate)) {
            throw new IllegalValueException(Attendance.MESSAGE_CONSTRAINTS);
        }
        //TODO use method from Attendance to parse
        return new Attendance(LocalDate.parse(attendanceDate, Attendance.VALID_DATE_FORMAT));
    }
}
