package seedu.address.model.product;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Product's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ProductName {
    public static final String MESSAGE_EMPTY_NAME = "Product name cannot be blank.";

    public static final String MESSAGE_NON_ALPHANUMERIC_NAME =
            "Product names should only contain alphanumeric characters and spaces.";
    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^/][^/]*";

    private final String originalName;
    private final String normalizedName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public ProductName(String name) {
        requireNonNull(name);

        // Store the original name as provided by the user
        originalName = name.trim().replaceAll("\\s+", " ");

        // Normalize the name for internal use (lowercase)
        normalizedName = originalName.toLowerCase();

        if (!isValidName(normalizedName)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getNormalizedName() {
        return normalizedName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProductName // instanceof handles nulls
                && normalizedName.equals(((ProductName) other).normalizedName));
    }

    @Override
    public int hashCode() {
        return normalizedName.hashCode();
    }

    @Override
    public String toString() {
        return originalName;
    }

}
