package seedu.address.testutil;

import seedu.address.model.goods.Goods;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goods.GoodsName;

/**
 * A utility class to help with building Goods objects.
 */
public class GoodsBuilder {

    public static final String DEFAULT_NAME = "Apple";
    public static final GoodsCategories DEFAULT_GOODS_CATEGORY = GoodsCategories.CONSUMABLES;

    private String name;
    private GoodsCategories goodsCategory;

    /**
     * Creates a {@code GoodsBuilder} with the default details.
     */
    public GoodsBuilder() {
        name = DEFAULT_NAME;
        goodsCategory = DEFAULT_GOODS_CATEGORY;
    }

    /**
     * Sets the {@code GoodsName} of the {@code Goods } that we are building.
     */
    public GoodsBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code GoodsCategories} of the {@code Goods } that we are building.
     */
    public GoodsBuilder withGoodsCategory(GoodsCategories goodsCategory) {
        this.goodsCategory = goodsCategory;
        return this;
    }

    /**
     * Builds a Goods object.
     */
    public Goods build() {
        return new Goods(new GoodsName(name), goodsCategory);
    }
}
