package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class SicknessTest {

    @Test
    void testValidSickness() {
        assertTrue(Sickness.isValidSickness("Common cold"));
        assertTrue(Sickness.isValidSickness("Flu"));
        assertTrue(Sickness.isValidSickness("COVID-19"));
        assertTrue(Sickness.isValidSickness("Migraine"));
        assertTrue(Sickness.isValidSickness("A"));
    }

    @Test
    void testInvalidSickness() {
        assertFalse(Sickness.isValidSickness(""));
        assertFalse(Sickness.isValidSickness(" "));
        assertFalse(Sickness.isValidSickness(" Headache"));
    }

    @Test
    void testConstructorWithValidInput() {
        Sickness sickness = new Sickness("Fever");
        assertEquals("Fever", sickness.value);
    }

    @Test
    void testConstructorWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> new Sickness(""));
        assertThrows(IllegalArgumentException.class, () -> new Sickness(" "));
        assertThrows(IllegalArgumentException.class, () -> new Sickness(" Cough"));
    }

    @Test
    void testConstructorWithNullInput() {
        assertThrows(NullPointerException.class, () -> new Sickness(null));
    }

    @Test
    void testToString() {
        Sickness sickness = new Sickness("Allergies");
        assertEquals("Allergies", sickness.toString());
    }

    @Test
    void testEquals() {
        Sickness sickness1 = new Sickness("Asthma");
        Sickness sickness2 = new Sickness("Asthma");
        Sickness sickness3 = new Sickness("Bronchitis");

        assertEquals(sickness1, sickness2);
        assertNotEquals(sickness1, sickness3);
        assertNotEquals(null, sickness1);
        assertNotEquals("Asthma", sickness1); // Different type
        assertEquals(sickness1, sickness1); // Same object
    }

    @Test
    void testHashCode() {
        Sickness sickness1 = new Sickness("Diabetes");
        Sickness sickness2 = new Sickness("Diabetes");
        Sickness sickness3 = new Sickness("Hypertension");

        assertEquals(sickness1.hashCode(), sickness2.hashCode());
        assertNotEquals(sickness1.hashCode(), sickness3.hashCode());
    }
}
