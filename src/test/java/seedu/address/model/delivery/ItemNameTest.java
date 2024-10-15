package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ItemNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ItemName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new ItemName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> ItemName.isValidItemName(null));

        // invalid name
        assertFalse(ItemName.isValidItemName("")); // empty string
        assertFalse(ItemName.isValidItemName(" ")); // spaces only
        assertFalse(ItemName.isValidItemName("^")); // only non-alphanumeric characters
        assertFalse(ItemName.isValidItemName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(ItemName.isValidItemName("peter jack")); // alphabets only
        assertTrue(ItemName.isValidItemName("12345")); // numbers only
        assertTrue(ItemName.isValidItemName("peter the 2nd")); // alphanumeric characters
        assertTrue(ItemName.isValidItemName("Capital Tan")); // with capital letters
        assertTrue(ItemName.isValidItemName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        ItemName name = new ItemName("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new ItemName("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new ItemName("Other Valid Name")));
    }
}
