package tahub.contacts.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tahub.contacts.model.course.AttendanceSession;

/**
 * Jackson-friendly version of {@link AttendanceSession}.
 */
public class JsonAdaptedAttendanceSession {
    private final boolean isSessionAttended;
    /**
     * Constructs a {@code JsonAdaptedAttendanceSession} with the given attendance details.
     */
    @JsonCreator
    public JsonAdaptedAttendanceSession(@JsonProperty("isSessionAttended") Boolean isSessionAttended) {
        this.isSessionAttended = isSessionAttended;
    }

    /**
     * Converts a given {@link AttendanceSession} into this class for Jackson use.
     */
    public JsonAdaptedAttendanceSession(AttendanceSession source) {
        isSessionAttended = source.getIsSessionAttended();
    }

    /**
     * Converts this Jackson-friendly adapted attendance object into the model's {@link AttendanceSession} object.
     */
    public AttendanceSession toModelType() {
        return new AttendanceSession(isSessionAttended);
    }
}
