package seedu.address.ui;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
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

    private TextField textField;


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

        // Always consume TAB, in case earlier disabling of tab traversal does not work
        if (event.getCode() == KeyCode.TAB) {
            event.consume();
        }
    }

    /**
     * Sets up key bindings for focus traversal and command handling across UI components.
     */
    public void initialize() {
        TextField commandTextField = commandBox.getCommandTextField();
        this.textField = commandTextField;
        TextArea resultTextArea = resultDisplay.getResultTextArea();
        ListView<Person> personListView = personListPanel.getPersonListView();


        // Disable default tab traversal
        commandTextField.setFocusTraversable(false);
        resultTextArea.setFocusTraversable(false);
        personListView.setFocusTraversable(false);

        KeyBind focusCommandBox = new KeyBind(KeyCode.SEMICOLON, commandTextField::requestFocus);

        KeyBind focusPersonListView = new KeyBind(KeyCode.ESCAPE, personListView::requestFocus);

        KeyBind undoCommand = new KeyBind(KeyCode.Z, () -> commandBox.handleCommand(UndoCommand.COMMAND_WORD, false),
                event -> event.isControlDown() && !event.isShiftDown());

        KeyBind redoCommand = new KeyBind(KeyCode.Z, () -> commandBox.handleCommand(RedoCommand.COMMAND_WORD, false),
                event -> event.isControlDown() && event.isShiftDown());

        resultTextArea.addEventFilter(KeyEvent.KEY_PRESSED, event ->
                handleKeyEvent(event, focusCommandBox, undoCommand, redoCommand));

        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, event ->
                    handleKeyEvent(event, focusPersonListView, undoCommand, redoCommand));

        personListView.addEventFilter(KeyEvent.KEY_PRESSED, event ->
                handleKeyEvent(event, focusCommandBox, undoCommand, redoCommand));



    }

}
