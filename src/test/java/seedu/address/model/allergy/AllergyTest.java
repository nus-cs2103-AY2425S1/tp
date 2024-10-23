package seedu.address.model.allergy;

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
    public void isValidAllergyName() {
        // null allergy name
        assertThrows(NullPointerException.class, () -> Allergy.isValidAllergyName(null));
    }

}
