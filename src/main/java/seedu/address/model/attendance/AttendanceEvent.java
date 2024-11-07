package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import seedu.address.model.person.StudentId;

/**
 * Represents an attendance event.
 */
public class AttendanceEvent {
    private final String eventName;
    private final Map<StudentId, Boolean> attendanceRecords;

    /**
     * Constructs an AttendanceEvent with the given name.
     *
     * @param eventName Name of the attendance event.
     */
    public AttendanceEvent(String eventName) {
        requireNonNull(eventName);
        this.eventName = eventName;
        this.attendanceRecords = new HashMap<>();
    }

    public String getEventName() {
        return eventName;
    }

    /**
     * Checks if this event is the same as another event (by name).
     */
    public boolean isSameEvent(AttendanceEvent otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null && otherEvent.getEventName().equalsIgnoreCase(eventName);
    }

    /**
     * Marks a student's attendance.
     *
     * @param studentId Student's ID.
     * @param isPresent True if present, false if absent.
     */
    public void markAttendance(StudentId studentId, boolean isPresent) {
        requireNonNull(studentId);
        attendanceRecords.put(studentId, isPresent);
    }

    /**
     * Gets the attendance status of a student.
     *
     * @param studentId Student's ID.
     * @return True if present, false if absent or not marked.
     */
    public boolean isStudentPresent(StudentId studentId) {
        requireNonNull(studentId);
        return attendanceRecords.getOrDefault(studentId, false);
    }

    /**
     * Returns the attendance records.
     *
     * @return Map of StudentId to attendance status.
     */
    public Map<StudentId, Boolean> getAttendanceRecords() {
        return new HashMap<>(attendanceRecords);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceEvent)) {
            return false;
        }

        AttendanceEvent that = (AttendanceEvent) o;
        return eventName.equalsIgnoreCase(that.eventName) && attendanceRecords.equals(that.attendanceRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("AttendanceEvent{name='%s'}", eventName);
    }
}
