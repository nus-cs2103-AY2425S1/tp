package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "Exploring the depths of knowledge can lead to unexpected insights, "
                + "transforming ordinary thoughts into extraordinary ideas that inspire change and growth."
                + "blah blah blah";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null Description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid Descriptions
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription("Exploring the depths of knowledge can "
                + "lead to unexpected insights," + "transforming ordinary thoughts into extraordinary "
                + "ideas that inspire change and growth"
                + "blah blah blah")); // too long Description




        // valid Descriptions
        assertTrue(Description.isValidDescription("Loves to code")); // alphabets only
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription("Loves to eat a lot of food all in 1 go, "
                + "and fishing")); // long Description
    }

    @Test
    public void equals() {
        Description description = new Description("Valid Description");

        // same values -> returns true
        assertTrue(description.equals(new Description("Valid Description")));

        // same object -> returns true
        assertTrue(description.equals(description));

        // null -> returns false
        assertFalse(description.equals(null));

        // different types -> returns false
        assertFalse(description.equals(5.0f));

        // different values -> returns false
        assertFalse(description.equals(new Description("Other Valid Description")));
    }
}
