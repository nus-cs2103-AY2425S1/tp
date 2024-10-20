package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;
    public static final String HELP_TEXT = "# BA€ 1.0 Help\n\n"
            + "Welcome to BA€ 1.0, your powerful ally in optimizing recurring sales. "
            + "This guide will help you navigate the key features and commands of BA€.\n\n"
            + "Key Features and Commands\n\n"
            + "1. Adding a Client\n"
            + "Add a new client to your database with detailed information.\n\n"
            + "Format: add n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]...\n\n"
            + "Example: add n/Acme Corp p/91234567 e/contact@acme.com a/123 Business Ave, Suite 100,"
            + "Metropolis t/industry:tech t/size:enterprise fi/Annual contract: €50,000 s/@acmecorp\n\n"
            + "2. Listing Clients\n"
            + "View all clients in your database.\n\n"
            + "Format: list\n\n"
            + "3. Finding Clients\n"
            + "Search for clients using keywords.\n\n"
            + "Format: find KEYWORD [MORE_KEYWORDS]\n\n"
            + "Example: find Acme Corp\n\n"
            + "4. Smart Filtering\n"
            + "Quickly identify client groups based on specific criteria.\n\n"
            + "Format: filter [CRITERIA]\n\n"
            + "Example: filter t/contract_value>100000 t/last_contact<30days\n\n"
            + "5. Editing Client Information\n"
            + "Update existing client details.\n\n"
            + "Format: edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...\n\n"
            + "Example: edit 1 p/98765432 e/newemail@acme.com\n\n"
            + "6. Deleting a Client\n"
            + "Remove a client from your database.\n\n"
            + "Format: delete INDEX\n\n"
            + "Example: delete 1\n\n"
            + "7. Advanced filtering\n"
            + "Organize your client list based on custom priority metrics.\n\n"
            + "Format: advfilter [CRITERIA]\n\n"
            + "Example: advfilter t/renewal_date <= 90days t/contract_value desc\n\n"
            + "8. Data Export\n"
            + "Export your client data for analysis or reporting.\n\n"
            + "Format: export format/[file format]\n\n"
            + "Example: export format/csv\n\n"
            + "9. Clearing All Entries\n"
            + "Remove all clients from your database.\n\n"
            + "Format: clear\n\n"
            + "10. Exiting the Program\n"
            + "Close the BA€ application.\n\n"
            + "Format: exit\n\n"
            + "For more detailed information, please refer to the full user guide.";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextFlow helpTextFlow;

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
        setHelpText();
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    private void setHelpText() {
        Text text = new Text(HELP_TEXT);
        text.getStyleClass().add("help-text");
        helpTextFlow.getChildren().add(text);
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
