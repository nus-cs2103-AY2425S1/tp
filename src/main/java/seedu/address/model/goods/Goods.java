package seedu.address.model.goods;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents goods.
 * Garauntees: Immutable; name is valid as declared in {@link #isValidGoodsName(String)};
 */
public class Goods {

    public static final String MESSAGE_CONSTRAINTS = "Product quantity cannot be negative or 0!";
    public static final String VALIDATION_REGEX = "/^[\\w\\-\\s]+$/";

    private final GoodsName goodsName;
    private final GoodsCategories category;

    /**
     * Constructs a {@Code Goods}.
     * All fields should not be null.
     *
     * @param goodsName A valid goods name.
     * @param category A category for the goods.
     */
    public Goods(GoodsName goodsName, GoodsCategories category) {
        requireAllNonNull(goodsName, category);
        this.goodsName = goodsName;
        this.category = category;
    }

    public GoodsName getGoodsName() {
        return goodsName;
    }

    public GoodsCategories getCategory() {
        return category;
    }

    /**
     * Returns True if the goods name is valid.
     *
     * @param test String for goods name
     */
    public static boolean isValidGoodsName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Format goods as text for viewing.
     */
    @Override
    public String toString() {
        return this.goodsName.toString();
    }

    /**
     * Converts good to be written in a CSV file.
     *
     * @return goods name with category
     */
    public String convertToCsvWrite() {
        return goodsName + "," + category;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof Goods otherGoods)) {
            return false;
        }

        return otherGoods.goodsName.equals(this.goodsName) && otherGoods.category.equals(this.category);
    }
}
