package seedu.address.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final Map<String, String> commandSyntaxMap = new HashMap<>();
    private static final Map<String, List<String>> commandParametersMap = new HashMap<>();
    private static final String TAG_PREFIX = "t/";
    private final CommandExecutor commandExecutor;
    private String currentSuggestion = ""; // Tracks current suggestion part
    private int tabIndex = 0; // Tracks the current position for tab completion

    @FXML
    private TextField commandTextField; //input field
    @FXML
    private Label suggestionLabel; // For greyed-out suggestion -> refer to fxml file

    static {
        commandSyntaxMap.put("add", "add n/NAME p/PHONE e/EMAIL a/ADDRESS ecname/EMERGENCY CONTACT NAME "
                + "ecphone/EMERGENCY CONTACT PHONE ecrs/EMERGENCY CONTACT RELATIONSHIP "
                + "dname/DOCTOR NAME dphone/DOCTOR PHONE demail/DOCTOR EMAIL t/TAG");
        commandSyntaxMap.put("clear", "clear");
        commandSyntaxMap.put("delete", "delete INDEX");
        commandSyntaxMap.put("edit", "edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] "
                + "[edu/EDUCATION] [tele/TELEGRAM_HANDLE]");
        commandSyntaxMap.put("find", "find KEYWORD [MORE_KEYWORDS]");
        commandSyntaxMap.put("undo", "undo");
        commandSyntaxMap.put("redo", "redo");
        commandSyntaxMap.put("list", "list");
        commandSyntaxMap.put("help", "help");
        initializeCommandParameters();
    }

    /**
     * Creates a CommandBox with the given CommandExecutor.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Clear error style whenever text changes
            setStyleToDefault();

            // Handle text changes for suggestions
            handleTextChanged(newValue);

            // Reset tab index when user types
            if (!oldValue.equals(newValue)) {
                tabIndex = 0;
            }
        });

        // Handle space and tab press for completion
        commandTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                handleSpaceCompletion();
                event.consume();
            } else if (event.getCode() == KeyCode.TAB) {
                handleTabCompletion();
                event.consume();
            }
        });
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        if (!styleClass.contains(ERROR_STYLE_CLASS)) {
            styleClass.add(ERROR_STYLE_CLASS);
        }
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.setText(""); // Clear after successful command execution
            suggestionLabel.setVisible(false); // Hide the suggestion after execution
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Extract all parameters ending with "/"
     */
    private static void initializeCommandParameters() {
        for (Map.Entry<String, String> entry : commandSyntaxMap.entrySet()) {
            String command = entry.getKey();
            String syntax = entry.getValue();

            List<String> parameters = new ArrayList<>();
            String[] parts = syntax.split(" ");

            for (String part : parts) {
                if (part.contains("/") && part.indexOf("/") > 0) {
                    // Skip the [...] notation for tags
                    if (!part.startsWith("[")) {
                        String prefix = part.substring(0, part.indexOf("/") + 1);
                        parameters.add(prefix);
                    }
                }
            }

            // Add tag parameter separately for "add" command
            if (command.equals("add")) {
                parameters.add(TAG_PREFIX);
            }

            commandParametersMap.put(command, parameters);
        }
    }

    /**
     * This method helps with auto-completion of command parameters when the user presses the Tab key.
     * It takes the input string (the current command and any parameters the user has typed) and
     * completes the next parameter by examining what has already been typed.
     * This will be used to suggest commands as the user types.
     */
    private void handleParameterCompletion(String input) {
        String[] parts = input.split("\\s+", 2);
        String command = parts[0];

        if (commandParametersMap.containsKey(command)) {
            List<String> parameters = commandParametersMap.get(command);
            String currentInput = parts.length > 1 ? parts[1] : "";

            // Find current parameter index
            int currentParamIndex = -1;
            for (int i = 0; i < parameters.size(); i++) {
                if (currentInput.endsWith(parameters.get(i))) {
                    currentParamIndex = i;
                    break;
                }
            }

            // Check if we're in the middle of a parameter
            for (int i = 0; i < parameters.size(); i++) {
                String param = parameters.get(i);
                if (currentInput.contains(param)) {
                    String afterParam = currentInput.substring(
                            currentInput.indexOf(param) + param.length()
                    );
                    if (!afterParam.contains("/")) {
                        currentParamIndex = i;
                        break;
                    }
                }
            }

            // Move to next parameter or tag
            if (currentParamIndex < parameters.size() - 1) {
                StringBuilder newText = new StringBuilder(command + " ");

                // Keep existing parameters
                if (parts.length > 1) {
                    String[] existingParams = parts[1].split("\\s+");
                    for (String param : existingParams) {
                        if (!param.isEmpty() && !param.equals(parameters.get(currentParamIndex + 1))) {
                            newText.append(param).append(" ");
                        }
                    }
                }

                // Add next parameter
                newText.append(parameters.get(currentParamIndex + 1));

                commandTextField.setText(newText.toString());
                commandTextField.positionCaret(commandTextField.getText().length());
            }
        }
    }

    private void handleTextChanged(String input) {
        // Clear suggestion if input is empty
        if (input.trim().isEmpty()) {
            suggestionLabel.setVisible(false);
            return;
        }

        String[] parts = input.trim().split("\\s+");
        String command = parts[0];

        // If we're in parameter mode (after a valid command)
        if (input.contains(" ") && commandSyntaxMap.containsKey(command)) {
            handleParameterInput(input, command);
        } else if (!input.contains(" ")) {
            // We're still typing the command
            handleCommandSuggestion(input, command);
        }
    }

    private void handleParameterInput(String input, String command) {
        String[] inputParts = input.split("\\s+");
        String lastPart = inputParts[inputParts.length - 1];

        // If the last part ends with slash or contains slash and no space after it,
        // hide suggestion until space is added
        if (lastPart.endsWith("/") || (lastPart.contains("/") && !input.endsWith(" "))) {
            suggestionLabel.setVisible(false);
            return;
        }

        // If input ends with space, show next parameter suggestion
        if (input.endsWith(" ")) {
            handleNextParameterSuggestion(input, command, inputParts);
            return;
        }

        // Hide suggestion if we're not in any valid suggestion state
        suggestionLabel.setVisible(false);
    }

    private void handleNextParameterSuggestion(String input, String command, String[] inputParts) {
        List<String> parameters = commandParametersMap.get(command);
        Set<String> usedParams = new HashSet<>();

        // Find used parameters
        for (String part : inputParts) {
            if (part.contains("/")) {
                String prefix = part.substring(0, part.indexOf("/") + 1);
                // Don't add tag prefix to used parameters since it can be used multiple times
                if (!prefix.equals(TAG_PREFIX)) {
                    usedParams.add(prefix);
                }
            }
        }

        // Special handling for tags - always suggest as an option after other parameters
        boolean hasRequiredParams = usedParams.size() == parameters.size() - 1; // -1 for tag parameter
        boolean lastPartIsTag = inputParts[inputParts.length - 1].startsWith(TAG_PREFIX);

        // Find next unused parameter or suggest tag
        for (String param : parameters) {
            if (!usedParams.contains(param) || (param.equals(TAG_PREFIX) && (hasRequiredParams || lastPartIsTag))) {
                String fullSyntax = commandSyntaxMap.get(command);
                String paramDescription;
                if (param.equals(TAG_PREFIX) && (hasRequiredParams || lastPartIsTag)) {
                    paramDescription = "TAG";
                } else {
                    paramDescription = getParameterDescription(fullSyntax, param);
                }
                suggestionLabel.setText(input + param + paramDescription);
                suggestionLabel.setVisible(true);
                return;
            }
        }

        // If all required parameters are used, suggest tag
        if (hasRequiredParams) {
            suggestionLabel.setText(input + TAG_PREFIX + "TAG");
            suggestionLabel.setVisible(true);
            return;
        }

        // Hide suggestion if all parameters are used
        suggestionLabel.setVisible(false);
    }

    private void handleCommandSuggestion(String input, String currentInput) {
        boolean foundMatch = false;

        for (String command : commandSyntaxMap.keySet()) {
            if (command.startsWith(currentInput.toLowerCase())) {
                String fullSyntax = commandSyntaxMap.get(command);
                String remainingSuggestion = fullSyntax.substring(currentInput.length());

                if (currentInput.equals(command) && !remainingSuggestion.startsWith(" ")) {
                    remainingSuggestion = " " + remainingSuggestion;
                } else if (!currentInput.equals(command) && remainingSuggestion.startsWith(" ")) {
                    remainingSuggestion = remainingSuggestion.substring(1);
                }

                suggestionLabel.setText(input + remainingSuggestion);
                suggestionLabel.setVisible(true);
                currentSuggestion = fullSyntax;
                foundMatch = true;
                break;
            }
        }

        if (!foundMatch) {
            suggestionLabel.setVisible(false);
            currentSuggestion = "";
        }
    }

    /**
     * Handles tab completion based on current input state
     */
    private void handleTabCompletion() {
        String input = commandTextField.getText().trim();

        if (input.isEmpty()) {
            return;
        }

        if (!input.contains(" ")) {
            // Only handle command completion if there's a valid suggestion
            if (!currentSuggestion.isEmpty() && suggestionLabel.isVisible()) {
                handleCommandCompletion(input);
            }
        } else {
            handleParameterCompletion(input);
        }
    }

    private void handleCommandCompletion(String input) {
        // Only complete if the input matches the beginning of a valid command
        boolean validCommand = false;
        for (String command : commandSyntaxMap.keySet()) {
            if (command.startsWith(input.toLowerCase())) {
                List<String> parameters = commandParametersMap.get(command);
                if (parameters.isEmpty()) {
                    // For commands without parameters (like delete, list, clear)
                    commandTextField.setText(command + " ");
                    commandTextField.positionCaret(commandTextField.getText().length());
                    currentSuggestion = commandSyntaxMap.get(command);
                    validCommand = true;
                } else {
                    // For commands with parameters
                    commandTextField.setText(command + " " + parameters.get(0));
                    commandTextField.positionCaret(commandTextField.getText().length());
                    currentSuggestion = commandSyntaxMap.get(command);
                    tabIndex = 1;
                    validCommand = true;
                }
                break;
            }
        }

        // If no valid command matches, clear suggestion
        if (!validCommand) {
            suggestionLabel.setVisible(false);
            currentSuggestion = "";
        }
    }

    /**
     * Extracts the parameter description from the full syntax
     */
    private String getParameterDescription(String syntax, String paramPrefix) {
        String[] parts = syntax.split(" ");
        for (String part : parts) {
            if (part.startsWith(paramPrefix)) {
                return part.substring(paramPrefix.length());
            }
        }
        return "VALUE";
    }

    /**
     * Handles space completion to move to the next part of the suggestion.
     * After a space is detected, it uses the slash as a pointer to pinpoint
     * where the suggestion should resume from.
     */
    private void handleSpaceCompletion() {
        String input = commandTextField.getText().trim();

        // Do nothing if the input is empty
        if (input.isEmpty()) {
            return;
        }
        // Parse input to find the last '/' to append the remaining suggestion
        int lastSlashIndex = input.lastIndexOf('/');
        if (lastSlashIndex != -1) {
            // Find the next part of the suggestion after the last slash
            String currentInputPart = input.substring(0, lastSlashIndex + 1); // Get everything before the last slash
            String remainingSuggestion = currentSuggestion.substring(currentInputPart.length()).trim();

            // Concat current input append remaining suggestion after the '  '
            suggestionLabel.setText(input + " " + remainingSuggestion);
            suggestionLabel.setVisible(true);
        }
    }


    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}
