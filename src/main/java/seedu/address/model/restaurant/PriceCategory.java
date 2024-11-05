package seedu.address.model.restaurant;

/**
 * Represents the price category of a restaurant.
 */
public enum PriceCategory {
    AFFORDABLE("$"),
    MODERATE("$$"),
    PREMIUM("$$$"),
    LUXURY("$$$$");

    public static final String MESSAGE_CONSTRAINTS = "Price categories should be one of the following: "
            + "$, $$, $$$, $$$$";
    private final String symbol;

    /**
     * Constructs a {@code PriceCategory}.
     *
     * @param maxValue the maximum value of the price category
     * @param symbol   the symbol of the price category
     */
    PriceCategory(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the symbol of the price category.
     *
     * @return the string of the symbol of the price category
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Alias for {@code getSymbol()}. Returns the symbol of the price category.
     *
     * @return the string of the symbol of the price category
     */
    @Override
    public String toString() {
        return getSymbol();
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
     * Returns the price category of a symbol.
     *
     * @param symbol the symbol to check
     * @return the price category of the symbol
     */
    public static PriceCategory getCategory(String symbol) {
        for (PriceCategory category : PriceCategory.values()) {
            if (category.symbol.equals(symbol)) {
                return category;
            }
        }
        throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
    }
}
