package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AllergyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Allergy(null));
    }

    @Test
    public void constructor_invalidAllergyName_throwsIllegalArgumentException() {
        String invalidAllergyName = "";
        assertThrows(IllegalArgumentException.class, () -> new Allergy(invalidAllergyName));
    }

    @Test
    public void constructor_validAllergyName_success() {
        // Valid allergy names
        new Allergy("Peanuts");
        new Allergy("Dust");
        new Allergy("Pollen");
    }

    @Test
    public void isValidAllergy() {
        // null allergy name
        assertThrows(NullPointerException.class, () -> Allergy.isValidAllergy(null));

        // invalid allergy names
        assertFalse(Allergy.isValidAllergy("")); // empty string
        assertFalse(Allergy.isValidAllergy("#choco0"));

        // valid allergy names
        assertTrue(Allergy.isValidAllergy("Peanuts"));
        assertTrue(Allergy.isValidAllergy("Dust"));
        assertTrue(Allergy.isValidAllergy("Pollen"));
    }

    @Test
    public void equals() {
        Allergy allergy1 = new Allergy("Peanuts");
        Allergy allergy2 = new Allergy("Peanuts");
        Allergy allergy3 = new Allergy("Dust");

        // same object -> returns true
        assertTrue(allergy1.equals(allergy1));

        // same values -> returns true
        assertTrue(allergy1.equals(allergy2));

        // different values -> returns false
        assertFalse(allergy1.equals(allergy3));

        // null -> returns false
        assertFalse(allergy1.equals(null));

        // different types -> returns false
        assertFalse(allergy1.equals(5)); // comparing with integer
    }

    @Test
    public void hashCode_sameAllergyName_sameHashCode() {
        Allergy allergy1 = new Allergy("Peanuts");
        Allergy allergy2 = new Allergy("Peanuts");

        assertEquals(allergy1.hashCode(), allergy2.hashCode());
    }

    @Test
    public void toString_validAllergyName_correctString() {
        Allergy allergy = new Allergy("Peanuts");
        assertEquals("Peanuts", allergy.toString());
    }
}
