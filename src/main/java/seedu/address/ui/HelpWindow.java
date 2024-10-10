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
    public static final String HELP_MESSAGE = "List of commands: \n\n\n"
            + "<<Viewing help>>\n"
            + "Format: help\n\n\n"
            + "<<Adding a person>>\n"
            + "Format: add n/NAME p/PHONE_NUMBER e/EMAIL r/REMARK a/ADDRESS [t/TAG]\n\n\n"
            + "<<Adding a remark>>\n"
            + "Format: remark NRIC r/REMARK\n\n\n"
            + "<<Listing all persons>>\n"
            + "Format: list\n\n\n"
            + "<<Adding an appointment>>\n"
            + "Format: appointment NRIC app/APPOINTMENT\n\n\n"
            + "<<Editing a person>>\n"
            + "Format: edit NRIC [n/NAME] [p/PHONE] [e/EMAIL] [r/REMARK] [a/ADDRESS] [t/TAG]\n\n\n"
            + "<<Locating persons by name>>\n"
            + "Format: find KEYWORD [MORE_KEYWORDS]\n\n\n"
            + "<<Deleting a person>>\n"
            + "Format: delete NRIC\n\n\n"
            + "<<Clearing all entries>>\n"
            + "Format: clear\n\n\n"
            + "<<Exiting the program>>\n"
            + "Format: exit\n\n\n"
            + "Refer to the user guide: "
            + USERGUIDE_URL;
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
        helpMessage.setText(HELP_MESSAGE);
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
