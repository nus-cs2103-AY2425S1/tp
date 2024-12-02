package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.person.Description.isValidDescription;

import org.junit.jupiter.api.Test;

public class DescriptionTest {
    private final String invalidLongInput = "a".repeat(501);
    private final String validLongInput = "a".repeat(500);

    @Test
    public void constructor_null_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new Description(null);
        });
    }

    @Test
    public void constructor_inputWith501Characters_throwIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            new Description(invalidLongInput);
        });
    }

    @Test
    public void isValidDescription_emptyString_true() {
        assertTrue(isValidDescription(""));
    }

    @Test
    public void isValidDescription_inputWith501Characters_true() {
        assertTrue(isValidDescription(validLongInput));
    }

    @Test
    public void isValidDescription_inputWith501Characters_false() {
        assertFalse(isValidDescription(invalidLongInput));
    }

    @Test
    public void isBlank_blankDescription_true() {
        Description description = new Description("");
        assertTrue(description.isBlank());
    }

    @Test
    public void isBlank_descriptionWithText_false() {
        Description description = new Description("Hello");
        assertFalse(description.isBlank());
    }

    @Test
    public void toString_emptyDescription_emptyString() {
        assertEquals("", new Description("").toString());
    }

    @Test
    public void toString_inputWith500Characters_emptyString() {
        assertEquals(validLongInput, new Description(validLongInput).toString());
    }
}
