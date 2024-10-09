package seedu.address.model.tag;

import java.util.Locale;

import static seedu.address.commons.util.AppUtil.checkArgument;

public class DifficultyTag extends Tag{

    public static final String MESSAGE_CONSTRAINTS = "Difficulty level needs to be a number";

    private String difficultyLevel;
    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     * @param difficultyLevel A difficultyLevel of either "low", "medium" or "high".
     */
    public DifficultyTag(String tagName,String difficultyLevel) {
        super(tagName);
        checkArgument(isValidDifficulty(difficultyLevel), MESSAGE_CONSTRAINTS);
        this.difficultyLevel = difficultyLevel;
    }

    public static boolean isValidDifficulty(String difficultyLevel) {
        String difficultyLevelLowerCase = difficultyLevel.toLowerCase(Locale.ROOT);
        boolean difficultyLevelIsValid = difficultyLevelLowerCase.equals("low")
                || difficultyLevelLowerCase.equals("medium")
                || difficultyLevelLowerCase.equals("high");

        return difficultyLevelIsValid;
    }

    @Override
    public String toString() {
        return "Difficulty: " + this.difficultyLevel;
    }
}
