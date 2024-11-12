package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-w13-1.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    private static final String ASSERT_HELP_MESSAGE_MUST_BE_INITIALIZED = "Help message label must be initialized";

    private static final String LOG_URL_COPIED_TO_CLIPBOARD = "URL copied to clipboard: ";
    private static final String LOG_FAILED_TO_ACCESS_CLIPBOARD = "Failed to access system clipboard. URL not copied.";
    private static final String LOG_SHOW_HELP_PAGE = "Showing help page about the application.";

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
        assert helpMessage != null : ASSERT_HELP_MESSAGE_MUST_BE_INITIALIZED;
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
        logger.fine(LOG_SHOW_HELP_PAGE);
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

    @FXML
    private void copyUrl() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard != null) {
            ClipboardContent content = new ClipboardContent();
            content.putString(USERGUIDE_URL);
            clipboard.setContent(content);
            logger.info(LOG_URL_COPIED_TO_CLIPBOARD + USERGUIDE_URL);
        } else {
            logger.warning(LOG_FAILED_TO_ACCESS_CLIPBOARD);
        }
    }
}
