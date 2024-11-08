package seedu.address.ui.commandpopup;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * A custom TextFlow that displays a command with its associated detail text.
 * It highlights the matching part of the command based on a filter and provides details for each command.
 */
public class CommandTextFlow extends TextFlow {
    private final String commandText;
    private final String detailText;

    /**
     * Constructs a CommandTextFlow object with the given command and filter.
     * Highlights the matching part of the command and displays the detailed explanation for the command.
     *
     * @param command The command to be displayed.
     * @param filter The filter text used to highlight matching parts of the command.
     */
    public CommandTextFlow(String command, String filter) {
        super();
        this.commandText = command;
        this.detailText = getDetailForCommand(command);
        // Style the command text with highlighting
        buildCommandText(command, filter);
        // Add separator
        getChildren().add(new Text("\n"));
        // Add detail text
        Text details = new Text(detailText);
        details.setFont(Font.font("Comfortaa", FontWeight.NORMAL, 14));
        details.setFill(Color.grayRgb(210));
        getChildren().add(details);
        this.setHeight(47);

        // Set padding for the entire flow
        setPadding(new Insets(2, 10, 2, 10));
    }

    /**
     * Builds the styled command text by highlighting the part that matches the filter.
     * Adds the styled text to the TextFlow.
     *
     * @param text The full command text.
     * @param filter The filter text to be highlighted in the command.
     */
    private void buildCommandText(String text, String filter) {
        int size = 18;
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());

        Text textFilter = new Text(text.substring(filterIndex, filterIndex + filter.length()));
        textFilter.setFont(Font.font("Comfortaa", FontWeight.BOLD, size));
        textFilter.setFill(Color.ORANGE);
        getChildren().add(textFilter);
        if (!text.toLowerCase().equals(filter.toLowerCase())) {
            Text textAfter = new Text(text.substring(filterIndex + filter.length()));
            textAfter.setFont(Font.font("Comfortaa", FontWeight.BOLD, size));
            textAfter.setFill(Color.WHITE);
            getChildren().add(textAfter);
        }

    }

    /**
     * Returns a detailed description of the command.
     * The description varies based on the specific command provided.
     *
     * @param command The command for which to retrieve details.
     * @return A string containing the detailed description of the command.
     */
    private String getDetailForCommand(String command) {
        // Switch statement to determine the detail text for each command
        return switch (command.toLowerCase()) {
        case "help" -> "help - Shows program help instructions and command summary";
        case "list" -> "list - Shows a list of all persons in the gamer address book";
        case "add" -> "add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [g/GAME]... [t/TAG]... [pt/TIME-TIME]...";
        case "edit" -> "edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [g/GAME]... "
                + "[t/TAG]... [pt/TIME-TIME]...";
        case "addgame" -> "addgame INDEX g/GAME [u/USERNAME] [s/SKILLLEVEL] [r/ROLE]";
        case "editgame" -> "editgame INDEX g/GAME [u/USERNAME] [s/SKILL_LEVEL] [r/ROLE]";
        case "deletegame" -> "deletegame INDEX g/GAME";
        case "favgame" -> "favgame INDEX g/GAME";
        case "unfavgame" -> "unfavgame INDEX g/GAME";
        case "find" -> "find KEYWORD [MORE_KEYWORDS]... - Finds persons whose names contain any of the keywords";
        case "findtime" -> "find TIME-TIME [TIME-TIME]... - Finds persons whose preferred time contains any TIMES";
        case "findgame" -> "findgame KEYWORD [MORE_KEYWORDS]... - "
                + "Finds persons whose games contain any of the keywords";
        case "delete" -> "delete INDEX - Deletes the specified person (e.g., delete 3)";
        case "clear" -> "clear - Clears all entries from the gamer address book";
        case "undo" -> "undo - Undoes the previous command (aside from save and load)";
        case "save" -> "save - Saves the gamer address book data";
        case "load" -> "load - Loads the gamer address book data";
        case "exit" -> "exit - Closes the gamer address book";
        default -> "No additional information available";
        };
    }

    // Method to get just the command text
    public String getCommandText() {
        return commandText;
    }

    // Method to get the detail text
    public String getDetailText() {
        return detailText;
    }
}
