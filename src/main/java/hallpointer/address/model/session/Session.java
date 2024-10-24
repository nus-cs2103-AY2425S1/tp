package hallpointer.address.model.session;

import static hallpointer.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import hallpointer.address.commons.util.ToStringBuilder;
import hallpointer.address.model.point.Point;

/**
 * Represents a Session in the system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Session {

    // Identity fields
    private final SessionName sessionName;
    private final SessionDate date;
    private final Point points;

    /**
     * Every field must be present and not null.
     */
    public Session(SessionName sessionName, SessionDate date, Point points) {
        requireAllNonNull(sessionName, date, points);
        this.sessionName = sessionName;
        this.date = date;
        this.points = points;
    }

    public SessionName getSessionName() {
        return sessionName;
    }

    public Point getPoints() {
        return points;
    }

    /**
     * Returns the date of the session.
     */
    public SessionDate getDate() {
        return date;
    }

    /**
     * Returns true if both sessions have the same name (not case-sensitive).
     * This defines a weaker notion of equality between two sessions.
     */
    public boolean isSameSession(Session otherSession) {
        if (otherSession == this) {
            return true;
        }

        return otherSession != null
                && otherSession.getSessionName().equals(getSessionName());
    }

    /**
     * Returns true if both sessions have the same identity and data fields.
     * This defines a stronger notion of equality between two sessions.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Session)) {
            return false;
        }

        Session otherSession = (Session) other;
        return sessionName.equals(otherSession.sessionName)
                && date.equals(otherSession.date)
                && points.equals(otherSession.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionName, date, points);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("sessionName", sessionName)
                .add("date", date)
                .add("points", points)
                .toString();
    }

}
