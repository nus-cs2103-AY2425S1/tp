package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void equals() {
        Note note = new Note("Valid Note");

        // same values -> returns true
        assertTrue(note.equals(new Note("Valid Note")));

        // same object -> returns true
        assertTrue(note.equals(note));

        // null -> returns false
        assertFalse(note.equals(null));

        // different types -> returns false
        assertFalse(note.equals(5.0f));

        // different values -> returns false
        assertFalse(note.equals(new Note("Other Valid Note")));

        // case-insensitive -> return true
        assertTrue(note.equals(new Note("valid note")));

        // ignore difference in whitespaces -> return true
        assertTrue(note.equals(new Note("Valid   Note")));
    }

    @Test
    public void hashcode_equivalents() {
        Note note = new Note("Paid for fees");

        //differnt in cases -> return true
        assertEquals(note.hashCode(), (new Note("PAID for fEEs")).hashCode());
        //different in spaces -> return true
        assertEquals(note.hashCode(), (new Note("Paid  for    fees")).hashCode());
    }
}
