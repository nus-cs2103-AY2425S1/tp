package seedu.address.model.tag;

import java.util.Locale;
import java.util.Objects;

/**
 * Acts like a builder class where it will output
 * a corresponding tag type according to whatever the user input is.
 * Guarantees: immutable;.
 */
public class TagBuilder {
    /**
     * Constructs a TagBuilder.
     */
    public TagBuilder() {
    }

    /**
     * Checks the user input tag string and returns the correct type of Tag.
     *
     * @param userInput The input string for the tag.
     * @return A corresponding Tag type (e.g., DifficultyTag, SalaryTag, CustomTag).
     */
    public Tag build(String userInput) throws IllegalArgumentException {
        if (Objects.equals(userInput, "")) {
            throw new IllegalArgumentException("Tag fields cannot be empty!");
        }
        // Split the string using the underscore as the delimiter
        String[] parts = userInput.split("_");
        // Check the first word
        if (parts.length > 1) {
            String firstWord = parts[0].toLowerCase(Locale.ROOT);
            String tagValue = parts[1];
            switch (firstWord) {
            case "difficulty":
                return new DifficultyTag("Difficulty", tagValue);
            case "salary":
                return new SalaryTag("Salary", tagValue);
            case "wlb":
                return new WorkLifeBalanceTag("WLB", tagValue);
            case "period":
                int year;
                try {
                    year = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;
                } catch (NumberFormatException e) {
                    year = 0;
                }
                return new PeriodTag("Period", tagValue, year);
            default:
                return new Tag(userInput);
            }
        }
        // If no specific type, return a Normal Tag
        return new Tag(userInput);
    }


}
