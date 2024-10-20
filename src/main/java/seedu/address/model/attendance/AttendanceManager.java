package seedu.address.model.attendance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manages attendance events.
 */
public class AttendanceManager {

    private final List<AttendanceEvent> attendanceEvents;

    public AttendanceManager() {
        this.attendanceEvents = new ArrayList<>();
    }

    /**
     * Adds a new attendance event.
     *
     * @param event AttendanceEvent to add.
     */
    public void addAttendanceEvent(AttendanceEvent event) {
        attendanceEvents.add(event);
    }

    /**
     * Retrieves an attendance event by name.
     *
     * @param eventName Name of the event.
     * @return Optional containing the event if found.
     */
    public Optional<AttendanceEvent> getAttendanceEvent(String eventName) {
        return attendanceEvents.stream()
                .filter(event -> event.getEventName().equals(eventName))
                .findFirst();
    }

    /**
     * Returns the list of attendance events.
     *
     * @return List of AttendanceEvent.
     */
    public List<AttendanceEvent> getAttendanceEvents() {
        return new ArrayList<>(attendanceEvents);
    }
}
