package seedu.address.ui;

/**
 * Represents a format suggestion for a command in the application.
 * This class provides functionality to generate remaining command formats
 * based on user input and predefined command formats.
 */
public class FormatSuggestion {
    private final String commandName;
    private final String formatExample;
    private final String[] formatPrefixes;
    private final String[] formatParts;

    /**
     * Constructs a FormatSuggestion with the specified command name,
     * example format, and parameter prefixes.
     *
     * @param commandName   The name of the command.
     * @param formatExample The example format for the command.
     * @param formatPrefixes The prefixes associated with the command's parameters.
     */
    public FormatSuggestion(String commandName, String formatExample, String[] formatPrefixes) {
        this.commandName = commandName;
        this.formatExample = formatExample;
        this.formatPrefixes = formatPrefixes;
        this.formatParts = removeFirstWord(formatExample).split("\\s+");
    }

    /**
     * Returns the remaining format parts that the user needs to enter based
     * on the input provided.
     *
     * @param enteredText The text entered by the user.
     * @return A string representing the remaining format parts.
     */
    public String getRemainingFormat(String enteredText) {
        if (enteredText.trim().equals(commandName)) {
            return " " + removeFirstWord(formatExample);
        } else if (enteredText.length() > commandName.length()) {
            String typedAfterCommandName = enteredText.substring(commandName.length()).trim();
            String[] typedParts = typedAfterCommandName.split("\\s+");

            StringBuilder remainingFormat = new StringBuilder();

            // check if user type number for INDEX
            if (formatParts[0].equals("INDEX") && !typedParts[0].matches("\\d+")) {
                return "";
            }

            // check if user type alphabet for NAME
            if (formatParts[0].equals("NAME") && !typedParts[0].matches("[a-zA-Z]+")) {
                return "";
            }

            // check if user type number or alphabet for INDEX/NAME
            if (formatParts[0].equals("INDEX/NAME") && !typedParts[0].matches("^[a-zA-Z0-9_.-]*$")) {
                return "";
            }


            for (String formatPart : formatParts) {
                boolean matched = false;
                if (formatPart.equals("INDEX") || formatPart.equals("NAME") || formatPart.equals("INDEX/NAME")) {
                    // Skip the INDEX and NAME part if a number has already been entered
                    continue;
                }

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

    /**
     * Returns the prefix for a given format part. If no prefix matches,
     * the original part is returned.
     *
     * @param part The format part to check for prefixes.
     * @return The matched prefix or the original part if no prefix matches.
     */
    private String getPrefix(String part) {
        for (String prefix : formatPrefixes) {
            if (part.startsWith(prefix)) {
                return prefix;
            }
        }
        return part;
    }

    /**
     * Removes the first word from the given input string.
     *
     * @param input The input string from which the first word will be removed.
     * @return The input string without the first word, or an empty string
     *         if the input has one word or is empty.
     */
    public static String removeFirstWord(String input) {
        String[] words = input.trim().split("\\s+", 2); // Split into two parts
        if (words.length > 1) {
            return words[1]; // Return the string without the first word
        } else {
            return ""; // If there's only one word or the input is empty, return an empty string
        }
    }
}
