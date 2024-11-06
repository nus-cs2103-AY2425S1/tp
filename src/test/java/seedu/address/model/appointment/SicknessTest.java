package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class SicknessTest {

    @Test
    void isValidSickness_validSicknessNames_returnsTrue() {

        // EP: Valid names
        assertTrue(Sickness.isValidSickness("Common cold"));
        assertTrue(Sickness.isValidSickness("Flu"));
        assertTrue(Sickness.isValidSickness("COVID-19"));
        assertTrue(Sickness.isValidSickness("Migraine"));
        assertTrue(Sickness.isValidSickness("A"));
    }

    @Test
    void isValidSickness_invalidSicknessNames_returnsFalse() {
        // EP: empty string
        assertFalse(Sickness.isValidSickness(""));

        // EP: whitespaces only
        assertFalse(Sickness.isValidSickness(" "));

        // EP: leading/trailing whitespaces
        assertFalse(Sickness.isValidSickness(" Headache"));
        assertFalse(Sickness.isValidSickness(" 1"));

        // EP: invalid characters
        assertFalse(Sickness.isValidSickness("1"));
        assertFalse(Sickness.isValidSickness("!"));
    }

    @Test
    void constructor_validSicknessName_createsSickness() {
        Sickness sickness = new Sickness("Fever");
        assertEquals("Fever", sickness.value);
    }

    @Test
    void constructor_invalidSicknessName_throwsIllegalArgumentException() {
        // EP: empty string
        assertThrows(IllegalArgumentException.class, () -> new Sickness(""));

        // EP: whitespaces only
        assertThrows(IllegalArgumentException.class, () -> new Sickness(" "));

        // EP: leading/trailing whitespaces
        assertThrows(IllegalArgumentException.class, () -> new Sickness(" Cough"));
        assertThrows(IllegalArgumentException.class, () -> new Sickness(" 1"));

        // EP: invalid characters
        assertThrows(IllegalArgumentException.class, () -> new Sickness("1"));
        assertThrows(IllegalArgumentException.class, () -> new Sickness("!"));
    }

    @Test
    void constructor_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Sickness(null));
    }

    @Test
    void toString_validSickness_returnsFormattedString() {
        Sickness sickness = new Sickness("Allergies");
        assertEquals("Allergies", sickness.toString());
    }

    @Test
    void equals_compareIdenticalAndDifferentSicknesses_returnsCorrectEquality() {
        Sickness sickness1 = new Sickness("Asthma");
        Sickness sickness2 = new Sickness("Asthma");
        Sickness sickness3 = new Sickness("Bronchitis");

        // EP: same name
        assertEquals(sickness1, sickness2);

        // EP: different name
        assertNotEquals(sickness1, sickness3);

        // EP: null
        assertNotEquals(null, sickness1);

        // EP: different type
        assertNotEquals("Asthma", sickness1);

        // EP: same object
        assertEquals(sickness1, sickness1);
    }

    @Test
    void hashCode_compareIdenticalAndDifferentSicknesses_returnsConsistentHashes() {
        Sickness sickness1 = new Sickness("Diabetes");
        Sickness sickness2 = new Sickness("Diabetes");
        Sickness sickness3 = new Sickness("Hypertension");

        // EP: same name
        assertEquals(sickness1.hashCode(), sickness2.hashCode());

        // EP: different name
        assertNotEquals(sickness1.hashCode(), sickness3.hashCode());
    }
}
