package seedu.address.model.tag;

/**
 * Acts like a builder class where it will output
 * a corresponding tag type according to whatever the user input is.
 * Guarantees: immutable;.
 */
public class TagBuilder {
    /**
     * Constructs a {@code Tag}.
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
        // Split the string using the underscore as the delimiter
        String[] parts = userInput.split("_");
        // Check the first word
        if (parts.length > 1) {
            String firstWord = parts[0];
            String tagValue = parts[1];
            switch (firstWord) {
            case "difficulty":
                return new DifficultyTag("Difficulty", tagValue);
            case "salary":
                return new SalaryTag("Salary", tagValue);
            case "wlb":
                return new WorkLifeBalanceTag("WLB", tagValue);
            case "period":
                int year = Integer.parseInt(parts.length > 2 ? parts[2] : "0"); // Default year if missing
                return new PeriodTag("Period", tagValue, year);
            default:
                throw new IllegalArgumentException("Unknown tag type: " + firstWord);
            }
        }
        // If no specific type, return a CustomTag
        return new Tag(userInput);
    }


}
