package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class TriageTest {

    @Test
    public void constructor_nullTriage_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Triage(null));
    }

    @Test
    public void constructor_validTriage_success() {
        Triage triage = new Triage("3");
        assertEquals("3", triage.value);
    }

    @Test
    public void isEmpty_nonNullValue_returnsFalse() {
        Triage triage = new Triage("3");
        assertFalse(triage.isEmpty());
    }

    @Test
    public void isValidTriage_validValues_returnsTrue() throws ParseException {
        assertTrue(Triage.isValidTriage("1"));
        assertTrue(Triage.isValidTriage("3"));
        assertTrue(Triage.isValidTriage("5"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Triage triage = new Triage("2");
        assertTrue(triage.equals(triage));
    }

    @Test
    public void equals_differentObjectsSameValue_returnsTrue() {
        Triage triage1 = new Triage("3");
        Triage triage2 = new Triage("3");
        assertTrue(triage1.equals(triage2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Triage triage1 = new Triage("3");
        Triage triage2 = new Triage("4");
        assertFalse(triage1.equals(triage2));
    }

    @Test
    public void equals_differentObjectType_returnsFalse() {
        Triage triage = new Triage("3");
        String other = "3";
        assertFalse(triage.equals(other));
    }

    @Test
    public void hashCode_sameValue_returnsSameHashCode() {
        Triage triage1 = new Triage("2");
        Triage triage2 = new Triage("2");
        assertEquals(triage1.hashCode(), triage2.hashCode());
    }

    @Test
    public void toString_returnsCorrectStringRepresentation() {
        Triage triage = new Triage("4");
        assertEquals("4", triage.toString());
    }

    @Test
    void isValidTriage_validValues_returnTrue() {
        // Test valid triage values in range 1 to 5
        assertTrue(Triage.isValidTriage("1"));
        assertTrue(Triage.isValidTriage("2"));
        assertTrue(Triage.isValidTriage("3"));
        assertTrue(Triage.isValidTriage("4"));
        assertTrue(Triage.isValidTriage("5"));
    }

    @Test
    void isValidTriage_invalidValues_returnFalse() {
        // Test invalid triage values (out of range, non-integer, empty string, negative numbers)
        assertFalse(Triage.isValidTriage("0"));
        assertFalse(Triage.isValidTriage("6"));
        assertFalse(Triage.isValidTriage("-1"));
        assertFalse(Triage.isValidTriage("a"));
        assertFalse(Triage.isValidTriage("1.5"));
        assertFalse(Triage.isValidTriage(""));
        assertFalse(Triage.isValidTriage(" "));
    }

    @Test
    void isValidTriage_nullValue_throwException() {
        // Test null input
        assertFalse(Triage.isValidTriage(null));
    }
}
