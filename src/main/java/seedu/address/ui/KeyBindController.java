package seedu.address.ui;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.person.Person;

/**
 * Manages key bindings and their actions within the application's user interface components.
 */
public class KeyBindController {
    private CommandBox commandBox;
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;


    /**
     * Initializes the KeyBindController with UI components to be managed.
     */
    public KeyBindController(CommandBox commandBox, PersonListPanel personListPanel,
                             ResultDisplay resultDisplay) {
        this.commandBox = commandBox;
        this.personListPanel = personListPanel;
        this.resultDisplay = resultDisplay;
    }
    /**
     * Handles a key event by executing any matching key bindings, or consumes the event if TAB is pressed.
     */
    static void handleKeyEvent(KeyEvent event, KeyBind... bindings) {
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
    /**
     * Sets up key bindings for focus traversal and command handling across UI components.
     */
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
