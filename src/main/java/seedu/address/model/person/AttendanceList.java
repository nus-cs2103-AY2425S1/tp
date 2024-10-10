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
     * Initializes an empty attendance list.
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
     * @param index The index of the attendance record to be removed.
     */
    public void removeAttendance(int index) {
        // TODO: handle error for invalid index
        attendanceList.remove(index);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AttendanceList al) {
            if (al.attendanceList.size() != this.attendanceList.size()) {
                return false;
            }
            for (int i = 0; i < this.attendanceList.size(); i++) {
                if (!al.attendanceList.get(i).equals(this.attendanceList.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
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
