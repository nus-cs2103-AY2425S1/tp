package seedu.address.model.restaurant;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.Pair;

/**
 * Represents the price category of a restaurant.
 */
public enum PriceCategory {
    CHEAP(10, "$"),
    MODERATE(20, "$$"),
    EXPENSIVE(40, "$$$"),
    LUXURY(Integer.MAX_VALUE, "$$$$");

    public static final String MESSAGE_CONSTRAINTS = "Price categories should be one of the "
            + "following: $, $$, $$$, $$$$";
    public static final String MESSAGE_MULTIPLE_PRICE_TAGS = "Multiple price tags found in the "
            + "set of tags";
    private final int maxValue;
    private final String symbol;

    /**
     * Constructs a {@code PriceCategory}.
     *
     * @param maxValue the maximum value of the price category
     * @param symbol   the symbol of the price category
     */
    PriceCategory(int maxValue, String symbol) {
        assert maxValue >= 0 : "Max value must not be negative.";
        this.maxValue = maxValue;
        this.symbol = symbol;
    }

    /**
     * Returns the symbol of the price category.
     *
     * @return the symbol of the price category
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns true if the symbol is part of a price category.
     *
     * @param symbol the symbol to check
     * @return true if the symbol is part of a price category
     */
    public static boolean isSymbol(String symbol) {
        for (PriceCategory category : PriceCategory.values()) {
            if (category.symbol.equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the price category based on the value of the price.
     *
     * @param value the value of the price
     * @return the price category
     */
    public static String getPriceCategoryString(double value) {
        // Pre-condition: Ensure the price is not negative
        assert value >= 0 : "Price must not be negative.";

        // Log the price value
        Logger logger = Logger.getLogger("PriceCategory");
        logger.log(Level.INFO, "Price value: {0}", value);

        for (PriceCategory category : PriceCategory.values()) {
            if (value <= category.maxValue) {
                return category.getSymbol();
            }
        }
        throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
    }

    /**
     * Extracts the price tag from a set of tags.
     *
     * @param tags the set of tags
     * @return a pair of the price tag and the other tags
     */
    public static Pair<Price, Set<Tag>> extractPriceTag(Set<Tag> tags) {
        Price priceTag = null;
        Set<Tag> otherTags = new HashSet<>();

        for (Tag tag : tags) {
            if (isSymbol(tag.tagName) && priceTag == null) {
                priceTag = new Price(tag);
            } else if (isSymbol(tag.tagName) && priceTag != null) {
                throw new IllegalArgumentException(MESSAGE_MULTIPLE_PRICE_TAGS);
            } else {
                otherTags.add(tag);
            }
        }
        return new Pair<>(priceTag, otherTags);
    }

    /**
     * Returns true if the set of tags has multiple price tags.
     *
     * @param tags the set of tags
     * @return true if the set of tags has multiple price tags
     */
    public static boolean hasMultiplePriceTags(Set<Tag> tags) {
        int count = 0;
        for (Tag tag : tags) {
            if (isSymbol(tag.tagName)) {
                count++;
            }
        }
        return count > 1;
    }
}
