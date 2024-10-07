package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import seedu.address.model.event.Description;

public class DescriptionTest {

    @Test
    public void ofNullable_nullDescription_returnsDefault() {
        Description description = Description.ofNullable(null);
        assertEquals("No description provided", description.toString());
    }

    @Test
    public void ofNullable_blankDescription_returnsDefault() {
        Description description = Description.ofNullable(" ");
        assertEquals("No description provided", description.toString());
    }

    @Test
    public void ofNullable_validDescription_returnsProvidedDescription() {
        Description description = Description.ofNullable("Discuss project details");
        assertEquals("Discuss project details", description.toString());
    }

    @Test
    public void isValidDescription() {
        // invalid descriptions
        assertFalse(Description.isValidDescription(null)); // null value
        assertFalse(Description.isValidDescription(" ")); // blank string

        // valid descriptions
        assertTrue(Description.isValidDescription("Meeting with team"));
        assertTrue(Description.isValidDescription("Technical workshop"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Description description = Description.ofNullable("Discuss project details");
        assertTrue(description.equals(description));
    }

    @Test
    public void equals_differentObjectsSameValue_returnsTrue() {
        Description desc1 = Description.ofNullable("Discuss project details");
        Description desc2 = Description.ofNullable("Discuss project details");
        assertTrue(desc1.equals(desc2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Description desc1 = Description.ofNullable("Discuss project details");
        Description desc2 = Description.ofNullable("Team meeting");
        assertFalse(desc1.equals(desc2));
    }
}

