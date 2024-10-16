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
 * Controller for a report bug page
 */
public class ReportBugWindow extends UiPart<Stage> {
    public static final String REPORTING_BUG_URL = "https://forms.gle/cGnn2jZ2fdfhWc3q7";
    public static final String REPORTING_BUG_MESSAGE = "Let us know the bug through this Google Form: "
            + REPORTING_BUG_URL;

    private static final Logger logger = LogsCenter.getLogger(ReportBugWindow.class);
    private static final String FXML = "ReportBugWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label reportBugMessage;

    /**
     * Creates a new ReportBugWindow.
     *
     * @param root Stage to use as the root of the ReportBugWindow.
     */
    public ReportBugWindow(Stage root) {
        super(FXML, root);
        reportBugMessage.setText(REPORTING_BUG_MESSAGE);
    }

    /**
     * Creates a new ReportBugWindow.
     */
    public ReportBugWindow() {
        this(new Stage());
    }

    /**
     * Shows the report bug window.
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
        logger.fine("Showing report bug page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the report bug window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the report bug window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the report bug window.
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
        url.putString(REPORTING_BUG_URL);
        clipboard.setContent(url);
    }
}
