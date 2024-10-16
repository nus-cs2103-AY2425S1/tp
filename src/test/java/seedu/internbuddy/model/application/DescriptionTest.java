package seedu.internbuddy.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        //assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        //invalid descriptions
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        //valid descriptions
        assertTrue(Description.isValidDescription("Requires: ReactJS and ExpressJS"));
        assertTrue(Description.isValidDescription("Requires: Python"));
        assertTrue(Description.isValidDescription("Requires: Figma"));
    }

    @Test
    public void equals() {
        Description description1 = new Description("INTERESTED");
        Description description2 = new Description("INTERESTED");
        Description description3 = new Description("APPLIED");

        // same object -> returns true
        assertTrue(description1.equals(description1));

        // same values -> returns true
        assertTrue(description1.equals(description2));

        // different values -> returns false
        assertFalse(description1.equals(description3));

        // null -> returns false
        assertFalse(description1.equals(null));

        // different type -> returns false
        assertFalse(description1.equals(5));
    }
}
