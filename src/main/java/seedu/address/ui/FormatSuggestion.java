package seedu.address.ui;
public class FormatSuggestion {
    private final String commandName;
    private final String formatExample;
    private final String[] formatPrefixes;
    private final String[] formatParts;
    public FormatSuggestion(String commandName, String formatExample, String[] formatPrefixes) {
        this.commandName = commandName;
        this.formatExample = formatExample;
        this.formatPrefixes = formatPrefixes;
        this.formatParts = removeFirstWord(formatExample)
                .split("\\s+");
    }


    public String getRemainingFormat(String enteredText) {
        if (enteredText.trim().equals(commandName)) {
            return " " + removeFirstWord(formatExample);
        } else if (enteredText.length() > commandName.length()) {
            String typedAfterCommandName = enteredText.substring(commandName.length()).trim();
            String[] typedParts = typedAfterCommandName.split("\\s+");

            StringBuilder remainingFormat = new StringBuilder();

            // Check if the user has entered a number for INDEX
            boolean numberEntered = false;
            for (String typedPart : typedParts) {
                if (typedPart.matches("\\d+")) {
                    numberEntered = true;
                    break;
                }
            }

            for (String formatPart : formatParts) {
                // Skip INDEX part if a number was entered
                if (formatPart.contains("INDEX") && numberEntered) {
                    continue;
                }
                boolean matched = false;
                for (String typedPart : typedParts) {
                    if (typedPart.startsWith(getPrefix(formatPart.trim().replace("[", "").replace("]", "")))) {
                        matched = true;
                        break;
                    }
                }
                if (!matched) {
                    // Add a single space before each remaining part
                    remainingFormat.append(" ").append(formatPart.trim());
                }
            }

            return remainingFormat.toString();
        }
        return "";
    }


    private String getPrefix(String part) {
        for (String prefix : formatPrefixes) {
            if (part.startsWith(prefix)) {
                return prefix;
            }
        }
        return part;
    }

    // Method to remove the first word
    public static String removeFirstWord(String input) {
        String[] words = input.trim().split("\\s+", 2); // Split into two parts
        if (words.length > 1) {
            return words[1]; // Return the string without the first word
        } else {
            return ""; // If there's only one word or the input is empty, return an empty string
        }
    }
}
