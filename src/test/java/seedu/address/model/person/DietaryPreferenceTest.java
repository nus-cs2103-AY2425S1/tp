package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DietaryPreferenceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidPreference_throwsIllegalArgumentException() {
        String invalidPreference = "";
        assertThrows(IllegalArgumentException.class, () -> new DietaryPreference(invalidPreference));
    }

    @Test
    public void isValidPreference() {
        // null preference
        assertThrows(NullPointerException.class, () -> DietaryPreference.isValidDietaryPreference(null));

        // invalid preferences
        assertFalse(DietaryPreference.isValidDietaryPreference("")); // empty string
        assertFalse(DietaryPreference.isValidDietaryPreference(" ")); // spaces only

        // valid preferences
        assertTrue(DietaryPreference.isValidDietaryPreference("Gluten Free"));
        assertTrue(DietaryPreference.isValidDietaryPreference("No dairy"));
        assertTrue(DietaryPreference.isValidDietaryPreference("Allergic to chocolate"));
    }

    @Test
    public void equals() {
        DietaryPreference preference = new DietaryPreference("Valid Preference");

        // same values -> returns true
        assertTrue(preference.equals(new DietaryPreference("Valid Preference")));

        // same object -> returns true
        assertTrue(preference.equals(preference));

        // null -> returns false
        assertFalse(preference.equals(null));

        // different types -> returns false
        assertFalse(preference.equals(5.0f));

        // different values -> returns false
        assertFalse(preference.equals(new DietaryPreference("Other Valid Preference")));
    }

}
