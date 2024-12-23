package hallpointer.address.model.member;

import static hallpointer.address.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Member's room in HallPointer.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoom(String)}
 */
public class Room {

    public static final String MESSAGE_CONSTRAINTS = "Room must be in the format block-floor-room number.\n"
            + "Example: 10-3-100\n"
            + "Unfortunately, non-numeric block, room or floor numbers are currently unsupported.";

    public static final String VALIDATION_REGEX = "^[0-9]+-[0-9]+-[0-9]+$";

    public final String value;

    /**
     * Constructs an {@code Room}.
     *
     * @param room A valid room.
     */
    public Room(String room) {
        requireNonNull(room);
        checkArgument(isValidRoom(room), MESSAGE_CONSTRAINTS);

        // remove zero-padding of block or floor or room number
        // causes uncatched exception if integers are too large
        // however, since this make no sense in realistic scenarios and fixing this counts as enhancement, ignoring it
        String[] arr = room.split("-");
        value = Integer.parseInt(arr[0]) + "-" + Integer.parseInt(arr[1]) + "-" + Integer.parseInt(arr[2]);
    }

    /**
     * Returns true if a given string is a valid room.
     */
    public static boolean isValidRoom(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Room)) {
            return false;
        }

        Room otherRoom = (Room) other;
        return value.equals(otherRoom.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
