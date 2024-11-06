package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * A CommandBox component that is part of the UI, allowing the user to input commands.
 * The CommandBox supports autocomplete functionality for command input, including autocompleting
 * command words and parameters, and displaying suggestions for the current command context.
 *
 * <p>
 * Users can press the Control key for autocompletion when typing a command or parameter.
 * This component dynamically updates the suggestion based on the user's input and the available
 * commands in the command syntax map.
 * </p>
 *
 * <p>
 * The CommandBox listens for user input, and upon submission, it delegates the execution
 * of the command to the provided {@code CommandExecutor}.
 * </p>
 */
public class CommandBox extends UiPart<Region> {
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final Map<String, String> commandSyntaxMap = new HashMap<>();
    private String currentSuggestion = "";

    @FXML
    private TextField commandTextField;
    @FXML
    private Label suggestionLabel;

    private final CommandExecutor commandExecutor;

    static {
        // Initialize command syntax map
        commandSyntaxMap.put("add", "add n/NAME p/PHONE e/EMAIL a/ADDRESS ecname/EMERGENCY_CONTACT_NAME "
                + "ecphone/EMERGENCY_CONTACT_PHONE ecrs/EMERGENCY_CONTACT_RELATIONSHIP "
                + "dname/DOCTOR_NAME dphone/DOCTOR_PHONE demail/DOCTOR_EMAIL t/TAG");
        commandSyntaxMap.put("clear", "clear");
        commandSyntaxMap.put("delete", "delete INDEX");
        commandSyntaxMap.put("edit", "edit INDEX n/NAME p/PHONE e/EMAIL a/ADDRESS ec/ECINDEX "
                + "ecname/EMERGENCY_CONTACT_NAME ecrs/EMERGENCY_CONTACT_RELATIONSHIP dname/DOCTOR_NAME"
                + " dphone/DOCTOR_PHONE demail/DOCTOR_EMAIL t/TAG");
        commandSyntaxMap.put("find", "find KEYWORD [MORE_KEYWORDS]");
        commandSyntaxMap.put("undo", "undo");
        commandSyntaxMap.put("redo", "redo");
        commandSyntaxMap.put("list", "list");
        commandSyntaxMap.put("help", "help");
    }

    /**
     * Creates a CommandBox component that allows users to input and execute commands.
     * It also provides autocomplete suggestions for commands and parameters.
     *
     * @param commandExecutor The executor responsible for executing commands input by the user.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;

        // Handle text changes
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            setStyleToDefault();
            handleTextChanged(newValue);
        });

        // Handle Control key release for autocomplete
        commandTextField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.CONTROL) {
                handleControlCompletion();
                event.consume();
            }
        });
    }

    /**
     * Handles text changes in the command box and updates suggestions dynamically.
     */
    private void handleTextChanged(String input) {
        if (input.trim().isEmpty()) {
            suggestionLabel.setVisible(false);
            return;
        }

        String[] words = input.split("\\s+");
        String commandPrefix = words[0];

        // Check if the command prefix matches any command in the commandSyntaxMap
        for (String cmd : commandSyntaxMap.keySet()) {
            if (cmd.startsWith(commandPrefix)) {
                String fullSyntax = commandSyntaxMap.get(cmd);
                suggestionLabel.setText(fullSyntax);
                suggestionLabel.setVisible(true);
                currentSuggestion = fullSyntax;

                if (cmd.equals(commandPrefix)) {
                    processParameters(input, fullSyntax, words);
                }
                return;
            }
        }
        suggestionLabel.setVisible(false);
    }

    private void processParameters(String input, String fullSyntax, String[] words) {
        StringBuilder consumedSyntax = new StringBuilder(words[0]);
        String[] syntaxParts = fullSyntax.split("\\s+");

        int syntaxIndex = 1; // Start after command word
        boolean hideSuggestion = false;

        // Split input by spaces to properly handle multi-word parameters
        String[] inputParts = input.split("\\s+");
        String currentPrefix = null;
        StringBuilder currentValue = new StringBuilder();

        // Skip the command word and process input parameters
        for (int i = 1; i < inputParts.length; i++) {
            String part = inputParts[i];

            // Check if this is a new parameter prefix
            if (part.contains("/")) {
                // If we had a previous parameter, add it to consumed syntax
                if (currentPrefix != null) {
                    consumedSyntax.append(" ").append(currentPrefix).append(currentValue);
                    syntaxIndex++;
                }

                // Start a new parameter
                currentPrefix = part.substring(0, part.indexOf("/") + 1);
                currentValue = new StringBuilder(part.substring(part.indexOf("/") + 1));

                // Skip this parameter in the syntax index
                while (syntaxIndex < syntaxParts.length && syntaxParts[syntaxIndex].startsWith(currentPrefix)) {
                    syntaxIndex++;
                }

                // Hide suggestion while typing parameter
                if (!input.endsWith(" ")) {
                    hideSuggestion = true;
                    suggestionLabel.setVisible(false);
                    return;
                }
            } else if (currentPrefix != null) {
                // Continue building the current parameter value
                currentValue.append(" ").append(part);
            }

            // Move to the next parameter if we're at a valid breakpoint
            if (currentPrefix != null && input.endsWith(" ")) {
                boolean hasNextParameter = false;
                if (i + 1 < inputParts.length) {
                    hasNextParameter = inputParts[i + 1].contains("/");
                }

                if (hasNextParameter) {
                    consumedSyntax.append(" ").append(currentPrefix).append(currentValue);
                    currentPrefix = null;
                    currentValue = new StringBuilder();
                    syntaxIndex++;
                }
            }
        }

        // Add the current parameter if we were building one
        if (currentPrefix != null) {
            consumedSyntax.append(" ").append(currentPrefix).append(currentValue);
        }

        // Prepare remaining syntax to append as a suggestion
        StringBuilder remainingSyntax = new StringBuilder();
        if (!hideSuggestion && syntaxIndex < syntaxParts.length) {
            if (input.endsWith(" ") && (currentPrefix == null || currentValue.length() > 0)) {
                String nextParam = syntaxParts[syntaxIndex];
                remainingSyntax.append(" ").append(nextParam);

                // Add remaining parameters
                for (int i = syntaxIndex + 1; i < syntaxParts.length; i++) {
                    remainingSyntax.append(" ").append(syntaxParts[i]);
                }
            }
        }

        // Update the suggestion label
        if (!hideSuggestion && remainingSyntax.length() > 0) {
            suggestionLabel.setText(consumedSyntax.toString() + remainingSyntax);
            suggestionLabel.setVisible(true);
        } else {
            suggestionLabel.setVisible(false);
        }
    }

    /**
     * Handles autocompletion based on the current input when the Control key is pressed.
     */
    private void handleControlCompletion() {
        String input = commandTextField.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        String[] words = input.split("\\s+");
        String command = words[0];

        // If the command has parameters in the syntax map
        if (commandSyntaxMap.containsKey(command)) {
            String fullSyntax = commandSyntaxMap.get(command);
            String[] syntaxParts = fullSyntax.split("\\s+");

            // Track already entered parameters to determine the next suggestion
            int syntaxIndex = 1; // Start after the command word
            StringBuilder consumedSyntax = new StringBuilder(command);

            // Determine which parts of the syntax have already been entered
            for (int i = 1; i < words.length; i++) {
                String part = words[i];
                if (syntaxIndex < syntaxParts.length && syntaxParts[syntaxIndex].equals(part)) {
                    // If the input matches the current expected syntax part, move forward
                    consumedSyntax.append(" ").append(part);
                    syntaxIndex++;
                } else if (part.contains("/")) {
                    // If the input is a parameter with a slash, move forward in the syntax parts
                    consumedSyntax.append(" ").append(part);
                    syntaxIndex++;
                }
            }

            // Skip already entered parameters
            while (syntaxIndex < syntaxParts.length && isParameterAlreadyEntered(input, syntaxParts[syntaxIndex])) {
                syntaxIndex++;
            }

            // If there are remaining parameters or words to suggest
            if (syntaxIndex < syntaxParts.length) {
                String nextPart = syntaxParts[syntaxIndex];
                String completion;

                if (nextPart.contains("/")) {
                    // It's a parameter, e.g., n/NAME, so autocomplete the prefix
                    completion = nextPart.substring(0, nextPart.indexOf("/") + 1);
                } else {
                    // It's a regular word like INDEX, so autocomplete the entire word
                    completion = nextPart;
                }

                // Add the next part to the input
                String newText = input + (input.endsWith(" ") ? "" : " ") + completion;
                commandTextField.setText(newText);
                commandTextField.positionCaret(newText.length());
                return;
            }
        }

        // Autocomplete for partially typed commands, if we are not inside parameter input
        for (String cmd : commandSyntaxMap.keySet()) {
            if (cmd.startsWith(command) && !cmd.equals(command)) {
                String newText = cmd;

                // Check if the command expects additional parameters based on its syntax definition
                String fullSyntax = commandSyntaxMap.get(cmd);
                if (fullSyntax.split("\\s+").length > 1) {
                    newText += " "; // Add trailing space if there are parameters expected
                }

                commandTextField.setText(newText);
                commandTextField.positionCaret(newText.length());
                currentSuggestion = fullSyntax;
                return;
            }
        }
    }

    private boolean isParameterAlreadyEntered(String input, String parameter) {
        String[] inputWords = input.split("\\s+");
        for (String word : inputWords) {
            if (word.startsWith(parameter.substring(0, parameter.indexOf("/") + 1))) {
                return true;
            }
        }
        return false;
    }



    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.isEmpty()) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
            suggestionLabel.setVisible(false);
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        if (!styleClass.contains(ERROR_STYLE_CLASS)) {
            styleClass.add(ERROR_STYLE_CLASS);
        }
    }

    /**
     * Functional interface for executing commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}
