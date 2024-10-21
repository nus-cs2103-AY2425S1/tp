package seedu.address.model.person;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
public class NotesTest {
    @Test
    public void equals() {
        Notes notes = new Notes("Hello");
        // same object -> returns true
        assertTrue(notes.equals(notes));
        // same values -> returns true
        Notes notesCopy = new Notes(notes.value);
        assertTrue(notes.equals(notesCopy));
        // different types -> returns false
        assertFalse(notes.equals(1));
        // null -> returns false
        assertFalse(notes.equals(null));
        // different Notes -> returns false
        Notes differentNotes = new Notes("Bye");
        assertFalse(notes.equals(differentNotes));
    }
}
