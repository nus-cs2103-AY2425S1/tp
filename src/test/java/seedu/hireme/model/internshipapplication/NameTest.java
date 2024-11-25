package seedu.hireme.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_emptyName_throwsIllegalArgumentException() {
        String emptyName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(emptyName));
    }

    @Test
    public void constructor_validSpecialCharacters_success() {
        // Only permitted special characters
        assertEquals(new Name("Google & Co."), new Name("Google & Co."));
        assertEquals(new Name("Research/Development"), new Name("Research/Development"));
        assertEquals(new Name("Example: AI (Research)"), new Name("Example: AI (Research)"));
    }

    @Test
    public void constructor_invalidSpecialCharacters_throwsIllegalArgumentException() {
        // Unsupported characters such as %, $, @, !
        assertThrows(IllegalArgumentException.class, () -> new Name("Company@123"));
        assertThrows(IllegalArgumentException.class, () -> new Name("Innovate! Co."));
        assertThrows(IllegalArgumentException.class, () -> new Name("Finance%Inc"));
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // different cases but same content -> returns true
        assertTrue(name.equals(new Name("valid name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }

    @Test
    public void toStringMethod() {
        Name name = new Name("Valid Name");
        assertEquals("Valid Name", name.toString());
    }
}
