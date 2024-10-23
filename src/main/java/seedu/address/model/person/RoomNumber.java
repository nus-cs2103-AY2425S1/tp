package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Resident's room number in the dormManager book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoomNumber(String)}
 * Note: is optional.
 */
public class RoomNumber {

    public static final String MESSAGE_CONSTRAINTS = "A string no longer than length 7 in the form of “xx-xxxx” "
            + "x are integers from “0” to “9”. E.g. “05-5053”. The first 2 integers are floor numbers and the other 4 "
            + "integers are the room number on the floor. No spaces are allowed in between each character.";


    /*
     * The first 2 integers are floor numbers,
     * the other 4 integers are the room number on the floor,
     * and no spaces are allowed in between each character.
     * Floor and Room are separated by a "-".
     */
    public static final String VALIDATION_REGEX = "\\d{2}-\\d{4}";

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
