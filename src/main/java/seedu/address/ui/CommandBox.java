package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        commandSyntaxMap.put("addec", "addec ecname/EMERGENCY_CONTACT_NAME ecphone/EMERGENCY_CONTACT_PHONE "
                + "ecrs/EMERGENCY_CONTACT_RELATIONSHIP");
        commandSyntaxMap.put("archive", "archive [DESCRIPTION]");
        commandSyntaxMap.put("clear", "clear");
        commandSyntaxMap.put("delete", "delete INDEX [ec/EMERGENCY_CONTACT_INDEX]");
        commandSyntaxMap.put("edit", "edit INDEX n/NAME p/PHONE e/EMAIL a/ADDRESS ec/ECINDEX "
                + "ecname/EMERGENCY_CONTACT_NAME ecrs/EMERGENCY_CONTACT_RELATIONSHIP dname/DOCTOR_NAME "
                + "dphone/DOCTOR_PHONE demail/DOCTOR_EMAIL t/TAG");
        commandSyntaxMap.put("find", "find KEYWORD [MORE_KEYWORDS]");
        commandSyntaxMap.put("finddoc", "finddoc KEYWORD [MORE_KEYWORDS]");
        commandSyntaxMap.put("help", "help");
        commandSyntaxMap.put("list", "list [SORT_ORDER]");
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
        parameterMap.put("ec/", "ec/ECINDEX");
        parameterMap.put("ec/", "ec/EMERGENCY_CONTACT_INDEX");

        // Initialize command-specific parameter order
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

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;

        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            setStyleToDefault();
            handleTextChanged(newValue);
        });
    }

    private boolean isValidPreSlashSequence(String input) {
        String command = input.split("\\s+")[0];
        if (!commandSyntaxMap.containsKey(command)) {
            return false;
        }

        String fullSyntax = commandSyntaxMap.get(command);

        // If syntax contains INDEX, replace it with the actual input value if present
        if (fullSyntax.contains("INDEX") || fullSyntax.contains("KEYWORD")) {
            String[] inputParts = input.trim().split("\\s+");
            if (inputParts.length > 1) {
                // Replace INDEX with the actual value from input
                fullSyntax = fullSyntax.replace("INDEX", inputParts[1]);
            } else {
            fullSyntax = fullSyntax.replace("KEYWORD", inputParts[1]);
            }
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
        String command = parts[0];

        // Create a set of valid prefixes for easier lookup
        Set<String> validPrefixes = new HashSet<>(parameterMap.keySet());

        // Check each part that contains a slash
        for (String part : parts) {
            int slashIndex = part.indexOf('/');
            if (slashIndex != -1) {
                // Extract the potential prefix (everything up to and including the slash)
                String potentialPrefix = part.substring(0, slashIndex + 1);

                // Only validate if it's one of our known command prefixes
                if (validPrefixes.contains(potentialPrefix)) {
                    // If this is a valid prefix, it should be at the start of the part
                    if (!part.startsWith(potentialPrefix)) {
                        return true;  // Invalid structure
                    }
                    // Check if there are any more slashes after this valid prefix
                    if (part.indexOf('/', slashIndex + 1) != -1) {
                        return true;  // Invalid structure
                    }
                }
            }
        }
        return false;
    }

    private boolean hasProperSpaceBeforePrefix(String input, String prefix) {
        int prefixPos = input.lastIndexOf(prefix);
        if (prefixPos <= 0) return false;

        // Get the last space before this prefix
        int lastSpacePos = input.lastIndexOf(' ', prefixPos);
        if (lastSpacePos == -1) {
            return false;  // No space found before prefix at all
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

    private boolean startsWithValidPrefix(String part) {
        for (String prefix : parameterMap.keySet()) {
            if (part.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private void handleTextChanged(String input) {

        System.out.println("Input received: '" + input + "'");
        System.out.println("Ends with space: " + input.endsWith(" "));

        if (input.trim().isEmpty()) {
            suggestionLabel.setVisible(false);
            return;
        }

        // New section for command suggestions
        if (!input.contains(" ")) {  // If we haven't typed a space yet
            StringBuilder suggestions = new StringBuilder();
            boolean foundMatch = false;

            // Sort the commands for consistent display
            List<String> commands = new ArrayList<>(commandSyntaxMap.keySet());
            Collections.sort(commands);

            for (String command : commands) {
                if (command.startsWith(input.trim().toLowerCase())) {
                    if (foundMatch) {
                        suggestions.append(" | ");  // Separator between commands
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

        String[] parts = input.split("\\s+");
        String command = parts[0];

        // If command isn't recognized, hide suggestion
        if (!commandSyntaxMap.containsKey(command)) {
            suggestionLabel.setVisible(false);
            return;
        }

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
            System.out.println("Invalid parameter structure detected");
            suggestionLabel.setVisible(false);
            return;
        }

        if (!isValidPreSlashSequence(input)) {
            System.out.println("Invalid pre-slash sequence");
            suggestionLabel.setVisible(false);
            return;
        }

        System.out.println("Command: " + command);

        // Find the last parameter prefix in the input
        String currentPrefix = null;
        int lastPrefixPos = -1;
        String restOfInput = "";

        for (String prefix : parameterMap.keySet()) {
            int pos = input.lastIndexOf(prefix);
            System.out.println("Checking prefix: " + prefix + " at position: " + pos);
            if (pos > lastPrefixPos) {
                if (hasProperSpaceBeforePrefix(input, prefix)) {
                    lastPrefixPos = pos;
                    currentPrefix = prefix;
                    restOfInput = input.substring(pos + prefix.length());
                    System.out.println("Found valid prefix: " + prefix + " with rest of input: '" + restOfInput + "'");
                }
            }
        }

        // We just typed the command
        if (input.trim().equals(command)) {
            System.out.println("Just command typed");
            suggestionLabel.setText(commandSyntaxMap.get(command));
            suggestionLabel.setVisible(true);
            return;
        }

        // If no valid prefix with proper spacing found, hide suggestion
        if (currentPrefix == null) {
            System.out.println("No valid prefix found");
            suggestionLabel.setVisible(false);
            return;
        }

        // If we just typed a properly spaced prefix
        if (restOfInput.trim().isEmpty()) {
            System.out.println("Empty rest of input - showing parameter value");
            String paramValue = parameterMap.get(currentPrefix).substring(currentPrefix.length());
            String beforePrefix = input.substring(0, lastPrefixPos);
            suggestionLabel.setText(beforePrefix + currentPrefix + paramValue);
            suggestionLabel.setVisible(true);
            return;
        }

        // If we're typing a value (not ended with space)
        if (!input.endsWith(" ")) {
            System.out.println("Currently typing value - hiding suggestion");
            suggestionLabel.setVisible(false);
            return;
        }

        // If we just pressed space after completing a value
        if (input.endsWith(" ")) {
            System.out.println("Space detected - getting next parameter");
            String nextParam = getNextParameter(command, input.trim());
            System.out.println("Next parameter found: " + nextParam);
            if (nextParam != null) {
                suggestionLabel.setText(input.trim() + " " + nextParam);
                suggestionLabel.setVisible(true);
                return;
            }
        }

        System.out.println("Reached end - hiding suggestion");
        suggestionLabel.setVisible(false);
    }

    private String getNextParameter(String command, String currentInput) {
        System.out.println("Getting next parameter for command: " + command);
        System.out.println("Current input: " + currentInput);

        List<String> paramOrder = commandParameterOrder.get(command);
        if (paramOrder == null) {
            System.out.println("No parameter order found for command");
            return null;
        }

        String currentPrefix = null;
        int lastPrefixPos = -1;

        for (String prefix : paramOrder) {
            int pos = currentInput.lastIndexOf(prefix);
            System.out.println("Checking prefix: " + prefix + " at position: " + pos);
            if (pos > lastPrefixPos) {
                lastPrefixPos = pos;
                currentPrefix = prefix;
                System.out.println("Found current prefix: " + currentPrefix);
            }
        }

        if (currentPrefix == null && !paramOrder.isEmpty()) {
            String firstParam = parameterMap.get(paramOrder.get(0));
            System.out.println("No current prefix found - returning first parameter: " + firstParam);
            return firstParam;
        }

        for (int i = 0; i < paramOrder.size() - 1; i++) {
            if (paramOrder.get(i).equals(currentPrefix)) {
                String nextParam = parameterMap.get(paramOrder.get(i + 1));
                System.out.println("Found next parameter: " + nextParam);
                return nextParam;
            }
        }

        System.out.println("No next parameter found");
        return null;
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
        if (!commandTextField.getStyleClass().contains(ERROR_STYLE_CLASS)) {
            commandTextField.getStyleClass().add(ERROR_STYLE_CLASS);
        }
    }


    @FunctionalInterface
    public interface CommandExecutor {
        void execute(String commandText) throws CommandException, ParseException;
    }
}