package seedu.address.ui;

import javafx.scene.control.TextField;

public class Suggestions {

    public void checkAllCommands(TextField inputTextField, TextField suggestionTextField, String currentText) {
        String enteredText = inputTextField.getText();

        for (Commands command : Commands.values()) {
            if (enteredText.trim().toLowerCase().startsWith(command.getCommand())) {
                // Show the suggestion text starting with the user's input
                FormatSuggestion commandSuggestion = new FormatSuggestion(command.getCommand(),
                        command.getExample(), command.getPrefix());
                String suggestionText = commandSuggestion.getRemainingFormat(enteredText);
                suggestionTextField.setText(enteredText + suggestionText);
                return;
            }
        }
        suggestionTextField.setText(enteredText);
    }
}
