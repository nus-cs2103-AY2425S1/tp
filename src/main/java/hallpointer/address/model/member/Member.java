package hallpointer.address.model.member;

import static hallpointer.address.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import hallpointer.address.commons.util.ToStringBuilder;
import hallpointer.address.model.point.Point;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.tag.Tag;

/**
 * Represents a Member in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Member {

    // Identity fields
    private final Name name;
    private final Telegram telegram;

    // Data fields
    private final Room room;
    private final Set<Tag> tags = new HashSet<>();
    private Point totalPoints;
    private Set<Session> sessions = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Member(Name name, Telegram telegram, Room room, Set<Tag> tags) {
        requireAllNonNull(name, telegram, room, tags);

        // Validate each field using regex
        if (!name.fullName.matches("^[a-zA-Z][a-zA-Z ]{0,99}$")) {
            throw new IllegalArgumentException("Error: Invalid name format.");
        }
        if (!room.value.matches("^[0-9]+/[0-9]+/[0-9]+$")) {
            throw new IllegalArgumentException(
                "Error: Room number must be in the format <block>/<floor>/<room_number>.");
        }
        if (!telegram.value.matches("^[a-zA-Z](?:[a-zA-Z0-9]|_(?=.*[a-zA-Z0-9]$)){4,31}$")) {
            throw new IllegalArgumentException(
                "Error: Telegram handle must only contain alphanumeric characters or underscores "
                + "and be between 5 to 32 characters long.");
        }

        // Set values after validation
        this.name = name;
        this.telegram = telegram;
        this.room = room;
        this.tags.addAll(tags);
        this.totalPoints = new Point(0);
    }

    /**
     * Every field must be present and not null. Overloaded constructor to include totalPoints and sessions.
     */
    public Member(Name name, Telegram telegram, Room room, Set<Tag> tags, Point totalPoints, Set<Session> sessions) {
        requireAllNonNull(name, telegram, room, tags);

        // Validate each field using regex
        if (!name.fullName.matches("^[a-zA-Z][a-zA-Z ]{0,99}$")) {
            throw new IllegalArgumentException("Error: Invalid name format.");
        }
        if (!room.value.matches("^[0-9]+/[0-9]+/[0-9]+$")) {
            throw new IllegalArgumentException(
                "Error: Room number must be in the format <block>/<floor>/<room_number>.");
        }
        if (!telegram.value.matches("^[a-zA-Z](?:[a-zA-Z0-9]|_(?=.*[a-zA-Z0-9]$)){4,31}$")) {
            throw new IllegalArgumentException(
                "Error: Telegram handle must only contain alphanumeric characters or underscores "
                + "and be between 5 to 32 characters long.");
        }

        // Set values after validation
        this.name = name;
        this.telegram = telegram;
        this.room = room;
        this.tags.addAll(tags);
        this.totalPoints = totalPoints != null ? totalPoints : new Point(0);
        this.sessions.addAll(sessions);
    }

    public Name getName() {
        return name;
    }

    public Telegram getTelegram() {
        return telegram;
    }

    public Room getRoom() {
        return room;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Point getTotalPoints() {
        return totalPoints;
    }

    public Set<Session> getSessions() {
        return Collections.unmodifiableSet(sessions);
    }

    /**
     *  Adds the given points to the member's total points.
     *
     *  @param points Points to be added to the member.
     */
    public void addPoints(Point points) {
        requireNonNull(points);
        this.totalPoints = totalPoints.add(points);
    }

    public void subtractPoints(Point points) {
        requireNonNull(points);
        this.totalPoints = totalPoints.subtract(points);
    }

    /**
     *  Adds the given session to the member's list of sessions.
     *
     *  @param session Session to be added to the member.
     */
    public void addSession(Session session) {
        requireNonNull(session);
        this.sessions.add(session);
        addPoints(totalPoints);
    }

    /**
     * Removes the given session from the member's list of sessions.
     *
     * @param session Session to be removed from the member.
     */
    public void removeSession(Session session) {
        requireNonNull(session);
        this.sessions.remove(session);
        subtractPoints(totalPoints);
    }

    /**
     * Returns true if both members have the same name.
     * This is used to check for duplicates in the address book.
     */
    public boolean isSameMember(Member otherMember) {
        if (otherMember == this) {
            return true;
        }

        // Check for duplicate based only on name
        return otherMember != null
                && otherMember.getName().equals(getName());
    }

    /**
     * Returns true if both members have the same identity and data fields.
     * This defines a stronger notion of equality between two members.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Member)) {
            return false;
        }

        Member otherMember = (Member) other;
        return name.equals(otherMember.name)
                && telegram.equals(otherMember.telegram)
                && room.equals(otherMember.room)
                && tags.equals(otherMember.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, telegram, room, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("telegram", telegram)
                .add("room", room)
                .add("tags", tags)
                .add("totalPoints", totalPoints)
                .add("sessions", sessions)
                .toString();
    }
}
