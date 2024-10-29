package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LevelTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Level(null));
    }

    @Test
    public void constructor_invalidLevel_throwsIllegalArgumentException() {
        String invalidLevel = "S7";
        assertThrows(IllegalArgumentException.class, () -> new Level(invalidLevel));
    }

    @Test
    public void isValidLevel() {
        // null level
        assertThrows(NullPointerException.class, () -> Level.isValidLevelName(null));

        // invalid level
        assertFalse(Level.isValidLevelName("")); // empty string
        assertFalse(Level.isValidLevelName(" ")); // spaces only
        assertFalse(Level.isValidLevelName("^")); // only non-alphanumeric characters
        assertFalse(Level.isValidLevelName("s!gm4")); // contains non-alphanumeric characters
        assertFalse(Level.isValidLevelName("S5 EXPRESS"));
        // valid levels
        assertTrue(Level.isValidLevelName("S1 EXPRESS"));
        assertTrue(Level.isValidLevelName("S2 EXPRESS"));
        assertTrue(Level.isValidLevelName("S3 EXPRESS"));
        assertTrue(Level.isValidLevelName("S4 EXPRESS"));

        assertTrue(Level.isValidLevelName("S2 NA"));
        assertTrue(Level.isValidLevelName("S2 NA"));
        assertTrue(Level.isValidLevelName("S3 NA"));
        assertTrue(Level.isValidLevelName("S4 NA"));

        assertTrue(Level.isValidLevelName("S1 NT"));
        assertTrue(Level.isValidLevelName("S2 NT"));
        assertTrue(Level.isValidLevelName("S3 NT"));
        assertTrue(Level.isValidLevelName("S4 NT"));

        assertTrue(Level.isValidLevelName("S1 IP"));
        assertTrue(Level.isValidLevelName("S2 IP"));
        assertTrue(Level.isValidLevelName("S3 IP"));
        assertTrue(Level.isValidLevelName("S4 IP"));

        assertTrue(Level.isValidLevelName("S5 NA"));

        //case insensitive
        assertTrue(Level.isValidLevelName("s4 express"));
        assertTrue(Level.isValidLevelName("s4 exPRess"));
        assertTrue(Level.isValidLevelName("S4 exPRess"));
        assertTrue(Level.isValidLevelName("s4 EXPRESS"));

        //ignore different spacing
        assertTrue(Level.isValidLevelName("S4    EXPRESS"));
        assertTrue(Level.isValidLevelName("S4  EXPRESS"));
    }

    @Test
    public void equals() {
        Level level = new Level("S1 EXPRESS");

        // same values -> returns true
        assertTrue(level.equals(new Level("S1 EXPRESS")));

        // same object -> returns true
        assertTrue(level.equals(level));

        // null -> returns false
        assertFalse(level.equals(null));

        // different types -> returns false
        assertFalse(level.equals(5.0f));

        // different values -> returns false
        assertFalse(level.equals(new Level("S2 EXPRESS")));

        // case-insensitive ->  returns true
        assertTrue(level.equals(new Level("s1 express")));

        // Ignore multi-spacing ->  returns true
        assertTrue(level.equals(new Level("s1   express")));
    }

    @Test
    public void toStringTest() {
        Level l = new Level("S1 EXPRESS");
        Level l2 = new Level("s1 express");
        String expected = "S1 EXPRESS";
        assertEquals(l.toString(), expected);
        assertEquals(l2.toString(), expected);
    }
}
