package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Student's Attendance.
 */
public class AttendanceList {
    private List<Attendance> attendanceList;

    /**
     * Represents an Attendance record for a person.
     * Each Attendance object stores whether the person has attended and the date of attendance.
     *
     * @param hasAttended A Boolean indicating if the person has attended.
     * @param date The date and time of the attendance.
     * @throws NullPointerException if {@code hasAttended} or {@code date} is null.
     */
    public AttendanceList() {
        this.attendanceList = new ArrayList<Attendance>();
    }

    /**
     * Adds an attendance record to the attendance list.
     *
     * @param attendance The attendance record to be added. Must not be null.
     */
    public void addAttendance(Attendance attendance) {
        requireNonNull(attendance);
        attendanceList.add(attendance);
    }

    /**
     * Removes the specified attendance from the attendance list.
     *
     * @param attendance The attendance to be removed. Must not be null.
     * @throws NullPointerException if the specified attendance is null.
     */
    public void removeAttendance(Attendance attendance) throws NullPointerException {
        requireNonNull(attendance);
        attendanceList.remove(attendance);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Attendance attendance : attendanceList) {
            sb.append(attendance.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
