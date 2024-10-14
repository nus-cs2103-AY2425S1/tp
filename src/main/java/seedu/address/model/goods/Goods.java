package seedu.address.model.goods;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

import seedu.address.model.goodsReceipt.Date;
import seedu.address.model.person.Name;

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
        return String.format("%s", this.goodsName);
    }
}
