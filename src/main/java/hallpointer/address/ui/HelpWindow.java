package hallpointer.address.ui;

import java.util.logging.Logger;

import hallpointer.address.commons.core.LogsCenter;
import hallpointer.address.logic.commands.AddMemberCommand;
import hallpointer.address.logic.commands.AddSessionCommand;
import hallpointer.address.logic.commands.ClearCommand;
import hallpointer.address.logic.commands.DeleteMemberCommand;
import hallpointer.address.logic.commands.DeleteSessionCommand;
import hallpointer.address.logic.commands.ExitCommand;
import hallpointer.address.logic.commands.FindMemberCommand;
import hallpointer.address.logic.commands.FindSessionCommand;
import hallpointer.address.logic.commands.HelpCommand;
import hallpointer.address.logic.commands.ListCommand;
import hallpointer.address.logic.commands.UpdateMemberCommand;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-w14-3.github.io/tp/UserGuide.html";

    public static final String COMMAND_WORDS = "These are the commands currently implemented in alphabetical order:\n"
            + AddMemberCommand.COMMAND_WORD + ", " + AddSessionCommand.COMMAND_WORD + "\n"
            + ClearCommand.COMMAND_WORD + "\n"
            + DeleteMemberCommand.COMMAND_WORD + ", " + DeleteSessionCommand.COMMAND_WORD + "\n"
            + ExitCommand.COMMAND_WORD + "\n"
            + FindMemberCommand.COMMAND_WORD + ", " + FindSessionCommand.COMMAND_WORD + "\n"
            + HelpCommand.COMMAND_WORD + "\n"
            + ListCommand.COMMAND_WORD + "\n"
            + UpdateMemberCommand.COMMAND_WORD + "\n";
    public static final String ADDITIONAL_HELP = "If you need more details, "
            + "please refer to the user guide: " + USERGUIDE_URL;
    public static final String HELP_MESSAGE = COMMAND_WORDS + ADDITIONAL_HELP;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button closeButton;

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
     * Hides the help window without mouse input being required.
     */
    @FXML
    private void closeWindow() {
        hide();
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
