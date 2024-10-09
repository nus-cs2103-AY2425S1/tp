package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Resident's room number in the dormManager book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoomNumber(String)}
 */
public class RoomNumber {

    public static final int MIN_FLOOR_DIGITS = 2;
    public static final int MAX_FLOOR_DIGITS = 2;
    public static final int MIN_ROOM_DIGITS = 2;
    public static final int MAX_ROOM_DIGITS = 2;

    public static final String MESSAGE_CONSTRAINTS = "Room Numbers should be in the form \"#Floor-Room\", and it should not be blank. Eg #01-01";

    /*
     * The first character of the address must be a "#",
     * the floor and room numbers must be separated with a "-"
     */
    public static final String VALIDATION_REGEX = String.format("#\\d{%d,%d}-\\d{%d,%d}",
            MIN_FLOOR_DIGITS, MAX_FLOOR_DIGITS, MIN_ROOM_DIGITS, MAX_ROOM_DIGITS);

    public final String value;

    /**
     * Constructs an {@code RoomNumber}.
     *
     * @param roomNumber A valid roomNumber.
     */
    public RoomNumber(String roomNumber) {
        requireNonNull(roomNumber);
        checkArgument(isValidRoomNumber(roomNumber), MESSAGE_CONSTRAINTS);
        value = roomNumber;
    }

    /**
     * Returns true if a given string is a valid room number.
     */
    public static boolean isValidRoomNumber(String test) {
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
        if (!(other instanceof RoomNumber)) {
            return false;
        }

        RoomNumber otherRoomNumber = (RoomNumber) other;
        return value.equals(otherRoomNumber.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
