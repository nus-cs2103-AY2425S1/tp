package seedu.address.ui;

/**
 * Provides functionality for generating command suggestions based on user input.
 * This class checks the entered text against predefined commands and
 * generates suggestions for completing the command format.
 */
public class Suggestions {

    /**
     * Checks all available commands and generates an appropriate suggestion
     * based on the user's input.
     *
     * @param enteredText The text entered by the user in the command input field.
     * @return A string containing the entered text followed by the suggestion,
     *         or just the entered text if no matching command is found.
     */
    public String checkAllCommands(String enteredText) {
        // Iterate through all defined commands to find matches
        for (Commands command : Commands.values()) {
            String firstWord = enteredText.split("\\s+")[0];
            if (firstWord.equals(command.getCommand())) {
                // Generate suggestion based on the matching command
                FormatSuggestion commandSuggestion = new FormatSuggestion(command.getCommand(),
                        command.getExample(), command.getPrefix());
                String suggestionText = commandSuggestion.getRemainingFormat(enteredText);
                return enteredText + suggestionText;
            }
        }
        // If no command matches, return the entered text as is
        return enteredText;
    }
}
