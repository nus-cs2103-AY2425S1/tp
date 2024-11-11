package spleetwaise.transaction.model.transaction;

import static java.util.Objects.requireNonNull;

import spleetwaise.commons.util.AppUtil;

/**
 * Represents a Transaction's categories in the transaction book.
 */
public class Category {

    public static final String MESSAGE_CONSTRAINTS = "Categories should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String category;

    /**
     * Constructs a {@code Category}.
     *
     * @param category A valid tag name.
     */
    public Category(String category) {
        requireNonNull(category);
        String trimmedCategory = category.trim();
        AppUtil.checkArgument(isValidCatName(trimmedCategory), MESSAGE_CONSTRAINTS);
        this.category = trimmedCategory;
    }

    /**
     * Returns true if a given string is a valid category format.
     */
    public static boolean isValidCatName(String test) {
        return test.trim().matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Category otherCategory)) {
            return false;
        }

        return category.equals(otherCategory.category);
    }

    @Override
    public int hashCode() {
        return category.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return category;
    }
}
