package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-f11-4.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;
    public static final String COMMANDS_INFO = """
            Common Commands:

            1. Adding a buyer:
               buyer n/NAME p/PHONE e/EMAIL
               Example: buyer n/John Doe p/98765432 e/johnd@example.com

            2. Adding a seller:
               seller n/NAME p/PHONE e/EMAIL
               Example: seller n/John Doe p/98765432 e/johnd@example.com

            3. Listing all clients:
               showclients

            4. Deleting a client:
               deleteclient INDEX
               Example: deleteclient 1

            5. Adding a listing:
               listing n/NAME pr/PRICE ar/AREA add/ADDRESS reg/REGION sel/SELLER buy/BUYER
               Example: listing n/NAME pr/400000 ar/2400 add/134 Pasir Ris reg/east sel/Alex buy/Bernice

            6. Listing all listings:
               showlistings

            7. Deleting a listing:
               deletelisting INDEX
               Example: deletelisting 1

            8. Help:
               help

            9. Exit:
               exit
            """;
    private static final String FXML = "HelpWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);

    @FXML
    private Button copyButton;

    @FXML
    private Button openBrowserButton;

    @FXML
    private Label helpMessage;

    @FXML
    private TextArea commandsArea;
    private final Desktop desktop;


    // Default constructor - uses the OS Desktop by default
    public HelpWindow(Stage root) {
        this(root, Desktop.getDesktop());
    }

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow
     * @param desktop Desktop instance used to open URLs, allowing for dependency injection in tests
     */
    public HelpWindow(Stage root, Desktop desktop) {
        super(FXML, root);
        this.desktop = desktop;
        helpMessage.setText(HELP_MESSAGE);
        commandsArea.setText(COMMANDS_INFO);
        commandsArea.setEditable(false);
        commandsArea.setWrapText(true);
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
     * Opens the user guide URL in the default browser.
     */
    @FXML
    void openInBrowser() {
        try {
            desktop.browse(new URI(USERGUIDE_URL));
        } catch (IOException | URISyntaxException e) {
            logger.warning("Error opening URL in browser: " + e.getMessage());
        }
    }

    /**
     * Opens the user guide in the system's default web browser.
     */
    @FXML
    void openUserGuide() {
        try {
            desktop.browse(new URI(USERGUIDE_URL));
        } catch (Exception e) {
            logger.warning("Failed to open the user guide in the browser: " + e.getMessage());
        }
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
