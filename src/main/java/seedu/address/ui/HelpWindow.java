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

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-w14-2.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide for more details: " + USERGUIDE_URL;
    public static final String HELP_TEXT = String.join("\n\n",
            "Welcome to BA€ 1.6, your powerful ally in optimizing recurring sales.",
            "This guide will help you navigate the key features and commands of BA€.",

            "=== Adding a Client ===",
            "Add a new client to your database with detailed information.",
            "Command: add n\\NAME p\\PHONE e\\EMAIL a\\ADDRESS [fi\\FINANCIALINFO]"
                    + " [s\\SOCIALMEDIAHANDLE] [t\\TAG]… [t\\TAG:value]…",
            "Example: add n\\Acme Corp p\\91234567 e\\contact@acme.com a\\123 Business Ave, Suite 100, Metropolis "
                    + "t\\industry:tech t\\size:enterprise fi\\Annual contract: €50,000 s\\@acmecorp",

            "=== Listing Clients ===",
            "View all clients in your database.",
            "Command: list",

            "=== Finding Clients ===",
            "Search for clients by name using keywords.",
            "Command: find KEYWORD [MORE_KEYWORDS]",
            "Example: find Acme Corp",

            "=== Smart Filtering ===",
            "Quickly identify client groups based on tags or name.",
            "Command: filter [n\\NAME] [t\\TAG]…",
            "Example: filter t\\friend n\\John",

            "=== Editing Client Information ===",
            "Update existing client details.",
            "Command: edit INDEX [n\\NAME] [p\\PHONE] [e\\EMAIL] [a\\ADDRESS] "
                    + "[fi\\FINANCIALINFO] [s\\SOCIALMEDIAHANDLE] [t\\TAG]… [t\\TAG:value]…",
            "Example: edit 1 p\\98765432 e\\newemail@acme.com",

            "=== Deleting a Client ===",
            "Remove a client from your database.",
            "Command: delete INDEX",
            "Example: delete 1",

            "=== Advanced Filtering ===",
            "Organize your client list based on custom priority metrics.",
            "Command: advfilter t\\tag [OPERATOR] [VALUE]",
            "Example: advfilter t\\age <= 50",

            "=== Sorting by Tags ===",
            "Sort your client list based on tag values.",
            "Command: sort t\\tag asc/desc",
            "Example: sort t\\age asc",

            "=== Data Export ===",
            "Export your client data for analysis or reporting.",
            "Command: export format\\[file format]",
            "Example: export format\\csv",

            "=== Clearing All Entries ===",
            "Remove all clients from your database.",
            "Command: clear",

            "=== Exiting the Program ===",
            "Close the BA€ application.",
            "Command: exit"
    );


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
