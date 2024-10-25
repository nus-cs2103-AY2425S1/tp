package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import seedu.address.model.person.exceptions.AttendanceNotFoundException;

/**
 * Represents a Student's Attendance.
 */
public class AttendanceList {

    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";

    private final Map<LocalDateTime, Attendance> attendanceList;

    /**
     * Initializes an empty immutable attendance list.
     */
    public AttendanceList() {
        this.attendanceList = Collections.unmodifiableMap(new TreeMap<>());
    }

    /**
     * Initializes an populated immutable attendance list.
     */
    public AttendanceList(Map<LocalDateTime, Attendance> attendanceList) {
        this.attendanceList = Collections.unmodifiableMap(attendanceList);
    }

    /**
     * Sets the attendance for a specific date.
     * If an attendance record already exists for the given date, it will be
     * replaced with the new attendance.
     *
     * @param date       The date for which the attendance is to be set.
     * @param attendance The attendance to be set for the specified date.
     * @return A new AttendanceList with the updated attendance record.
     */
    public AttendanceList setAttendance(LocalDateTime date, Attendance attendance) {
        requireNonNull(date);
        requireNonNull(attendance);
        Map<LocalDateTime, Attendance> newAttendanceList = new TreeMap<>(attendanceList);
        newAttendanceList.merge(date, attendance, (oldAttendance, newAttendance) -> newAttendance);
        return new AttendanceList(newAttendanceList);
    }

    /**
     * Removes the attendance record for the specified date.
     *
     * @param date The date of the attendance record to be removed.
     * @return A new AttendanceList with the specified attendance record removed.
     * @throws AttendanceNotFoundException If the attendance record for the specified
     *                                  date does not exist.
     */
    public AttendanceList removeAttendance(LocalDateTime date) {
        requireNonNull(date);
        if (!attendanceList.containsKey(date)) {
            throw new AttendanceNotFoundException();
        }
        Map<LocalDateTime, Attendance> newAttendanceList = new TreeMap<>(attendanceList);
        newAttendanceList.remove(date);
        return new AttendanceList(newAttendanceList);
    }

    public Attendance getAttendance(LocalDateTime date) {
        return attendanceList.get(date);
    }

    /**
     * Returns a copy of the map from {@code LocalDateTime} to {@code Attendance} in this {@code AttendanceList}
     *
     * @return A map from {@code LocalDateTime} to {@code Attendance} representing all the attendances.
     */
    public Map<LocalDateTime, Attendance> getMap() {
        return new TreeMap<>(attendanceList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AttendanceList otherAttendanceList)) {
            return false;
        }

        return this.attendanceList.equals(otherAttendanceList.attendanceList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        attendanceList.forEach((date, attendance) -> {
            sb.append(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(date));
            sb.append(" ");
            sb.append(attendance.toString());
            sb.append("\n");
        });
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
