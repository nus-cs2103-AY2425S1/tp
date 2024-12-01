package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * A CommandBox component that is part of the UI, allowing the user to input commands.
 * This version provides simple word-by-word autocomplete suggestions based on the command syntax map.
 */
public class CommandBox extends UiPart<Region> {
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final Map<String, String> commandSyntaxMap = new HashMap<>();
    private static final Map<String, List<String>> commandParameterOrder = new HashMap<>();
    private static final Map<String, String> parameterMap = new HashMap<>();
    private static final String DEFAULT_STYLE = "-fx-font-family: 'Segoe UI'; -fx-font-size: 13pt;"
            + " -fx-text-fill: white;";
    private static final String INPUT_NEEDED_STYLE = "-fx-font-family: 'Segoe UI'; -fx-font-size: 13pt;"
            + " -fx-text-fill: #fb5252;";

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
        commandSyntaxMap.put("addec", "addec INDEX ecname/EMERGENCY_CONTACT_NAME ecphone/EMERGENCY_CONTACT_PHONE "
                + "ecrs/EMERGENCY_CONTACT_RELATIONSHIP");
        commandSyntaxMap.put("archive", "archive DESCRIPTION");
        commandSyntaxMap.put("clear", "clear");
        commandSyntaxMap.put("delete", "delete INDEX ec/EMERGENCY_CONTACT_INDEX");
        commandSyntaxMap.put("edit", "edit INDEX n/NAME p/PHONE e/EMAIL a/ADDRESS ec/EMERGENCY_CONTACT_INDEX "
                + "ecname/EMERGENCY_CONTACT_NAME ecrs/EMERGENCY_CONTACT_RELATIONSHIP dname/DOCTOR_NAME "
                + "dphone/DOCTOR_PHONE demail/DOCTOR_EMAIL t/TAG");
        commandSyntaxMap.put("find", "find KEYWORD MORE_KEYWORDS");
        commandSyntaxMap.put("finddoc", "finddoc KEYWORD MORE_KEYWORDS");
        commandSyntaxMap.put("help", "help");
        commandSyntaxMap.put("list", "list SORT_ORDER");
        commandSyntaxMap.put("listarchives", "listarchives");
        commandSyntaxMap.put("loadarchive", "loadarchive FILE_NAME");
        commandSyntaxMap.put("deletearchive", "deletearchive FILE_NAME");
        commandSyntaxMap.put("redo", "redo");
        commandSyntaxMap.put("undo", "undo");

        // Initialize parameter mappings
        parameterMap.put("n/", "n/NAME");
        parameterMap.put("p/", "p/PHONE");
        parameterMap.put("e/", "e/EMAIL");
        parameterMap.put("a/", "a/ADDRESS");
        parameterMap.put("ecname/", "ecname/EMERGENCY_CONTACT_NAME");
        parameterMap.put("ecphone/", "ecphone/EMERGENCY_CONTACT_PHONE");
        parameterMap.put("ecrs/", "ecrs/EMERGENCY_CONTACT_RELATIONSHIP");
        parameterMap.put("dname/", "dname/DOCTOR_NAME");
        parameterMap.put("dphone/", "dphone/DOCTOR_PHONE");
        parameterMap.put("demail/", "demail/DOCTOR_EMAIL");
        parameterMap.put("t/", "t/TAG");
        parameterMap.put("ec/", "ec/EMERGENCY_CONTACT_INDEX");

        // Initialize command-specific parameter order for commands that have "/" in them
        commandParameterOrder.put("add", Arrays.asList(
                "n/", "p/", "e/", "a/", "ecname/", "ecphone/", "ecrs/", "dname/", "dphone/", "demail/", "t/"
        ));
        commandParameterOrder.put("addec", Arrays.asList(
                "ecname/", "ecphone/", "ecrs/"
        ));
        commandParameterOrder.put("edit", Arrays.asList(
                "n/", "p/", "e/", "a/", "ec/", "ecname/", "ecrs/", "dname/", "dphone/", "demail/", "t/"
        ));
        commandParameterOrder.put("delete", Arrays.asList(
                "ec/"
        ));
    }

    /**
     * Creates a CommandBox with the given CommandExecutor.
     * This command box provides an command interface with suggestions based on command syntax.
     * Suggestions are shown for:
     * - Available commands when typing command keywords
     * - Parameter syntax for commands with parameters
     * - Next parameter in sequence after completing each parameter
     *
     * @param commandExecutor Lambda function that executes the entered command text
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // Listen for ANY text changes to reset ALL styles
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            resetStyle();
            commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
            handleTextChanged(newValue);
        });

        // Consume control key to autocomplete
        commandTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.CONTROL) {
                handleControlPressed();
                event.consume();
            }
        });
    }
    private void resetStyle() {
        commandTextField.setStyle(DEFAULT_STYLE);
    }


    private String findShortestCommandMatch(String partial) {
        if (partial.isEmpty()) {
            return null;
        }
        String shortestMatch = null;

        for (String command : commandSyntaxMap.keySet()) {
            if (command.startsWith(partial)) {
                if (shortestMatch == null || command.length() < shortestMatch.length()) {
                    shortestMatch = command;
                }
            }
        }
        return shortestMatch;
    }

    private void suggestNextParameter(String command, String input) {
        String nextParam = getNextParameter(command, input.trim());
        if (nextParam != null) {
            String nextPrefix = nextParam.substring(0, nextParam.indexOf('/') + 1);
            commandTextField.setText(input.trim() + " " + nextPrefix);
            commandTextField.positionCaret(commandTextField.getText().length());
        }
    }

    /**
     * Retrieves the next uncompleted parameter for the given command based on the current input.
     * The method checks the sequence of parameters defined for the command and returns the
     * next parameter that has not been provided yet.
     *
     * Example:
     * If the command is "add" and the currentInput is "add n/John p/12345",
     * and the parameter sequence is ["n/", "p/", "e/"],
     * this method will return "e/" since it is the next uncompleted parameter.
     */
    private String getNextParameter(String command, String currentInput) {
        List<String> paramOrder = commandParameterOrder.get(command);
        if (paramOrder == null) {
            return null;
        }

        // Create a set of completed prefixes
        Set<String> completedPrefixes = new HashSet<>();

        // Split input into parts and process each part in order
        String[] parts = currentInput.split("\\s+");
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            // Find the longest matching prefix for this part
            String matchedPrefix = null;
            for (String prefix : paramOrder) {
                if (part.startsWith(prefix) && (matchedPrefix == null || prefix.length() > matchedPrefix.length())) {
                    matchedPrefix = prefix;
                }
            }

            // If we found a prefix and there's a value after it, mark it as completed
            if (matchedPrefix != null) {
                String value = part.substring(matchedPrefix.length());
                if (!value.isEmpty()) {
                    completedPrefixes.add(matchedPrefix);
                }
            }
        }

        // Find the first uncompleted parameter in the order
        for (String prefix : paramOrder) {
            if (!completedPrefixes.contains(prefix)) {
                return parameterMap.get(prefix);
            }
        }

        return null;
    }


    private void handleControlPressed() {
        /* Trim excess spaces but keep the input intact
        "\\s+$" is the REGEX pattern being used here:
        '\\s' matches any whitespace character (including spaces, tabs, etc.).
        '+' means one or more occurrences of the previous pattern (\\s).
        '$' matches the end of the string.
         */
        String input = commandTextField.getText().replaceAll("\\s+$", ""); // Remove trailing spaces

        // check for single word commands e.g(add, INDEX, find etc)
        if (!input.contains(" ")) {
            String match = findShortestCommandMatch(input.trim());
            if (match != null) {
                if (match.equals(input)) {
                    String syntax = commandSyntaxMap.get(match);
                    // these words need be replaced by 1-word inputs by users that cannot be autocompleted
                    if (syntax.contains("INDEX") || syntax.contains("KEYWORD")) {
                        commandTextField.setStyle(INPUT_NEEDED_STYLE);
                        return;
                    }
                    // If no INDEX needed, proceed with parameter autocomplete e.g.(proceed to n/ p/ cases)
                    suggestNextParameter(match, match); //input vs cur state same
                } else {
                    // Just complete the command (delete, add, find)
                    commandTextField.setText(match);
                    commandTextField.positionCaret(match.length());
                }
            }
            return;
        }

        // Check if input sequence is valid before proceeding with ANY autocomplete
        if (!isValidPreSlashSequence(input)) {
            return;
        }

        String[] parts = input.split("\\s+");
        String command = parts[0];
        String lastPart = parts[parts.length - 1];

        // If last part ends with a slash, turn red (user needs to input info)
        if (lastPart.endsWith("/")) {
            commandTextField.setStyle(INPUT_NEEDED_STYLE);
            return;
        }

        // Check if the last part contains a valid prefix and value
        if (lastPart.contains("/")) {
            String value = lastPart.substring(lastPart.indexOf('/') + 1).trim();

            // If there's a value after the slash
            if (!value.isEmpty()) {
                suggestNextParameter(command, input);
                return;
            }
        }

        if (!commandSyntaxMap.containsKey(command)) {
            return;
        }
        suggestNextParameter(command, input);
    }

    private boolean isValidPreSlashSequence(String input) {
        String command = input.split("\\s+")[0];
        if (!commandSyntaxMap.containsKey(command)) {
            return false;
        }

        String fullSyntax = commandSyntaxMap.get(command);

        if (fullSyntax.contains("INDEX") || fullSyntax.contains("KEYWORD")) {
            String[] inputParts = input.trim().split("\\s+");
            if (inputParts.length > 1) {
                if (fullSyntax.contains("INDEX")) {
                    fullSyntax = fullSyntax.replace("INDEX", inputParts[1]);
                } else {
                    fullSyntax = fullSyntax.replace("KEYWORD", inputParts[1]);
                }
            }
        }

        // Find the longest matching prefix in the input
        String longestPrefix = null;
        int longestPrefixLength = 0;
        for (String prefix : parameterMap.keySet()) {
            if (input.contains(prefix) && prefix.length() > longestPrefixLength) {
                longestPrefix = prefix;
                longestPrefixLength = prefix.length();
            }
        }

        // If no prefix found or input shorter than what we found so far
        if (longestPrefix == null) {
            return true; // Allow typing to continue
        }

        int inputSlashPos = input.indexOf('/');
        int syntaxSlashPos = fullSyntax.indexOf('/');

        String inputToCompare = (inputSlashPos == -1) ? input : input.substring(0, inputSlashPos);
        String syntaxToCompare = fullSyntax.substring(0, syntaxSlashPos);

        if (inputToCompare.length() > syntaxToCompare.length()) {
            return false;
        }

        for (int i = 0; i < inputToCompare.length(); i++) {
            if (inputToCompare.charAt(i) != syntaxToCompare.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    private boolean hasInvalidParameterStructure(String input) {
        String[] parts = input.split("\\s+");

        // Create a set of valid prefixes for easier lookup
        Set<String> validPrefixes = new HashSet<>(parameterMap.keySet());

        String currentPrefix = null; // Track current param prefix

        for (String part : parts) {
            if (part.trim().isEmpty()) {
                continue;
            }

            int slashIndex = part.indexOf('/');
            if (slashIndex != -1) {
                // Check for space before slash in this part
                if (part.substring(0, slashIndex).contains(" ")) {
                    return true; // Invalid if there's space before slash
                }

                // Find the longest valid prefix at the start of this part
                String foundPrefix = null;
                int longestLength = 0;

                for (String prefix : validPrefixes) {
                    if (part.startsWith(prefix) && prefix.length() > longestLength) {
                        foundPrefix = prefix;
                        longestLength = prefix.length();
                    }
                }

                // If we found a valid prefix at the start
                if (foundPrefix != null) {
                    currentPrefix = foundPrefix;
                    // Don't check for more slashes in the value part
                } else if (currentPrefix == null) {
                    // Only invalid if we're not in a parameter value
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasProperSpaceBeforePrefix(String input, String prefix) {
        int prefixPos = input.lastIndexOf(prefix);
        if (prefixPos <= 0) {
            return false;
        }

        // Get the last space before this prefix
        int lastSpacePos = input.lastIndexOf(' ', prefixPos);
        if (lastSpacePos == -1) {
            return false; // No space found before prefix at all
        }

        // Get the text between the last space and this prefix
        String textBetween = input.substring(lastSpacePos + 1, prefixPos);

        // Check if there are any valid prefixes in the text between
        // This handles cases like "namep/" or "phonee/"
        for (String validPrefix : parameterMap.keySet()) {
            if (textBetween.contains(validPrefix)) {
                return false;
            }
        }

        return true;
    }
    private boolean isValidCommandPrefix(String input) {
        // Split the input by spaces to isolate the first word (the command keyword)
        String[] parts = input.trim().split("\\s+");
        String commandPrefix = parts[0];
        if (parts.length == 1) {
            // Show suggestions if commandPrefix exactly matches any command
            // or is a valid beginning of a command in the syntax map
            for (String command : commandSyntaxMap.keySet()) {
                if (command.startsWith(commandPrefix)) {
                    return true;
                }
            }
        }

        // Check if the commandPrefix exactly matches any valid command in the syntax map
        return commandSyntaxMap.containsKey(commandPrefix);
    }

    private void handleTextChanged(String input) {

        if (!isValidCommandPrefix(input)) {
            suggestionLabel.setVisible(false);
            return;
        }
        if (input.trim().isEmpty()) {
            suggestionLabel.setVisible(false);
            return;
        }

        // Check if the last part of the input contains a slash
        String[] parts = input.split("\\s+");
        String lastPart = parts[parts.length - 1];

        // For just the command (before any parameters)
        if (!input.contains(" ")) {
            StringBuilder suggestions = new StringBuilder();
            boolean foundMatch = false;

            List<String> commands = new ArrayList<>(commandSyntaxMap.keySet());
            Collections.sort(commands);

            for (String command : commands) {
                if (command.startsWith(input.trim())) {
                    if (foundMatch) {
                        suggestions.append(" | ");
                    }
                    suggestions.append(commandSyntaxMap.get(command));
                    foundMatch = true;
                }
            }

            if (foundMatch) {
                suggestionLabel.setText(suggestions.toString());
                suggestionLabel.setVisible(true);
                return;
            }
        }

        String command = parts[0];

        // Special handling for commands without slash parameters
        if (!commandSyntaxMap.get(command).contains("/")) {
            // If just the command is typed
            if (input.trim().equals(command)) {
                suggestionLabel.setText(commandSyntaxMap.get(command));
                suggestionLabel.setVisible(true);
                return;
            }

            // For list command with optional sort order
            if (command.equals("list") && parts.length == 1) {
                suggestionLabel.setText(commandSyntaxMap.get(command));
                suggestionLabel.setVisible(true);
                return;
            }

            // Hide suggestions once parameters are being typed for non-slash commands
            suggestionLabel.setVisible(false);
            return;
        }

        // Regular handling for slash-parameter commands
        if (hasInvalidParameterStructure(input)) {
            suggestionLabel.setVisible(false);
            return;
        }

        if (!isValidPreSlashSequence(input)) {
            suggestionLabel.setVisible(false);
            return;
        }

        // Find the last parameter prefix in the input with longest match
        String longestPrefix = null;
        int lastPrefixPos = -1;
        int longestPrefixLength = 0;
        String restOfInput = "";

        for (String prefix : parameterMap.keySet()) {
            int pos = input.lastIndexOf(prefix);
            if (pos > -1 && hasProperSpaceBeforePrefix(input, prefix) && prefix.length() > longestPrefixLength) {
                lastPrefixPos = pos;
                longestPrefix = prefix;
                longestPrefixLength = prefix.length();
                restOfInput = input.substring(pos + prefix.length());
            }
        }

        // We just typed the command
        if (input.trim().equals(command)) {
            suggestionLabel.setText(commandSyntaxMap.get(command));
            suggestionLabel.setVisible(true);
            return;
        }

        // If no valid prefix with proper spacing found, hide suggestion
        if (longestPrefix == null) {
            suggestionLabel.setVisible(false);
            return;
        }

        // If we just typed a properly spaced prefix OR if last part contains a slash
        if (restOfInput.trim().isEmpty() || lastPart.contains("/")) {
            String paramValue;
            String prefix;
            String beforePrefix;

            if (lastPart.contains("/")) {
                // For parts that contain a slash
                prefix = lastPart.substring(0, lastPart.indexOf('/') + 1);
                String valueAfterSlash = lastPart.substring(lastPart.indexOf('/') + 1);

                // If typing after slash, hide suggestion unless followed by space
                if (!valueAfterSlash.isEmpty()) {
                    if (input.endsWith(" ") && !valueAfterSlash.endsWith(" ")) {
                        // Show next parameter suggestion when space is hit
                        String nextParam = getNextParameter(command, input.trim());
                        if (nextParam != null) {
                            suggestionLabel.setText(input.trim() + " " + nextParam);
                            suggestionLabel.setVisible(true);
                            return;
                        }
                    }
                    suggestionLabel.setVisible(false);
                    return;
                }

                paramValue = parameterMap.get(prefix).substring(prefix.length());
                beforePrefix = input.substring(0, input.lastIndexOf(lastPart));
            } else {
                // For just typed prefix
                prefix = longestPrefix;
                paramValue = parameterMap.get(longestPrefix).substring(longestPrefix.length());
                beforePrefix = input.substring(0, lastPrefixPos);
            }

            suggestionLabel.setText(beforePrefix + prefix + paramValue);
            suggestionLabel.setVisible(true);
            return;
        }

        // If we just pressed space after completing a value
        if (input.endsWith(" ")) {
            String nextParam = getNextParameter(command, input.trim());
            if (nextParam != null) {
                suggestionLabel.setText(input.trim() + " " + nextParam);
                suggestionLabel.setVisible(true);
                return;
            }
        }

        suggestionLabel.setVisible(false);
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


    private void setStyleToIndicateCommandFailure() {
        if (!commandTextField.getStyleClass().contains(ERROR_STYLE_CLASS)) {
            commandTextField.getStyleClass().add(ERROR_STYLE_CLASS);
        }
    }

    /**
     * Functional interface for command execution in the CommandBox.
     * Implementations should handle parsing and execution of command text entered by users.
     * Commands can throw CommandException for execution errors or ParseException for invalid syntax.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        void execute(String commandText) throws CommandException, ParseException;
    }
}
