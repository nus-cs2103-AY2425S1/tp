package hallpointer.address.model.session;

import static hallpointer.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import hallpointer.address.commons.util.ToStringBuilder;
import hallpointer.address.model.member.Member;

/**
 * Represents a Session in the system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Session {

    // Identity fields
    private final SessionName sessionName;
    private final int points;
    private final Date date;

    // Data fields
    private final Set<Member> members = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Session(SessionName sessionName, Date date, int points, Set<Member> members) {
        requireAllNonNull(sessionName, date, points, members);
        this.sessionName = sessionName;
        this.date = date;
        this.points = points;
        this.members.addAll(members);
    }

    public SessionName getSessionName() {
        return sessionName;
    }

    public int getPoints() {
        return points;
    }

    /**
     * Returns an immutable member set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Member> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    /**
     * Updates the set of members in the session.
     *
     * @param newMembers New set of members.
     */
    public Session updateMembers(Set<Member> newMembers) {
        return new Session(this.sessionName, this.date, this.points, newMembers);
    }

    /**
     * Returns true if both sessions have the same name.
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
                && points == otherSession.points
                && members.equals(otherSession.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionName, points, members);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("sessionName", sessionName)
                .add("points", points)
                .add("members", members)
                .toString();
    }

    public Object getDate() {
        return this.date;
    }
}
