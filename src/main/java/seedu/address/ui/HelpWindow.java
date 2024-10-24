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
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

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
        helpTextFlow.getChildren().addAll(
                createHeader("BA€ 1.4 Help                                                    "),
                createParagraph(
                        "Welcome to BA€ 1.4, your powerful ally in optimizing recurring sales.",
                        "This guide will help you navigate the key features and commands of BA€."
                ),
                createHeader("Key Features and Commands"),
                createFeature(
                        "Adding a Client: add",
                        "Add a new client to your database with detailed information.",
                        "add n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]...",
                        "add n/Acme Corp p/91234567 e/contact@acme.com a/123 Business Ave, Suite 100, "
                                + "\n Metropolis t/industry:tech t/size:enterprise "
                                + "fi/Annual contract: €50,000 s/@acmecorp"
                ),
                createFeature(
                        "Listing Clients: list",
                        "View all clients in your database.",
                        "list                                                                                       ",
                        null
                ),
                createFeature(
                        "Finding Clients: find",
                        "Search for clients using keywords.",
                        "find KEYWORD [MORE_KEYWORDS]",
                        "find Acme Corp"
                ),
                createFeature(
                        "Smart Filtering: filter",
                        "Quickly identify client groups based on specific criteria.",
                        "filter [CRITERIA]",
                        "filter t/contract_value>100000 t/last_contact<30days"
                ),
                createFeature(
                        "Editing Client Information: edit",
                        "Update existing client details.",
                        "edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...",
                        "edit 1 p/98765432 e/newemail@acme.com"
                ),
                createFeature(
                        "Deleting a Client: delete",
                        "Remove a client from your database.",
                        "delete INDEX",
                        "delete 1"
                ),
                createFeature(
                        "Advanced Filtering: advfilter",
                        "Organize your client list based on custom priority metrics.",
                        "advfilter [CRITERIA]",
                        "advfilter t/renewal_date <= 90days t/contract_value desc"
                ),
                createFeature(
                        "Data Export: export",
                        "Export your client data for analysis or reporting.",
                        "export format/[file format]",
                        "export format/csv                                               "
                ),
                createFeature(
                        "Clearing All Entries: clear",
                        "Remove all clients from your database.",
                        "clear                                                 ",
                        null
                ),
                createFeature(
                        "Exiting the Program: exit",
                        "Close the BA€ application.",
                        "exit",
                        null
                ),
                createParagraph(
                        "For more detailed information, please refer to the full user guide."
                )
        );
    }

    private Text createText(String content, String... styleClasses) {
        Text text = new Text(content);
        text.getStyleClass().addAll("white-text");
        text.getStyleClass().addAll(styleClasses);
        return text;
    }

    private TextFlow createHeader(String content) {
        Text header = createText(content + "\n", "header");
        TextFlow feature = new TextFlow(header);
        return feature;
    }

    private TextFlow createParagraph(String line1, String line2) {
        TextFlow paragraph = new TextFlow(
                createText(line1 + "\n"),
                createText(line2)
        );
        paragraph.getChildren().add(createText("\n"));
        return paragraph;
    }

    private TextFlow createParagraph(String line1) {
        TextFlow paragraph = new TextFlow(
                createText(line1 + "\n")
        );
        return paragraph;
    }

    private TextFlow createFeature(String title, String description, String format, String example) {
        TextFlow feature = new TextFlow(
                createText(title + "\n", "subheader"),
                createText(description + "\n"),
                createText("Format: ", "bold"),
                createText(format + "\n", "code")
        );

        if (example != null) {
            feature.getChildren().addAll(
                    createText("Example: ", "bold"),
                    createText(example + "\n", "code")
            );
        }

        feature.getChildren().add(createText("\n"));
        return feature;
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
