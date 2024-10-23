package seedu.ddd.model.event;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.contact.common.Id;

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
        EventId Id1String = new EventId("0");
        EventId Id1Int = new EventId(0);

        EventId Id2String = new EventId(Integer.toString(Integer.MAX_VALUE));
        EventId Id2Int = new EventId(Integer.MAX_VALUE);

        assertTrue(Id1String.equals(Id1String));
        assertTrue(Id1String.equals(Id1Int));
        assertTrue(Id2String.equals(Id2Int));
        assertFalse(Id1String.equals(Id2String));
        assertFalse(Id1String.equals(Id2Int));
        assertFalse(Id1Int.equals(Id2String));
        assertFalse(Id1Int.equals(Id2Int));
        assertFalse(Id1String.equals(null));
    }
}
