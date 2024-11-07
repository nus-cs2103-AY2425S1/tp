package tahub.contacts.model.course;

/**
 * An immutable object that represents a session that a student attends.
 */
public class AttendanceSession {
    private final boolean isSessionAttended;

    /**
     * Creates a session with the specified {@code sessionAttendance}
     *
     * @param isSessionAttended whether this session was attended
     */
    public AttendanceSession(boolean isSessionAttended) {
        this.isSessionAttended = isSessionAttended;
    }

    /**
     * Creates a new {@link AttendanceSession} that is marked as attended.
     *
     * @return a new {@link AttendanceSession}.
     */
    public static AttendanceSession createAttended() {
        return new AttendanceSession(true);
    }

    /**
     * Creates a new {@link AttendanceSession} that is marked as absent.
     *
     * @return a new {@link AttendanceSession}.
     */
    public static AttendanceSession createAbsent() {
        return new AttendanceSession(false);
    }

    /**
     * Gets whether this {@link AttendanceSession} was attended.
     *
     * @return whether this {@link AttendanceSession} was attended.
     */
    public boolean getIsSessionAttended() {
        return isSessionAttended;
    }

    /**
     * Compares this {@link AttendanceSession} object to another object for strict equality.
     *
     * @param other Object to be compared against.
     * @return {@code true} if the other object is another {@link AttendanceSession} object with the same session
     *      attendance.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AttendanceSession otherAttendanceSession)) {
            return false;
        }

        return isSessionAttended == otherAttendanceSession.isSessionAttended;
    }

    @Override
    public int hashCode() {
        return isSessionAttended ? 1 : 0;
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return isSessionAttended ? "V" : "X";
    }
}
