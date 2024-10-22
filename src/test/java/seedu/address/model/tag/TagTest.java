package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void hashMapSuccess() {
        Tag.getDietaryRestrictionsMappings().put("v", "Vegan");
        Tag.getDietaryRestrictionsMappings().put("vg", "Vegetarian");
        assertEquals(new Tag("v").toString(), "[Vegan]");
        assertEquals(new Tag("vg").toString(), "[Vegetarian]");
    }

    @Test
    public void testGetTagName() {
        Tag tag = new Tag("exampleTag");
        String result = tag.getTagName();
        assertEquals("exampleTag", result);
    }

    @Test
    public void equalsSuccess() {
        Tag.getDietaryRestrictionsMappings().put("v", "Vegan");
        assertTrue(new Tag("v").equals(new Tag("Vegan")));
        //testing custom tags
        assertTrue(new Tag("No pork").equals(new Tag("No pork")));
        //testing against null
        assertFalse(new Tag("Vegan").equals(null));
    }
}
