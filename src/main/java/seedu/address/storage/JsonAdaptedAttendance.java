package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;

/**
 * Jackson-friendly version of {@link Attendance}.
 */
public class JsonAdaptedAttendance {
    /** Unlike in Attendance, session is represented as a simple String here */
    private final String session;

    /**
     * Constructs a {@code JsonAdaptedAttendance} with the given {@code date}.
     *
     */
    @JsonCreator
    public JsonAdaptedAttendance(String date) {
        this.session = date;
    }

    /**
     * Converts a given {@code Attendance} into JsonAdaptedAttendance for Jackson use.
     */
    public JsonAdaptedAttendance(Attendance source) {
        this.session = source.session.toString();
    }


    @JsonValue
    public String getSession() {
        return session;
    }

    /**
     * Converts this Jackson-friendly adapted attendance object into the model's {@code Attendance} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted attendance.
     */
    public Attendance toModelType() throws IllegalValueException {
        if (!Attendance.isValidDate(session)) {
            throw new IllegalValueException(Attendance.MESSAGE_CONSTRAINTS);
        }
        return new Attendance(session);
    }

}
