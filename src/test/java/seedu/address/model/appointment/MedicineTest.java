package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MedicineTest {
    @Test
    public void testValidMedicine() {
        assertTrue(Medicine.isValidMedicine("Aspirin"));
        assertTrue(Medicine.isValidMedicine("Tamiflu"));
        assertTrue(Medicine.isValidMedicine("Paracetamol"));
        assertTrue(Medicine.isValidMedicine("Neoprofen"));
    }

    @Test
    public void testInvalidMedicine() {
        assertFalse(Medicine.isValidMedicine(""));
        assertFalse(Medicine.isValidMedicine(" "));
        assertFalse(Medicine.isValidMedicine(" Aspirin"));
    }

    @Test
    public void testConstructorWithValidInput() {
        Medicine medicine = new Medicine("Aspirin");
        assertEquals("aspirin", medicine.value);
    }

    @Test
    public void testConstructorWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> new Medicine(""));
        assertThrows(IllegalArgumentException.class, () -> new Medicine(" "));
        assertThrows(IllegalArgumentException.class, () -> new Medicine(" Aspirin"));
    }

    @Test
    public void testConstructorWithNullInput() {
        assertThrows(NullPointerException.class, () -> new Sickness(null));
    }

    @Test
    public void testToString() {
        Medicine medicine = new Medicine("Aspirin");
        assertEquals("aspirin", medicine.toString());
        assertNotEquals("Aspirin", medicine.toString());
    }

    @Test
    public void testEquals() {
        Medicine medicine1 = new Medicine("Aspirin");
        Medicine medicine2 = new Medicine("Aspirin");
        Medicine medicine3 = new Medicine("Tamiflu");

        assertEquals(medicine1, medicine2);
        assertNotEquals(medicine1, medicine3);
        assertNotEquals(null, medicine1);
        assertNotEquals("Aspirin", medicine1);
        assertEquals(medicine1, medicine1);
    }

    @Test
    public void testHashCode() {
        Medicine medicine1 = new Medicine("Aspirin");
        Medicine medicine2 = new Medicine("Aspirin");
        Medicine medicine3 = new Medicine("Tamiflu");

        assertEquals(medicine1.hashCode(), medicine2.hashCode());
        assertNotEquals(medicine1.hashCode(), medicine3.hashCode());
    }
}
