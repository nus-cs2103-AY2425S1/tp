package seedu.sellsavvy.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.testutil.Assert.assertThrows;

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

        // valid item
        assertTrue(Item.isValidItem("^")); // only non-alphanumeric characters
        assertTrue(Item.isValidItem("abacus*")); // contains non-alphanumeric characters
        assertTrue(Item.isValidItem("abacus \\0 \\n +x test")); // contains non-punctuation characters
        assertTrue(Item.isValidItem("world Map")); // alphabets only
        assertTrue(Item.isValidItem("12345")); // numbers only
        assertTrue(Item.isValidItem("Encyclopedia 25th edition")); // alphanumeric characters
        assertTrue(Item.isValidItem("Encyclopedia <edited by John> [25th edition]")); // long item description
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

    @Test
    public void isSimilarTo() {
        Item item = new Item("Valid Item");

        // same values -> returns true
        assertTrue(item.isSimilarTo(new Item("Valid Item")));

        // same object -> returns true
        assertTrue(item.isSimilarTo(item));

        // null -> throws exception
        assertThrows(NullPointerException.class, () -> item.isSimilarTo(null));

        // different values -> returns false
        assertFalse(item.isSimilarTo(new Item("Other Valid Item")));

        // Only different in casing -> returns true
        assertTrue(item.isSimilarTo(new Item("valid item")));

        // Only different in space -> returns true
        assertTrue(item.isSimilarTo(new Item("ValidItem")));

        // different in both casing and space
        assertTrue(item.isSimilarTo(new Item("validitem")));

    }
}
