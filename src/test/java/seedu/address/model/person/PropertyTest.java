package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PropertyTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Property(null));
    }

    @Test
    public void equals() {
        Property property = new Property("Pasir Ris");

        // same values -> returns true
        assertTrue(property.equals(new Property("Pasir Ris")));

        // same object -> returns true
        assertTrue(property.equals(property));

        // null -> returns false
        assertFalse(property.equals(null));

        // different types -> returns false
        assertFalse(property.equals(5.0f));

        // different values -> returns false
        assertFalse(property.equals(new Phone("995")));
    }
}
