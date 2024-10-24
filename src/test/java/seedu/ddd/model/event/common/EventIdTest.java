package seedu.ddd.model.event.common;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EventIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventId(null));
    }

    //Assumption: The null input have been filtered out.
    @Test
    public void constructorTest() {
        String invalidInput1 = "";
        String invalidInput2 = "A string instead of a number";
        int invalidInput3 = -1;
        int invalidInput4 = Integer.MIN_VALUE;
        String invalidInput5 = "-1";
        String invalidInput6 = Integer.toString(Integer.MIN_VALUE);

        int validInput1 = 0;
        int validInput2 = Integer.MAX_VALUE;
        String validInput3 = "0";
        String validInput4 = Integer.toString(Integer.MAX_VALUE);

        assertThrows(java.lang.IllegalArgumentException.class, () -> new EventId(invalidInput1));
        assertThrows(java.lang.IllegalArgumentException.class, () -> new EventId(invalidInput2));
        assertThrows(java.lang.IllegalArgumentException.class, () -> new EventId(invalidInput3));
        assertThrows(java.lang.IllegalArgumentException.class, () -> new EventId(invalidInput4));
        assertThrows(java.lang.IllegalArgumentException.class, () -> new EventId(invalidInput5));
        assertThrows(java.lang.IllegalArgumentException.class, () -> new EventId(invalidInput6));

        assertDoesNotThrow(() -> new EventId(validInput1));
        assertDoesNotThrow(() -> new EventId(validInput2));
        assertDoesNotThrow(() -> new EventId(validInput3));
        assertDoesNotThrow(() -> new EventId(validInput4));
    }

    //Assumption: The EventId object contains a valid Id.
    @Test
    public void equalsTest() {
        EventId id1String = new EventId("0");
        EventId id1Int = new EventId(0);

        EventId id2String = new EventId(Integer.toString(Integer.MAX_VALUE));
        EventId id2Int = new EventId(Integer.MAX_VALUE);

        assertTrue(id1String.equals(id1String));
        assertTrue(id1String.equals(id1Int));
        assertTrue(id2String.equals(id2Int));
        assertFalse(id1String.equals(id2String));
        assertFalse(id1String.equals(id2Int));
        assertFalse(id1Int.equals(id2String));
        assertFalse(id1Int.equals(id2Int));
        assertFalse(id1String.equals(null));
    }
}
