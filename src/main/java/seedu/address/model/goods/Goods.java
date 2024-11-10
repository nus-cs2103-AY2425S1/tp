package seedu.address.model.goods;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * An immutable class that represents goods.
 */
public record Goods(GoodsName goodsName, GoodsCategories category) {

    public static final String MESSAGE_CONSTRAINTS = "Product quantity cannot be negative or 0!";

    /**
     * Constructs a {@Code Goods}.
     * All fields should not be null.
     *
     * @param goodsName A valid goods name.
     * @param category  A category for the goods.
     */
    public Goods {
        requireAllNonNull(goodsName, category);
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

        return otherGoods.goodsName.equals(this.goodsName)
                && otherGoods.category.equals(this.category);
    }
}
