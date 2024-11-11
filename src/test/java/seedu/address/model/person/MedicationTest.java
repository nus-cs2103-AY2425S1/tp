package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MedicationTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Medication(null));
    }

    @Test
    public void isValidMedication() {
        // null Diagnosis
        assertThrows(NullPointerException.class, () -> Medication.isValidMedication(null));

        // invalid Medication
        assertFalse(Medication.isValidMedication("    ")); // spaces only
        assertFalse(Medication.isValidMedication("@@@@@@@@@@@")); // only certain symbols
        assertFalse(Medication.isValidMedication("123345673481928473987413897498174987132947184789234782198324798174"
                + "im getting lazy to type 80 characters so let me yap a bit more and we will be done with this"));

        // valid Medication
        assertTrue(Medication.isValidMedication("")); // empty string
        assertTrue(Medication.isValidMedication("dw()//--,,")); // allowed characters
        assertTrue(Medication.isValidMedication("abcdefg12345()//--,,")); // allowed words, numbers and characters
        assertTrue(Medication.isValidMedication("abcdefghijklmnop")); // allowed words only
        assertTrue(Medication.isValidMedication("123456789")); // allowed numbers only
        assertTrue(Medication.isValidMedication("123456789abcdefghijk")); // allowed numbers and words only
    }

    @Test
    public void isValidMedication_whitespaceHandling() {
        assertTrue(Medication.isValidMedication(" medication test ")); // leading and trailing spaces
        assertTrue(Medication.isValidMedication("medication  test")); // multiple spaces in between
    }

    @Test
    public void isValidMedication_boundaryLength() {
        assertTrue(Medication.isValidMedication("a".repeat(80))); // exactly 80 characters
        assertFalse(Medication.isValidMedication("a".repeat(81))); // 81 characters
    }

    @Test
    public void equals() {
        Medication medication = new Medication("Valid Medication");

        // same values -> returns true
        assertTrue(medication.equals(new Medication("Valid Medication")));

        // same object -> returns true
        assertTrue(medication.equals(medication));

        // null -> returns false
        assertFalse(medication.equals(null));

        // different types -> returns false
        assertFalse(medication.equals(5.0f));

        // different values -> returns false
        assertFalse(medication.equals(new Diagnosis("Other Valid Diagnosis")));
    }
}
