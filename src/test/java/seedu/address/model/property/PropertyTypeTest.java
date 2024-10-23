package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PropertyTypeTest {
    private String validType1 = "CONDO";
    private String validType2 = "condO";
    private String inValidType1 = "PRIVATE";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertFalse(PropertyType.isValidEnumValue(inValidType1));
        assertTrue(PropertyType.isValidEnumValue(validType1));
        assertTrue(PropertyType.isValidEnumValue(validType2));
    }
}
