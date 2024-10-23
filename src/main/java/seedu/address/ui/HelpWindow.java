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
 * Controller for a help page.
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
    public static final String HELP_MESSAGE = "Copy the link for the complete user guide  ---> ";

    public static final String HELP_USAGE = "===============================\n"
            + "         Quick Command Summary\n"
            + "===============================\n"
            + "1. Help\n"
            + "   Displays help information.\n"
            + "   Format: help\n\n"
            + "2. Add a Person\n"
            + "   Adds a new contact to the record.\n"
            + "   Format: add n/NAME p/PHONE e/EMAIL t/TELEGRAM r/ROLE ([f/])\n\n"
            + "3. List All Persons\n"
            + "   Displays all contacts.\n"
            + "   Format: list\n\n"
            + "4. Edit a Person\n"
            + "   Edits an existing contact by index.\n"
            + "   Format: edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [t/TELEGRAM] [r/ROLE] ([f/] / [nf/])\n\n"
            + "5. Find by Name or Role or Telegram or Favourites\n"
            + "   Finds contacts whose names, roles or telegram contain the specified keywords or\n"
            + "   if they are marked as favourites.\n"
            + "   Format: find [n/NAME] [r/ROLE] [t/TELEGRAM] [f/]\n\n"
            + "6. View contact by telegram handle\n"
            + "   View detailed information of a contact with the specified telegram handle\n"
            + "   Format: view t/TELEGRAM\n\n"
            + "7. Delete a Person\n"
            + "   Deletes a contact by index.\n"
            + "   Format: delete INDEX\n\n"
            + "8. Clear All Entries\n"
            + "   Clears all contacts from the address book.\n"
            + "   Format: clear\n\n"
            + "9. Exit\n"
            + "   Exits the application.\n"
            + "   Format: exit";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private Label helpUsage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        helpUsage.setText(HELP_USAGE);
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
