package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;

public class JsonAdaptedAttendance {
    private final String session; // tbd

    @JsonCreator
    public JsonAdaptedAttendance(String date) {
        this.session = date;
    }

    public JsonAdaptedAttendance(Attendance source) {
        this.session = source.session.toString();
    }

    @JsonValue
    public String getSession() {
        return session;
    }

    public Attendance toModelType() throws IllegalValueException {
        if (!Attendance.isValidDate(session)) {
            throw new IllegalValueException(Attendance.MESSAGE_CONSTRAINTS);
        }
        return new Attendance(session);
    }

}
