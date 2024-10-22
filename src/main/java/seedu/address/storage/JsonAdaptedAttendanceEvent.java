package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.AttendanceEvent;
import seedu.address.model.person.StudentId;

/**
 * Jackson-friendly version of {@link AttendanceEvent}.
 */
class JsonAdaptedAttendanceEvent {

    private final String eventName;
    private final List<JsonAdaptedAttendanceRecord> attendanceRecords;

    /**
     * Constructs a {@code JsonAdaptedAttendanceEvent} with the given attendance event details.
     */
    @JsonCreator
    public JsonAdaptedAttendanceEvent(@JsonProperty("eventName") String eventName,
                                      @JsonProperty("attendanceRecords") List<JsonAdaptedAttendanceRecord>
                                              attendanceRecords) {
        this.eventName = eventName;
        this.attendanceRecords = attendanceRecords != null ? attendanceRecords : new ArrayList<>();
    }

    /**
     * Converts a given {@code AttendanceEvent} into this class for Jackson use.
     */
    public JsonAdaptedAttendanceEvent(AttendanceEvent source) {
        eventName = source.getEventName();
        attendanceRecords = source.getAttendanceRecords().entrySet().stream()
                .map(entry -> new JsonAdaptedAttendanceRecord(entry.getKey().value, entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted attendance event object into the model's {@code AttendanceEvent} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AttendanceEvent toModelType() throws IllegalValueException {
        if (eventName == null) {
            throw new IllegalValueException("Event name is missing!");
        }
        AttendanceEvent event = new AttendanceEvent(eventName);
        for (JsonAdaptedAttendanceRecord record : attendanceRecords) {
            String studentIdString = record.getStudentId();
            boolean isPresent = record.isPresent();

            if (studentIdString == null) {
                throw new IllegalValueException("Student ID is missing in attendance record!");
            }
            if (!StudentId.isValidStudentId(studentIdString)) {
                throw new IllegalValueException(StudentId.MESSAGE_CONSTRAINTS);
            }
            StudentId studentId = new StudentId(studentIdString);
            event.markAttendance(studentId, isPresent);
        }
        return event;
    }
}
