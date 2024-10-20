package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteTest {
    @Test
    public void isValidString() {
        // null string
        assertThrows(NullPointerException.class, () -> Note.isValidString(null));

        // valid string
        assertTrue(() -> Note.isValidString("10mg panadol."));

        // invalid string
        assertFalse(() -> Note.isValidString("INVALID_STRING"));
    }

    @Test
    public void equals() {
        Note note = new Note();

        // same values -> returns true
        assertTrue(note.equals(new Note()));

        // same object -> returns true
        assertTrue(note.equals(note));

        // null -> returns false
        assertFalse(note.equals(null));

        // different types -> returns false
        assertFalse(note.equals(5.0f));

        // different values -> returns false
        Note testNote = new Note();
        testNote.addRemark("Test");
        assertFalse(note.equals(testNote));

        testNote = new Note();
        testNote.addMedication("Test");
        assertFalse(note.equals(testNote));

        testNote = new Note();
        testNote.addAppointment("01/01/2025 0000");
        assertFalse(note.equals(testNote));
    }
}

