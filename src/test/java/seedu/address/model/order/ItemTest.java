package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ItemTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Item(null));
    }

    @Test
    public void constructor_invalidItem_throwsIllegalArgumentException() {
        String invalidItem = "";
        assertThrows(IllegalArgumentException.class, () -> new Item(invalidItem));
    }

    @Test
    public void isValidItem() {
        // null item
        assertThrows(NullPointerException.class, () -> Item.isValidItem(null));

        // invalid item
        assertFalse(Item.isValidItem("")); // empty string
        assertFalse(Item.isValidItem(" ")); // spaces only
        assertFalse(Item.isValidItem("^")); // only non-alphanumeric characters
        assertFalse(Item.isValidItem("peter*")); // contains non-alphanumeric characters

        // valid item
        assertTrue(Item.isValidItem("peter jack")); // alphabets only
        assertTrue(Item.isValidItem("12345")); // numbers only
        assertTrue(Item.isValidItem("peter the 2nd")); // alphanumeric characters
        assertTrue(Item.isValidItem("Capital Tan")); // with capital letters
        assertTrue(Item.isValidItem("David Roger Jackson Ray Jr 2nd")); // long item description
    }

    @Test
    public void equals() {
        Item item = new Item("Valid Item");

        // same values -> returns true
        assertTrue(item.equals(new Item("Valid Item")));

        // same object -> returns true
        assertTrue(item.equals(item));

        // null -> returns false
        assertFalse(item.equals(null));

        // different types -> returns false
        assertFalse(item.equals(5.0f));

        // different values -> returns false
        assertFalse(item.equals(new Item("Other Valid Item")));
    }
}
