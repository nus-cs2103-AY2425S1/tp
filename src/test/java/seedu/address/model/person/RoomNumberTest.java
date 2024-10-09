package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoomNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RoomNumber(null));
    }

    @Test
    public void constructor_invalidRoomNumber_throwsIllegalArgumentException() {
        String invalidRoomNumber = "#0101";
        assertThrows(IllegalArgumentException.class, () -> new RoomNumber(invalidRoomNumber));
    }

    @Test
    public void isValidRoomNumber() {
        // null address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses
        assertFalse(RoomNumber.isValidRoomNumber("0101")); // no # or -
        assertFalse(RoomNumber.isValidRoomNumber("#0101")); // no -
        assertFalse(RoomNumber.isValidRoomNumber("01-01")); // no #
        assertFalse(RoomNumber.isValidRoomNumber("#1-01")); // incorrect digits for floor
        assertFalse(RoomNumber.isValidRoomNumber("#001-01")); // incorrect digits for floor
        assertFalse(RoomNumber.isValidRoomNumber("#01-1")); // incorrect digits for room
        assertFalse(RoomNumber.isValidRoomNumber("#01-001")); // incorrect digits for room

        // valid addresses
        assertTrue(Address.isValidAddress("#01-01"));
        assertTrue(Address.isValidAddress("#10-10")); // one character
    }

    @Test
    public void equals() {
        RoomNumber roomNumber = new RoomNumber("Valid Room Number");

        // same values -> returns true
        assertTrue(roomNumber.equals(new RoomNumber("Valid Room Number")));

        // same object -> returns true
        assertTrue(roomNumber.equals(roomNumber));

        // null -> returns false
        assertFalse(roomNumber.equals(null));

        // different types -> returns false
        assertFalse(roomNumber.equals(5.0f));

        // different values -> returns false
        assertFalse(roomNumber.equals(new RoomNumber("Other Valid Room Number")));
    }
}
