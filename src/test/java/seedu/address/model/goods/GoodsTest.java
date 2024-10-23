package seedu.address.model.goods;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class GoodsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Goods(new GoodsName("Test"), null));
    }

    @Test
    public void constructor_invalidProcurementDate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Goods(new GoodsName("Milk Bread^*"),
                GoodsCategories.CONSUMABLES));
    }

    @Test
    public void constructor_validParameters_success() {
        assertDoesNotThrow(() -> new Goods(new GoodsName("Milk Bread"), GoodsCategories.CONSUMABLES));
    }

    @Test
    public void getReadableGoodsName_success() {
        Goods testGoods = new Goods(new GoodsName("Gardenia Bread"), GoodsCategories.CONSUMABLES);
        String expected = "Gardenia Bread";
        assertEquals(expected, testGoods.getReadableGoodsName());
    }

    @Test
    public void sameGoods_success() {
        Goods testGoods = new Goods(new GoodsName("Gardenia Bread"), GoodsCategories.CONSUMABLES);
        Goods otherGoods = new Goods(new GoodsName("Gardenia Bread"), GoodsCategories.CONSUMABLES);
        assertEquals(testGoods, otherGoods);
    }

    @Test
    public void differentGoods_failure() {
        Goods testGoods = new Goods(new GoodsName("Gardenia Bread"), GoodsCategories.CONSUMABLES);
        Goods otherGoods = new Goods(new GoodsName("Other Bread"), GoodsCategories.CONSUMABLES);
        assertNotEquals(testGoods, otherGoods);
    }
}
