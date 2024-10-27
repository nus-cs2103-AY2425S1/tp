package seedu.address.model.attendance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 * Represents an attendance record for a single date.
 */
public class AttendanceRecord implements Observable {
    private final LocalDate date;
    private Attendance attendance;
    private final List<InvalidationListener> listeners = new ArrayList<>();

    /**
     * Creates an AttendanceRecord with the specified date and attendance.
     * @param date The date of the attendance record.
     * @param attendance The attendance status (present/absent).
     */
    public AttendanceRecord(LocalDate date, Attendance attendance) {
        this.date = date;
        this.attendance = attendance;
        notifyListeners();
    }

    public LocalDate getDate() {
        return date;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
        notifyListeners();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (InvalidationListener listener : listeners) {
            listener.invalidated(this);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AttendanceRecord)) {
            return false;
        }
        AttendanceRecord otherRecord = (AttendanceRecord) other;
        return otherRecord.getDate().equals(getDate())
                && otherRecord.getAttendance().equals(getAttendance());
    }

    @Override
    public int hashCode() {
        return date.hashCode() + attendance.hashCode();
    }

    @Override
    public String toString() {
        return date + ": " + attendance;
    }

}
