package hallpointer.address.model.member;

import static hallpointer.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RoomTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Room(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Room(invalidAddress));
    }

    @Test
    public void isValidRoom() {
        // null address
        assertThrows(NullPointerException.class, () -> Room.isValidRoom(null));

        // invalid addresses
        assertFalse(Room.isValidRoom("")); // empty string
        assertFalse(Room.isValidRoom(" ")); // spaces only

        // valid addresses
        assertTrue(Room.isValidRoom("Blk 456, Den Road, #01-355"));
        assertTrue(Room.isValidRoom("-")); // one character
        assertTrue(Room.isValidRoom("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    @Test
    public void equals() {
        Room room = new Room("Valid Room");

        // same values -> returns true
        assertTrue(room.equals(new Room("Valid Room")));

        // same object -> returns true
        assertTrue(room.equals(room));

        // null -> returns false
        assertFalse(room.equals(null));

        // different types -> returns false
        assertFalse(room.equals(5.0f));

        // different values -> returns false
        assertFalse(room.equals(new Room("Other Valid Room")));
    }
}
