package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-f15-1.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide:";
    private static final String HELP_COMMAND = getAllCommands();
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private Label helpCommands;

    @FXML
    private Hyperlink userGuideLink;

    /**
     * Creates a new HelpWindow. The HelpWindow will be set at
     * foreground of the MainWindow until closed.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root, Stage mainStage) {
        super(FXML, root);
        root.setAlwaysOnTop(true);

        helpMessage.setText(HELP_MESSAGE);
        helpCommands.setText(HELP_COMMAND);

        userGuideLink.setOnAction(event -> openUserGuide());

        mainStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                root.setAlwaysOnTop(true);
            } else {
                root.setAlwaysOnTop(false);
            }
        });
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow(Stage mainStage) {
        this(new Stage(), mainStage);
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
     * Returns the list of all commands.
     */
    public static String getAllCommands() {
        return "Here are the list of commands available:\n"
                + "\n"
                + "General commands\n"
                + "1. help\n"
                + "2. clear\n"
                + "3. exit\n"
                + "\n"
                + "Patient-related commands:\n"
                + "1. add\n"
                + "2. delete\n"
                + "3. edit\n"
                + "4. emergency\n"
                + "5. delemergency\n"
                + "6. priority\n"
                + "7. deletelevel\n"
                + "8. list\n"
                + "9. find\n"
                + "\n"
                + "Task-related commands\n"
                + "1. addtask\n"
                + "2. deletetask\n"
                + "3. findtask\n"
                + "4. listtask\n"
                + "5. marktask\n"
                + "6. unmarktask\n"
                + "7. listincomplete\n";
    }

    /**
     * Opens the user guide in the default browser.
     */
    private void openUserGuide() {
        try {
            Desktop.getDesktop().browse(new URI(USERGUIDE_URL));
        } catch (IOException | URISyntaxException e) {
            logger.warning("Failed to open user guide: " + e.getMessage());
        }
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
