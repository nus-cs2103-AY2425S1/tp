package tahub.contacts.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tahub.contacts.model.course.Attendance;

/**
 * Jackson-friendly version of {@link Attendance}.
 */
public class JsonAdaptedAttendance {
    private final List<JsonAdaptedAttendanceSession> attendanceList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedAttendance} with the given attendance details.
     */
    @JsonCreator
    public JsonAdaptedAttendance(@JsonProperty("attendance") List<JsonAdaptedAttendanceSession> attendance) {
        if (attendance != null) {
            this.attendanceList.addAll(attendance);
        }
    }

    /**
     * Converts a given {@link Attendance} into this class for Jackson use.
     */
    public JsonAdaptedAttendance(Attendance source) {
        attendanceList.addAll(source.getAttendanceList().stream()
                .map(JsonAdaptedAttendanceSession::new)
                .toList());
    }

    /**
     * Converts this Jackson-friendly adapted attendance object into the model's {@link Attendance} object.
     */
    public Attendance toModelType() {
        return new Attendance(attendanceList.stream().map(JsonAdaptedAttendanceSession::toModelType).toList());
    }
}
