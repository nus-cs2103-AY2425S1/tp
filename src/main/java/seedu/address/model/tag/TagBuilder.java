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
            String levelValue = parts[1];
            switch (firstWord) {
            case "difficulty":
                return new DifficultyTag("Difficulty", levelValue);
            case "salary":
                return new SalaryTag("Salary", levelValue);
            default:
                throw new IllegalArgumentException("Unknown tag type: " + firstWord);
            }
        }
        // If no specific type, return a CustomTag
        return new Tag(userInput);
    }


}
