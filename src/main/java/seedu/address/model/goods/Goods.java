package seedu.address.model.goods;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * An immutable class that represents goods.
 */
public final class Goods {

    public static final String MESSAGE_CONSTRAINTS = "Product quantity cannot be negative or 0!";
    private final GoodsName goodsName;
    private final GoodsCategories category;


    /**
     * Constructs a {@Code Goods}.
     * All fields should not be null.
     *
     * @param goodsName A valid goods name.
     * @param category  A category for the goods.
     */
    public Goods(GoodsName goodsName, GoodsCategories category) {
        requireAllNonNull(goodsName, category);
        this.goodsName = goodsName;
        this.category = category;
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

    public GoodsName goodsName() {
        return goodsName;
    }

    public GoodsCategories category() {
        return category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(goodsName, category);
    }

}
