package seedu.address.model.tag;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Locale;

import seedu.address.model.tag.enums.Level;


/**
 * Package-private class. Represents a Tag with a difficulty level.
 * This tag is used to categorize items by difficulty,
 * with possible levels being "low", "medium", or "high".
 */
public class DifficultyTag extends Tag {

    public static final String MESSAGE_CONSTRAINTS = "Difficulty level needs to be either 'low', 'medium' or 'high'";
    private String difficultyLevel;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     * @param difficultyLevel A difficultyLevel of either "low", "medium" or "high".
     */
    DifficultyTag(String tagName, String difficultyLevel) {
        super(tagName);
        checkArgument(isValidDifficulty(difficultyLevel), MESSAGE_CONSTRAINTS);
        this.difficultyLevel = difficultyLevel.toUpperCase();
    }

    /**
     * Returns true if the difficulty level is valid, checks based on ENUM class
     * Valid levels are "low", "medium", or "high".
     *
     * @param difficultyLevel The difficulty level to do validation.
     * @return true if the difficulty level is "low", "medium", or "high".
     */
    private static boolean isValidDifficulty(String difficultyLevel) {
        try {
            // Attempt to convert the input string to a valid Level enum value.
            Level.valueOf(difficultyLevel.toUpperCase(Locale.ROOT));
            return true;
        } catch (IllegalArgumentException e) {
            return false; // If the conversion fails, the value is invalid.
        }
    }


    @Override
    public String toString() {
        return "Difficulty:" + this.difficultyLevel;
    }
}
