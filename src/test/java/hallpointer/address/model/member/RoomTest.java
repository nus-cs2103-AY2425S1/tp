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
    public void constructor_invalidRoom_throwsIllegalArgumentException() {
        String invalidRoom = "";
        assertThrows(IllegalArgumentException.class, () -> new Room(invalidRoom));
    }

    @Test
    public void isValidRoom() {
        // null room
        assertThrows(NullPointerException.class, () -> Room.isValidRoom(null));

        // invalid rooms
        assertFalse(Room.isValidRoom("")); // empty string
        assertFalse(Room.isValidRoom("//")); // slashes only
        assertFalse(Room.isValidRoom("-1/2/3")); // invalid number
        assertFalse(Room.isValidRoom("1/0.2/3")); // invalid decimal

        assertFalse(Room.isValidRoom("1/2/")); // missing numbers
        assertFalse(Room.isValidRoom("1/2")); // insufficient input
        assertFalse(Room.isValidRoom("3 2 3")); // no slashes

        assertFalse(Room.isValidRoom("/1/2/3/")); // too many slashes
        assertFalse(Room.isValidRoom("1/2/3/4")); // incorrect extra input
        assertFalse(Room.isValidRoom("3/2/3 0")); // also incorrect extra input


        // valid rooms
        assertTrue(Room.isValidRoom("1/2/3"));
        assertTrue(Room.isValidRoom("1/2/0")); // 0 is also acceptable here
        assertTrue(Room.isValidRoom("1000/2000/3000")); // long room numbers
        assertTrue(Room.isValidRoom("1000000000000/9/9")); // duplicate numbers
    }

    @Test
    public void equals() {
        Room room = new Room("1/2/3");

        // same values -> returns true
        assertTrue(room.equals(new Room("1/2/3")));

        // same object -> returns true
        assertTrue(room.equals(room));

        // null -> returns false
        assertFalse(room.equals(null));

        // different types -> returns false
        assertFalse(room.equals(5.0f));

        // different values -> returns false
        assertFalse(room.equals(new Room("2/3/4")));
    }
}
