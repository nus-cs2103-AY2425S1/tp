package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.HashMap;
import java.util.Map;

/**
 * A CommandBox component that is part of the UI, allowing the user to input commands.
 * This version provides simple word-by-word autocomplete suggestions based on the command syntax map.
 */
public class CommandBox extends UiPart<Region> {
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final Map<String, String> commandSyntaxMap = new HashMap<>();

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
    }

    /**
     * Creates a CommandBox component that allows users to input and execute commands.
     * Provides simple word-by-word autocomplete suggestions based on the command syntax map.
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
    }

    private void handleTextChanged(String input) {
        if (input.trim().isEmpty()) {
            suggestionLabel.setVisible(false);
            return;
        }

        String[] words = input.split("\\s+");
        String commandPrefix = words[0];

        // Find the best matching command
        String bestMatch = findBestMatchingCommand(commandPrefix);

        if (bestMatch != null) {
            String fullSyntax = commandSyntaxMap.get(bestMatch);
            String[] syntaxParts = fullSyntax.split("\\s+");
            StringBuilder suggestion = new StringBuilder();

            // Build the suggestion based on input words
            int wordIndex = 0;
            boolean validInput = true;
            for (String part : syntaxParts) {
                if (wordIndex < words.length) {
                    if (part.startsWith(words[wordIndex])) {
                        suggestion.append(part).append(" ");
                        wordIndex++;
                    } else if (!words[wordIndex].equals(part)) {
                        validInput = false;
                        break;
                    }
                } else {
                    suggestion.append(part).append(" ");
                }
            }

            if (validInput && wordIndex == words.length && input.replaceAll("\\s+", "").equals(bestMatch.substring(0, input.replaceAll("\\s+", "").length())) && bestMatch.startsWith(input.trim())) {
                suggestionLabel.setText(suggestion.toString().trim());
                suggestionLabel.setVisible(true);
            } else {
                suggestionLabel.setVisible(false);
            }
        } else {
            suggestionLabel.setVisible(false);
        }
    }

    private String findBestMatchingCommand(String prefix) {
        return commandSyntaxMap.keySet().stream()
                .filter(cmd -> cmd.startsWith(prefix))
                .min((cmd1, cmd2) -> Integer.compare(cmd1.length(), cmd2.length()))
                .orElse(null);
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

    /**
     * Functional interface for executing commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        void execute(String commandText) throws CommandException, ParseException;
    }
}
