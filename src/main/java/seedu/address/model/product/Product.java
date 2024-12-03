package seedu.address.model.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Product in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidProductName(String)}.
 */
public class Product {
    public static final String MESSAGE_CONSTRAINTS = "PRODUCT must be alphanumeric, can include spaces but must "
            + "not start with a space and must be \nbetween 1 and 50 (inclusive) characters long.";
    public static final String VALIDATION_REGEX = "^[\\p{Alnum}][\\p{Alnum} ]{0,49}$";

    private final String productName;

    /**
     * Constructs a {@code Product}.
     *
     * @param productName A valid product name.
     */
    public Product(String productName) {
        requireNonNull(productName);
        checkArgument(isValidProductName(productName), MESSAGE_CONSTRAINTS);
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    /**
     * Returns true if a given string is a valid product name.
     *
     * @param test String to test.
     */
    public static boolean isValidProductName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Product)) {
            return false;
        }

        Product otherProduct = (Product) other;
        return productName.equals(otherProduct.productName);
    }

    @Override
    public int hashCode() {
        return productName.hashCode();
    }

    public String toString() {
        return productName;
    }


}
