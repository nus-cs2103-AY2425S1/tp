package seedu.address.model.restaurant;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the rating of the restaurant from the user.
 * Guarantees: immutable; is valid as declared in {@link #isValidRating(Integer)}
 */
public class Rating {
    public static final String MESSAGE_CONSTRAINTS = "Price should be a non-negative integer, between 0 to 10";
    public final Integer value;

    /**
     * Constructs a {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rating(Integer rating) {
        if (rating != null) {
            checkArgument(isValidRating(rating), MESSAGE_CONSTRAINTS);
        }
        this.value = rating;
    }

    /**
     * Get String value of Rating
     *
     * @return value in String.
     */
    public String getStringValue() {
        return this.value == null ? "No Rating" : this.value.toString();
    }

    /**
     * Returns true if a given a valid rating.
     */
    public static boolean isValidRating(Integer rating) {
        return rating >= 0 && rating <= 10;
    }

    /**
     * Returns a Price object based on the string value of the priceString.
     * @param ratingString the string representation of the price
     * @return a Price object with the parsed value
     * @throws IllegalArgumentException if the input is invalid
     */
    public static Rating fromString(String ratingString) {
        // Check if the input is null or empty
        if (ratingString == null || ratingString.isEmpty()) {
            return new Rating(null);
        }

        try {
            String cleanedString = ratingString.trim();
            int value = Integer.parseInt(cleanedString);
            return new Rating(value);
        } catch (NumberFormatException e) {
            // Handle cases where the string cannot be converted to a valid number
            throw new IllegalArgumentException("Invalid price format: " + ratingString, e);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public int hashCode() {
        return value.hashCode();
    }
}
