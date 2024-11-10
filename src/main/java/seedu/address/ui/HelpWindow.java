package seedu.address.ui;

import java.util.Arrays;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-w14-2.github.io/tp/UserGuide.html";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private ListView<String> helpList;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        initializeHelpContent();
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    private void initializeHelpContent() {
        helpList.getItems().addAll(Arrays.asList(
                "Welcome to BA€ 1.6, your powerful ally in optimizing recurring sales.\n",

                "Adding a Client: add\n"
                        + "add n\\NAME p\\PHONE e\\EMAIL a\\ADDRESS [fi\\FINANCIALINFO] "
                        + "[s\\SOCIALMEDIAHANDLE] [t\\TAG]… [t\\TAG:value]…\n"
                        + "Example: add n\\Acme Corp p\\91234567 e\\contact@acme.com a\\123 Business Ave\n",

                "Listing Clients: list\n"
                        + "Command: list\n",

                "Finding Clients: find\n"
                        + "Search for clients by name using keywords.\n"
                        + "Command: find KEYWORD [MORE_KEYWORDS]\n"
                        + "Example: find Acme Corp\n",

                "Smart Filtering: filter\n"
                        + "Command: filter [n\\NAME] [t\\TAG]…\n"
                        + "Example: filter t\\friend n\\John\n",

                "Editing Client Information: edit\n"
                        + "Command: edit INDEX [n\\NAME] [p\\PHONE] [e\\EMAIL] [a\\ADDRESS] [t\\TAG]…\n"
                        + "Example: edit 1 p\\98765432 e\\newemail@acme.com\n",

                "Deleting a Client: delete\n"
                        + "Command: delete INDEX\n"
                        + "Example: delete 1\n",

                "Clearing All Entries: clear\n"
                        + "Command: clear\n",

                "Exiting the Program: exit\n"
                        + "Command: exit\n",

                "\nRefer to the user guide: " + USERGUIDE_URL
        ));

        helpList.setFocusTraversable(false);
    }
    /**
     * Shows a new HelpWindow.
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }

    public void hide() {
        getRoot().hide();
    }

    public void focus() {
        getRoot().requestFocus();
    }

    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
