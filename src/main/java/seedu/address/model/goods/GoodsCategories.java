package seedu.address.model.goods;

/**
 * Enum to represent Categories of Goods
 */
public enum GoodsCategories {
    CONSUMABLES,
    LIFESTYLE,
    SPECIALTY;

    public static final String MESSAGE_UNKNOWN_CATEGORY =
            "Category should strictly be one of [CONSUMABLES, LIFESTYLE, SPECIALTY]";
}
