package seedu.address.model.goods;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GoodsNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GoodsName(null));
    }

    @Test
    public void constructor_invalidGoodsName_throwsIllegalArgumentException() {
        String invalidGoodsName = "";
        assertThrows(IllegalArgumentException.class, () -> new GoodsName(invalidGoodsName));
    }

    @Test
    public void isValidGoodsName() {
        // null GoodsName
        assertThrows(NullPointerException.class, () -> GoodsName.isValidName(null));

        // invalid GoodsName
        assertFalse(GoodsName.isValidName("")); // empty string
        assertFalse(GoodsName.isValidName(" ")); // spaces only
        assertFalse(GoodsName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(GoodsName.isValidName("bread*^")); // contains non-alphanumeric characters

        // valid GoodsName
        assertTrue(GoodsName.isValidName("milk bread")); // alphabets only
        assertTrue(GoodsName.isValidName("gardenia milk bread 2")); // alphanumeric characters
        assertTrue(GoodsName.isValidName("Gardenia Milk Bread")); // with capital letters
        assertTrue(GoodsName.isValidName("Super expensive Gardenia Milk Bread with Cheese")); // long GoodsNames
    }

    @Test
    public void equals() {
        GoodsName goodsName = new GoodsName("A valid GoodsName");

        // same values -> returns true
        assertEquals(goodsName, new GoodsName("A valid GoodsName"));

        // same object -> returns true
        assertEquals(goodsName, goodsName);

        // null -> returns false
        assertNotEquals(null, goodsName);

        // different types -> returns false
        assertNotEquals(100, goodsName);

        // different values -> returns false
        assertNotEquals(goodsName, new GoodsName("Other valid GoodsName"));
    }
}
