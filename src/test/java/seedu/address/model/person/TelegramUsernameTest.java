package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TelegramUsernameTest {
    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new TelegramUsername(invalidName));
    }

    @Test
    public void isValidUsername_correctlyVerifiesUsernames() {
        // invalid name
        assertFalse(TelegramUsername.isValidUsername("")); // empty string
        assertFalse(TelegramUsername.isValidUsername(" ")); // spaces only
        assertFalse(TelegramUsername.isValidUsername("1234")); // length of 4 characters < 5 (minimum)
        assertFalse(TelegramUsername.isValidUsername("quickbrownfoxjumpsoverthelazygoat")); // length of 33 > 32 allowed
        assertFalse(TelegramUsername.isValidUsername("_1234")); // cannot start with an underscore
        assertFalse(TelegramUsername.isValidUsername("1234_")); // cannot end with an underscore
        assertFalse(TelegramUsername.isValidUsername("12345")); // cannot have numbers only
        assertFalse(TelegramUsername.isValidUsername("9hello")); // cannot start with a number
        assertFalse(TelegramUsername.isValidUsername("^^^^^^^")); // only non-alphanumeric characters
        assertFalse(TelegramUsername.isValidUsername("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(TelegramUsername.isValidUsername("peterjack")); // alphabets only
        assertTrue(TelegramUsername.isValidUsername("p12345")); // alphabets and numbers only
        assertTrue(TelegramUsername.isValidUsername("peter_the_2nd")); // alphanumeric characters with underscores
        assertTrue(TelegramUsername.isValidUsername("CapitalTan")); // with capital letters
        assertTrue(TelegramUsername.isValidUsername("David_Roger_Jackson_Ray_Jr_2nd")); // long names
        assertTrue(TelegramUsername.isValidUsername("quickbrownfoxjumpsoverthelazydog")); // 32 characters
    }

    @Test
    public void equals_correctlyChecksUsernameEquality() {
        TelegramUsername username = new TelegramUsername("Valid_Name");

        // same values -> returns true
        assertEquals(username, new TelegramUsername("Valid_Name"));

        // same values but different casing -> returns true
        assertEquals(username, new TelegramUsername("valid_NAME"));

        // same object -> returns true
        assertEquals(username, username);

        // null -> returns false
        assertNotEquals(null, username);

        // different types -> returns false
        assertFalse(username.equals(5.0f));

        // different values -> returns false
        assertNotEquals(username, new TelegramUsername("Other_Valid_Name"));
    }

    @Test
    public void hasUsername_returnsAsExpected() {
        TelegramUsername nullUsername = new TelegramUsername(null);
        assertFalse(nullUsername.hasUsername());
        TelegramUsername validUsername = new TelegramUsername("valid_name");
        assertTrue(validUsername.hasUsername());
    }

    @Test
    public void equals_oneNullTelegramUsername_returnsFalse() {
        TelegramUsername nullUsername = new TelegramUsername(null);
        TelegramUsername validUsername = new TelegramUsername("valid_name");

        assertFalse(validUsername.equals(nullUsername));
        assertFalse(nullUsername.equals(validUsername));
    }

    @Test
    public void equals_bothNullTelegramUsername_returnsTrue() {
        TelegramUsername nullUsername1 = new TelegramUsername(null);
        TelegramUsername nullUsername2 = new TelegramUsername(null);

        assertEquals(nullUsername1, nullUsername2);
    }

    @Test
    public void toString_correctlyConvertsToString() {
        TelegramUsername username = new TelegramUsername("Valid_Name");
        assertEquals("Valid_Name", username.toString());
    }

    @Test
    public void hashCode_nullTelegramUsername() {
        TelegramUsername username1 = new TelegramUsername(null);
        TelegramUsername username2 = new TelegramUsername(null);

        assertEquals(username1.hashCode(), 0);
        assertEquals(username1.hashCode(), username2.hashCode());
    }

    @Test
    public void hashCode_correctlyConvertsForSameUsername() {
        TelegramUsername username1 = new TelegramUsername("Valid_Name");
        TelegramUsername username2 = new TelegramUsername("Valid_Name");
        TelegramUsername username3 = new TelegramUsername("Diff_Name");
        assertTrue(username1.hashCode() == username2.hashCode());
        assertFalse(username2.hashCode() == username3.hashCode());
    }
}
