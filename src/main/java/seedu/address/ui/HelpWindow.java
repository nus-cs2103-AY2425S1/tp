package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddWeddingCommand;
import seedu.address.logic.commands.ClearAddressBookCommand;
import seedu.address.logic.commands.ClearWeddingBookCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteWeddingCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListWeddingCommand;
import seedu.address.logic.commands.TagAddCommand;
import seedu.address.logic.commands.TagDeleteCommand;
import seedu.address.logic.commands.ViewWeddingCommand;


/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {
    public static final String COMMAND_SUMMARY =
            AddCommand.COMMAND_FUNCTION + '\n'
            + AddWeddingCommand.COMMAND_FUNCTION + '\n'
            + DeleteCommand.COMMAND_FUNCTION + '\n'
            + DeleteWeddingCommand.COMMAND_FUNCTION + '\n'
            + EditCommand.COMMAND_FUNCTION + '\n'
            + FilterCommand.COMMAND_FUNCTION + '\n'
            + HelpCommand.COMMAND_FUNCTION + '\n'
            + ListCommand.COMMAND_FUNCTION + '\n'
            + ListWeddingCommand.COMMAND_FUNCTION + '\n'
            + TagAddCommand.COMMAND_FUNCTION + '\n'
            + TagDeleteCommand.COMMAND_FUNCTION + '\n'
            + ViewWeddingCommand.COMMAND_FUNCTION + '\n'
            + ClearAddressBookCommand.COMMAND_FUNCTION + '\n'
            + ClearWeddingBookCommand.COMMAND_FUNCTION + '\n';
    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-w13-4.github.io/tp/index.html";
    public static final String HELP_MESSAGE = COMMAND_SUMMARY
            + "\nFor further details please refer to the user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
