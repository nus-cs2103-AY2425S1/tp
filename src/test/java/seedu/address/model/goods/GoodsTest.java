package seedu.address.model.goods;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;

public class GoodsTest {
    private static final String DATETIME_VALID = "2024-10-10 12:00";
    private static final String DATETIME_INVALID = "2024-12-12 12:00";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Goods(new GoodsName("Test"), 0, 0, null, null, null, false, null));
    }

    @Test
    public void constructor_invalidProcurementDate_throwsIllegalArgumentException() {
        GoodsCategories category = GoodsCategories.CONSUMABLES;
        assertThrows(IllegalArgumentException.class, () -> new Goods(new GoodsName("Milk Bread"), 1, 5.0,
                category, new Date(DATETIME_INVALID), new Date(DATETIME_INVALID), false, new Name("Alex Yeoh")));
    }

    @Test
    public void constructor_validParameters_success() {
        assertDoesNotThrow(() -> new Goods(new GoodsName("Milk Bread"), 1, 5.0, GoodsCategories.CONSUMABLES,
                new Date(DATETIME_VALID), new Date(DATETIME_VALID), false, new Name("Alex Yeoh")));
    }
}
