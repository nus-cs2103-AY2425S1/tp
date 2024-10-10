package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-w11-3.github.io/tp/UserGuide.html#quick-start";
    public static final String PRODUCT_WEBSITE = "https://ay2425s1-cs2103t-w11-3.github.io/tp/";
    public static final String HELP_MESSAGE = "Welcome to the AcademyAssist Help Window!\n\n"
            + "Here are some useful commands to get started:\n\n"
            + "1. add: Adds a student's details\n"
            + "   Format: add n/NAME ic/IC_NUMBER e/EMAIL p/PHONE_NUMBER a/ADDRESS c/CLASS y/ACADEMIC_YEAR\n\n"
            + "2. view: Shows a list of all students\n"
            + "   Format: view\n\n"
            + "3. edit: Edits an existing student's details\n"
            + "   Format: edit STUDENT_ID FIELD:NEW_VALUE\n\n"
            + "4. find: Find students whose names contain any of the given keywords\n"
            + "   Format: find KEYWORD [MORE_KEYWORDS]\n\n"
            + "5. delete: Deletes the specified student\n"
            + "   Format: del STUDENT_ID\n\n"
            + "6. sort: arranges the list of students based on a specified field\n"
            + "   Format: sort FIELD\n\n"
            + "7. clear: Clears all entries from the address book\n"
            + "   Format: clear\n\n"
            + "8. exit: Exits the program\n"
            + "   Format: exit\n\n"
            + "For more detailed information, please refer to the links below\n";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyUserGuideButton;

    @FXML
    private Button copyProductWebsiteButton;

    @FXML
    private TextArea helpMessageArea;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessageArea.setText(HELP_MESSAGE);
        helpMessageArea.setWrapText(true);
        helpMessageArea.setEditable(false);
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
    private void copyUserGuideUrl() {
        copyToClipboard(USERGUIDE_URL);
    }

    /**
     * Copies the URL to the README to the clipboard.
     */
    @FXML
    private void copyProductWebsite() {
        copyToClipboard(PRODUCT_WEBSITE);
    }

    private void copyToClipboard(String content) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(content);
        clipboard.setContent(url);
    }
}
