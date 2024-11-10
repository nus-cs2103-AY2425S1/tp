package seedu.address.model.goods;

/**
 * An enum to represent the categories of goods
 */
public enum GoodsCategories {

    CONSUMABLES, LIFESTYLE, SPECIALTY;

    public static final String MESSAGE_UNKNOWN_CATEGORY =
            "Category should strictly be one of [CONSUMABLES, LIFESTYLE, SPECIALTY]";
}
