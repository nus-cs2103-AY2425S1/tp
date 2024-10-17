package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * A handle for interacting with the command input box in the GUI. This class provides methods to manipulate and
 * retrieve information from a {@code TextField} used for command input.
 */
public class CommandBoxHandle extends NodeHandle<TextField> {

    /** The CSS selector ID for the command input field. */
    public static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";

    /**
     * Constructs a {@code CommandBoxHandle} for the given command box node.
     *
     * @param commandBoxNode The {@code TextField} representing the command input box.
     */
    public CommandBoxHandle(TextField commandBoxNode) {
        super(commandBoxNode);
    }

    /**
     * Retrieves the current text input from the command box.
     *
     * @return The text currently present in the command input field.
     */
    public String getInput() {
        return getRootNode().getText();
    }

    /**
     * Simulates running a command by setting the command input and pressing the ENTER key.
     *
     * @param command The command to be executed in the command input field.
     */
    public void run(String command) {
        click();
        guiRobot.interact(() -> getRootNode().setText(command));
        guiRobot.pauseForHuman();

        guiRobot.type(KeyCode.ENTER);
    }

    /**
     * Retrieves the style class list of the command input field.
     *
     * @return An {@code ObservableList} of the style classes applied to the command input field.
     */
    public ObservableList<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }
}
