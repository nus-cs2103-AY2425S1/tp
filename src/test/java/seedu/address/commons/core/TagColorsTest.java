package seedu.address.commons.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagColorsTest {
    @Test
    public void toStringMethod() {
        TagColors tagColors = new TagColors();
        String result = tagColors.toString();
        String expected = TagColors.class.getCanonicalName() + "{Colors=" + tagColors.getTagColors() + "}";
        assertEquals(result, expected);
    }
}
