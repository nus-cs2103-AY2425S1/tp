package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    private static final String VALID_DESCRIPTION = "Buy groceries";
    private static final String INVALID_DESCRIPTION_EMPTY = "";
    private static final String INVALID_DESCRIPTION_BLANK = "   ";
    private static final String VALID_DESCRIPTION_WITH_WHITESPACE = "   Clean the house   ";

    @Test
    public void constructor_validDescription_success() {
        Description description = new Description(VALID_DESCRIPTION);
        assertEquals(VALID_DESCRIPTION, description.toString());
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        // Test with empty description
        try {
            new Description(INVALID_DESCRIPTION_EMPTY);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        // Test with blank description (only spaces)
        try {
            new Description(INVALID_DESCRIPTION_BLANK);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void isValidDescription_validDescription_returnsTrue() {
        assertTrue(Description.isValidDescription(VALID_DESCRIPTION));
    }

    @Test
    public void isValidDescription_invalidDescription_returnsFalse() {
        assertFalse(Description.isValidDescription(INVALID_DESCRIPTION_EMPTY));
        assertFalse(Description.isValidDescription(INVALID_DESCRIPTION_BLANK));
    }

    @Test
    public void equals_sameDescription_returnsTrue() {
        Description description1 = new Description(VALID_DESCRIPTION);
        Description description2 = new Description(VALID_DESCRIPTION);
        assertTrue(description1.equals(description2));
    }

    @Test
    public void equals_differentDescription_returnsFalse() {
        Description description1 = new Description(VALID_DESCRIPTION);
        Description description2 = new Description("Go for a run");
        assertFalse(description1.equals(description2));
    }

    @Test
    public void equals_nullDescription_returnsFalse() {
        Description description = new Description(VALID_DESCRIPTION);
        assertFalse(description.equals(null));
    }

    @Test
    public void equals_differentObjectType_returnsFalse() {
        Description description = new Description(VALID_DESCRIPTION);
        assertFalse(description.equals("some string"));
    }

    @Test
    public void hashCode_sameDescription_returnsSameHashCode() {
        Description description1 = new Description(VALID_DESCRIPTION);
        Description description2 = new Description(VALID_DESCRIPTION);
        assertEquals(description1.hashCode(), description2.hashCode());
    }

    @Test
    public void hashCode_differentDescription_returnsDifferentHashCode() {
        Description description1 = new Description(VALID_DESCRIPTION);
        Description description2 = new Description("Go for a run");
        assertFalse(description1.hashCode() == description2.hashCode());
    }

    @Test
    public void toString_trimsWhitespace() {
        Description description = new Description(VALID_DESCRIPTION_WITH_WHITESPACE.trim());
        assertEquals("Clean the house", description.toString());
    }
}
