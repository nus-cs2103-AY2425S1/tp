package seedu.address.ui;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import seedu.address.model.person.Person;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;


public class KeyBindController {
    // In your controller class
    private CommandBox commandBox;
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;



    public KeyBindController(CommandBox commandBox, PersonListPanel personListPanel,
                             ResultDisplay resultDisplay) {
        this.commandBox = commandBox;
        this.personListPanel = personListPanel;
        this.resultDisplay = resultDisplay;
    }

    private static void handleKeyEvent(KeyEvent event, KeyBind... bindings) {
        for (KeyBind binding : bindings) {
            if (binding.matches(event)) {
                event.consume();
                binding.execute();
                return;
            }
        }

        // Always consume TAB
        if (event.getCode() == KeyCode.TAB) {
            event.consume();
        }
    }

    public void initialize() {
        // Disable default tab traversal
        commandBox.getRoot().setFocusTraversable(false);
        personListPanel.getRoot().setFocusTraversable(false);
        resultDisplay.getRoot().setFocusTraversable(false);

        TextField commandTextField = commandBox.getCommandTextField();
        TextArea resultTextArea = resultDisplay.getResultTextArea();
        ListView<Person> personListView = personListPanel.getPersonListView();

        KeyBind focusCommandBox = new KeyBind(KeyCode.SEMICOLON, commandTextField::requestFocus);

        KeyBind focusResult = new KeyBind(KeyCode.ESCAPE, resultTextArea::requestFocus);

        KeyBind undoCommand = new KeyBind(KeyCode.Z, () -> commandBox.handleCommand(UndoCommand.COMMAND_WORD),
                event -> event.isControlDown() && !event.isShiftDown());

        KeyBind redoCommand = new KeyBind(KeyCode.Z, () -> commandBox.handleCommand(RedoCommand.COMMAND_WORD),
                event -> event.isControlDown() && event.isShiftDown());

        resultTextArea.setOnKeyPressed(event ->
                handleKeyEvent(event, focusCommandBox, undoCommand, redoCommand));

        commandTextField.setOnKeyPressed(event ->
                handleKeyEvent(event, focusResult, undoCommand, redoCommand));

        personListView.setOnKeyPressed(event ->
                handleKeyEvent(event, focusCommandBox, undoCommand, redoCommand));



    }

}
