package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.Appointment;

public class NoteTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Set<Appointment> appointmentSet = new HashSet<>();
        Set<String> testSet = new HashSet<>();
        assertThrows(NullPointerException.class, () -> new Note(null, testSet, testSet));
        assertThrows(NullPointerException.class, () -> new Note(appointmentSet, null, testSet));
        assertThrows(NullPointerException.class, () -> new Note(appointmentSet, testSet, null));
    }

    @Test
    public void constructor_success() {
        Set<Appointment> appointmentSet = new HashSet<>();
        Set<String> testSet = new HashSet<>();
        Note note = new Note(appointmentSet, testSet, testSet);
        assertNotNull(note);
    }

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
    public void isValidAppointment() {
        // null string
        assertThrows(NullPointerException.class, () -> Note.isValidAppointment(null));

        // valid string
        assertTrue(() -> Note.isValidAppointment(new Appointment("12/12/2023 1200")));

        // invalid string
        assertFalse(() -> Note.isValidAppointment(new Appointment("31/12/2099 0000")));
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
        testNote.addAppointment("01/01/2023 0000");
        assertFalse(note.equals(testNote));
    }

    @Test
    public void toString_test() {
        Set<Appointment> appointmentSet = new HashSet<>();
        appointmentSet.add(new Appointment("01/01/2025 0000"));
        Set<String> remarksSet = new HashSet<>();
        remarksSet.add("Test remark");
        Set<String> medicationSet = new HashSet<>();
        medicationSet.add("Test medications");

        Note note = new Note(appointmentSet, remarksSet, medicationSet);

        String expectedString = "Previous Appointments: [01/01/2025 0000]\n"
                                + "Medications: Test medications\n"
                                + "Remarks: Test remark";

        assertEquals(expectedString, note.toString());
    }
}

