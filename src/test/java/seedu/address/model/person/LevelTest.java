package seedu.address.model.person;

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

        // valid levels
        assertTrue(Level.isValidLevelName("K1"));
        assertTrue(Level.isValidLevelName("K2"));
        assertTrue(Level.isValidLevelName("P1"));
        assertTrue(Level.isValidLevelName("P2"));
        assertTrue(Level.isValidLevelName("P3"));
        assertTrue(Level.isValidLevelName("P4"));
        assertTrue(Level.isValidLevelName("P5"));
        assertTrue(Level.isValidLevelName("P6"));
        assertTrue(Level.isValidLevelName("S1"));
        assertTrue(Level.isValidLevelName("S2"));
        assertTrue(Level.isValidLevelName("S3"));
        assertTrue(Level.isValidLevelName("S4"));
        assertTrue(Level.isValidLevelName("S5"));
        assertTrue(Level.isValidLevelName("JC1"));
        assertTrue(Level.isValidLevelName("JC2"));
    }

    @Test
    public void equals() {
        Level level = new Level("JC1");

        // same values -> returns true
        assertTrue(level.equals(new Level("JC1")));

        // same object -> returns true
        assertTrue(level.equals(level));

        // null -> returns false
        assertFalse(level.equals(null));

        // different types -> returns false
        assertFalse(level.equals(5.0f));

        // different values -> returns false
        assertFalse(level.equals(new Level("JC2")));
    }
}
