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

    public static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

    /**
     * Add Additional Help Messages
     */
    private static final String ADD_PERSON_HELP = "Add a person: Use the command 'add' followed by the details.";
    private static final String LIST_PERSONS_HELP = "List all persons: Use the command 'list' to view all contacts.";
    private static final String EDIT_PERSON_HELP = "Edit a person: Use the command 'edit' followed by the person's ID.";
    private static final String FIND_PERSON_HELP = "Find persons by keywords: "
            + "Use the command 'find' followed by the name.";
    private static final String DELETE_PERSON_HELP = "Delete a person: Use the command 'delete' followed by the index.";
    private static final String CLEAR_ENTRIES_HELP = "Clear all entries: "
            + "Use the command 'clear' to remove all contacts.";
    private static final String EXIT_HELP = "Exit the program: Use the command 'exit' to close the application.";

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
        initializeHelpMessage(); // Initialize the help messages
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Initializes the help message to display in the help window.
     */
    private void initializeHelpMessage() {
        StringBuilder helpContent = new StringBuilder();
        helpContent.append(HELP_MESSAGE).append("\n\n");
        helpContent.append(ADD_PERSON_HELP).append("\n");
        helpContent.append(LIST_PERSONS_HELP).append("\n");
        helpContent.append(EDIT_PERSON_HELP).append("\n");
        helpContent.append(FIND_PERSON_HELP).append("\n");
        helpContent.append(DELETE_PERSON_HELP).append("\n");
        helpContent.append(CLEAR_ENTRIES_HELP).append("\n");
        helpContent.append(EXIT_HELP).append("\n");

        helpMessage.setText(helpContent.toString());
    }

    /**
     * Shows the help window.
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
