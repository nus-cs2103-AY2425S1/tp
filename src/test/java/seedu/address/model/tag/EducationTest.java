package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EducationTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Education(null));
    }

    @Test
    public void constructor_invalidEducationLevel_throwsIllegalArgumentException() {
        String invalidEducationLevel = "";
        assertThrows(IllegalArgumentException.class, () -> new Education(invalidEducationLevel));
    }

    @Test
    public void constructor_validEducationLevel() {
        String validEducationLevel = "Primary";
        assertEquals(Education.class, new Education(validEducationLevel).getClass());
    }

    @Test
    public void isValidEducationLevel() {
        // null education level
        assertThrows(NullPointerException.class, () -> Education.isValidEducationLevel(null));
    }

    @Test
    public void equals() {
        Education education = new Education("Primary");

        // same education -> returns true
        assertTrue(education.equals(new Education("Primary")));

        // same object -> returns true
        assertTrue(education.equals(education));

        // null -> returns false
        assertFalse(education.equals(null));

        // different types -> returns false
        assertFalse(education.equals(5.0f));

        // different education -> returns false
        assertFalse(education.equals(new Education("Secondary")));
    }

    @Test
    public void testToString() {
        assertEquals("Primary", new Education("Primary").toString());
    }
}
