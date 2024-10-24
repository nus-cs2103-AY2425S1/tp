package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_HIGH_PROFILE_CLIENT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidNote = "";
        assertThrows(IllegalArgumentException.class, () -> new Note(invalidNote));
    }

    @Test
    public void isValidNote() {
        // null note
        assertThrows(NullPointerException.class, () -> Note.isValidNoteName(null));

        // blank note
        assertFalse(Note.isValidNoteName("")); // empty string
        assertFalse(Note.isValidNoteName(" ")); // spaces only

        // invalid note - non-alphanumerical characters
        assertFalse(Note.isValidNoteName("High profile_client")); // underscore in local part
        assertFalse(Note.isValidNoteName("Likes.dumplings")); // period in local part
        assertFalse(Note.isValidNoteName("Prefers handshakes+meetings")); // '+' symbol in local part
        assertFalse(Note.isValidNoteName("High-importance")); // hyphen in local part

        // valid note
        assertTrue(Note.isValidNoteName("impt")); // minimal
        assertTrue(Note.isValidNoteName("Likes dumplings")); // usual
        assertTrue(Note.isValidNoteName("prefers handshakes and meetings")); // usual long
    }

    @Test
    public void equals() {
        Note note = new Note(VALID_NOTE_HIGH_PROFILE_CLIENT);

        // same values -> returns true
        assertTrue(note.equals(new Note(VALID_NOTE_HIGH_PROFILE_CLIENT)));

        // same object -> returns true
        assertTrue(note.equals(note));

        // null -> returns false
        assertFalse(note.equals(null));

        // different types -> returns false
        assertFalse(note.equals(5.0f));

        // different values -> returns false
        assertFalse(note.equals(new Note("other valid note")));
    }
}
