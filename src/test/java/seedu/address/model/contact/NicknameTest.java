package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Nickname;

public class NicknameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Nickname(null));
    }

    @Test
    public void constructor_trim_nickname() {
        // nicknames with only with white spaces
        assertEquals(new Nickname("").value, "");
        assertEquals(new Nickname(" ").value, "");
        assertEquals(new Nickname("  ").value, "");

        // nicknames with preceding white spaces
        assertEquals(new Nickname(" aa").value, "aa");
        assertEquals(new Nickname("    b bc").value, "b bc");

        // nicknames with trimming white spaces
        assertEquals(new Nickname("jackson ").value, "jackson");
        assertEquals(new Nickname("jackson  ").value, "jackson");
        assertEquals(new Nickname("jackson goh  ").value, "jackson goh");

        // nicknames with both preceding and trimming white spaces
        assertEquals(new Nickname(" jackson ").value, "jackson");
        assertEquals(new Nickname("  jackson ").value, "jackson");
        assertEquals(new Nickname("  jackson goh  ").value, "jackson goh");
    }

    @Test
    public void isEmpty() {
        // empty nickname
        assertTrue(new Nickname("").isEmpty());
        assertTrue(new Nickname(" ").isEmpty());
        assertTrue(new Nickname("  ").isEmpty());

        // non-empty nickname
        assertFalse(new Nickname("a").isEmpty());
        assertFalse(new Nickname(" a  ").isEmpty());
        assertFalse(new Nickname("a ").isEmpty());
    }

    @Test
    public void equals() {
        Nickname nickname = new Nickname("john ");

        // same values -> returns true
        assertTrue(nickname.equals(new Nickname("john")));

        // same object -> returns true
        assertTrue(nickname.equals(nickname));

        // null -> returns false
        assertFalse(nickname.equals(null));

        // different types -> returns false
        assertFalse(nickname.equals(5.0f));

        // different values -> returns false
        assertFalse(nickname.equals(new Nickname("peter")));
    }
}
