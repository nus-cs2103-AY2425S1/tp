package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103-f10-3.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "For full details, refer to the user guide: " + USERGUIDE_URL;
    public static final String URL_FAIL_MESSAGE = "Failed to open the URL. The URL has been copied to the clipboard.";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    private CommandTablePanel commandTablePanel;
    @FXML
    private Button copyButton;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private Label helpMessage;

    @FXML
    private StackPane commandTablePlaceholder;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        errorMessageLabel.setText("");
        errorMessageLabel.setManaged(false);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     *
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
     * Handles the URL button pressed event.
     */
    @FXML
    private void handleUrl() {
        // try to open browser
        boolean redirectSuccess = openUrl();

        // fall back to copying URL to clipboard
        if (!redirectSuccess) {
            copyUrl();
            showError();
        }
    }

    /**
     * Makes error message appear.
     */
    private void showError() {
        errorMessageLabel.setText(URL_FAIL_MESSAGE);
        errorMessageLabel.setManaged(true);
        this.getRoot().sizeToScene();
    }


    /**
     * Copies the URL to the user guide to the clipboard.
     */
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

    /**
     * Open the URL to the user guide to the clipboard.
     *
     * @return success status of URL open.
     */
    private boolean openUrl() {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(USERGUIDE_URL));
                return true;
            } else {
                System.out.println("Desktop browsing is not supported on this system.");
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        commandTablePanel = new CommandTablePanel();
        commandTablePlaceholder.getChildren().add(commandTablePanel.getRoot());
    }
}
