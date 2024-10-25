package seedu.address.storage.attendance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;

/**
 * Jackson-friendly version of {@link Attendance}.
 */
public class JsonAdaptedAttendance {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Attendance's %s field is missing!";

    public final String status;

    /**
     * Constructs a {@code JsonAdaptedAttendance} with the given attendance status.
     */
    @JsonCreator
    public JsonAdaptedAttendance(@JsonProperty("status") String status) {
        this.status = status;
    }

    /**
     * Converts a given {@code Attendance} into this class for Jackson use.
     */
    public JsonAdaptedAttendance(Attendance source) {
        status = source.value;
    }

    /**
     * Converts this Jackson-friendly adapted attendance object into the model's {@code Attendance} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted attendance.
     */
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
