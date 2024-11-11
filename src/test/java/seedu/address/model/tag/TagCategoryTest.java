package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class TagCategoryTest {

    @Test
    public void fromString_validCategories_returnsSuccess() throws IllegalValueException {
        // all valid inputs
        assertEquals(TagCategory.GENERAL, TagCategory.fromString("GENERAL"));
        assertEquals(TagCategory.ACADEMICS, TagCategory.fromString("ACADEMICS"));
        assertEquals(TagCategory.NETWORKING, TagCategory.fromString("NETWORKING"));
        assertEquals(TagCategory.MENTORSHIP, TagCategory.fromString("MENTORSHIP"));
        assertEquals(TagCategory.ACTIVITIES, TagCategory.fromString("ACTIVITIES"));
    }

    @Test
    public void fromString_invalidCategories_throwsIllegalValueException() {
        // completely invalid inputs
        assertThrows(IllegalValueException.class, () -> TagCategory.fromString("HEHE"));
        assertThrows(IllegalValueException.class, () -> TagCategory.fromString("ACACACACAC"));

        // wrong case
        assertThrows(IllegalValueException.class, () -> TagCategory.fromString("Academics"));
        assertThrows(IllegalValueException.class, () -> TagCategory.fromString("GeNeRaL"));
        assertThrows(IllegalValueException.class, () -> TagCategory.fromString("aCtIViTieS"));
    }
}
