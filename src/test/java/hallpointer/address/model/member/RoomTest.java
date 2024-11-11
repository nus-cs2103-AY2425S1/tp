package hallpointer.address.model.member;

import static hallpointer.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertFalse(Room.isValidRoom("--")); // dashes only
        assertFalse(Room.isValidRoom("-1-2-3")); // invalid number
        assertFalse(Room.isValidRoom("1-0.2-3")); // invalid decimal
        assertFalse(Room.isValidRoom("1-0.-3")); // invalid decimal point
        assertFalse(Room.isValidRoom("+1-02-3")); // invalid symbol

        assertFalse(Room.isValidRoom("1-2-")); // missing numbers
        assertFalse(Room.isValidRoom("1--3")); // missing numbers
        assertFalse(Room.isValidRoom("-2-3")); // missing numbers
        assertFalse(Room.isValidRoom("1-2")); // insufficient input
        assertFalse(Room.isValidRoom("3 2 3")); // no dashes

        assertFalse(Room.isValidRoom("-1-2-3")); // too many dashes
        assertFalse(Room.isValidRoom("1-2-3-4")); // incorrect extra input
        assertFalse(Room.isValidRoom("3-2-3 0")); // also incorrect extra input

        // valid rooms
        assertTrue(Room.isValidRoom("1-2-3"));
        assertTrue(Room.isValidRoom("1-2-0")); // 0 is also acceptable here
        assertTrue(Room.isValidRoom("001-00002-0")); // zero padding is fine
        assertTrue(Room.isValidRoom("1000-2000-3000")); // long room numbers
        assertTrue(Room.isValidRoom("9-9-9")); // duplicate numbers
    }

    @Test
    public void equals() {
        Room room = new Room("10-2-3");

        // same values -> returns true
        assertTrue(room.equals(new Room("10-2-3")));

        // zero padding removal -> returns true
        assertTrue(room.equals(new Room("10-02-3")));
        assertTrue(room.equals(new Room("0010-02-3")));
        assertTrue(room.equals(new Room("10-02-0003")));

        // not zero padding -> returns false
        assertFalse(room.equals(new Room("1-2-3")));

        // same object -> returns true
        assertTrue(room.equals(room));

        // null -> returns false
        assertFalse(room.equals(null));

        // different types -> returns false
        assertFalse(room.equals(5.0f));

        // different values -> returns false
        assertFalse(room.equals(new Room("2-3-4")));
    }

    // Needed only because of the zero-padding removal, and so the test cases are set accordingly
    @Test
    public void toStringMethod() {
        Room room = new Room("34-567-89");
        // Zero padding
        assertEquals(room.toString(), new Room("034-567-89").toString());
        assertEquals(room.toString(), new Room("34-00567-89").toString());
        assertEquals(room.toString(), new Room("34-567-00089").toString());
        assertEquals(room.toString(), new Room("0034-0567-0089").toString());

        // Not zero padding
        assertFalse(new Room("10-1-1").toString().equals(new Room("1-1-1").toString()));
        assertFalse(new Room("1-100-1").toString().equals(new Room("1-1-1").toString()));
        assertFalse(new Room("1-1-10").toString().equals(new Room("1-1-1").toString()));
        assertFalse(new Room("1000-100-10").toString().equals(new Room("1-1-1").toString()));

        // Both at the same time
        assertTrue(new Room("010-1-1").toString().equals(new Room("10-1-1").toString()));
        assertFalse(new Room("01-001-1").toString().equals(new Room("10-01-1").toString()));
    }
}
