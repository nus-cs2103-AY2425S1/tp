package seedu.address.model.tag;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Locale;

import seedu.address.model.tag.enums.Level;

/**
 * Package-private class. Represents a tag indicating the work-life balance level of a company or position.
 * This tag ensures that the provided work-life balance level matches one of the
 * with possible levels being "low", "medium", or "high".
 */
public class WorkLifeBalanceTag extends Tag {

    public static final String MESSAGE_CONSTRAINTS =
            "Work Life Balance level needs to be either Low, Medium, or High";
    private String workLifeBalanceLevel;

    /**
     * Constructs a {@code WorkLifeBalanceTag} with the given tag name and work-life balance level.
     *
     * @param tagName The name of the tag.
     * @param workLifeBalanceLevel The work-life balance level (e.g., "Low", "Medium", "High").
     * @throws IllegalArgumentException if the provided level is not a valid {@link Level} value.
     */
    WorkLifeBalanceTag(String tagName, String workLifeBalanceLevel)
            throws IllegalArgumentException {
        super(tagName);
        checkArgument(isValidWorkLifeBalance(workLifeBalanceLevel), MESSAGE_CONSTRAINTS);
        this.workLifeBalanceLevel = workLifeBalanceLevel.toUpperCase();
    }

    /**
     * Checks if the provided work-life balance level is valid.
     * The level must match one of the {@link Level} enum values.
     *
     * @param workLifeBalanceLevel The input string representing the work-life balance level.
     * @return {@code true} if the input string is a valid level; {@code false} otherwise.
     */
    private static boolean isValidWorkLifeBalance(String workLifeBalanceLevel) {
        try {
            // Attempt to convert the input string to a valid Level enum value.
            Level.valueOf(workLifeBalanceLevel.toUpperCase(Locale.ROOT));
            return true;
        } catch (IllegalArgumentException e) {
            return false; // If the conversion fails, the value is invalid.
        }
    }

    @Override
    public String toString() {
        return "WLB:" + this.workLifeBalanceLevel;
    }
}
