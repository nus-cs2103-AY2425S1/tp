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

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-f15-2.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE =
            "============================================================================================\n"
            + "   H E L P   M E N U\n"
            + "============================================================================================\n"
            + "\n1. Add Student\n"
            + "   - add [ID] n/ [STUDENT_NAME] p/ [PHONE_NUMBER] a/ [ADDRESS] c/ [COURSE] t/ [TAGS]\n"
            + "\n2. Add a Student Grade\n"
            + "   - grade id/ [ID] m/ [MODULE] g/ [GRADE]\n"
            + "\n3. Add Module\n"
            + "   - module id/ [STUDENT_ID] m/ [MODULE]\n"
            + "\n4. Edit Student\n"
            + "   - edit [ID] [FIELD_TO_EDIT_PREFIX] [NEW_VALUE]\n"
            + "   - edit [ID] m/ [OLD_MODULE] [NEW_MODULE]\n"
            + "\n5. Find Student\n"
            + "   - find [ID]\n"
            + "\n6. Delete Student\n"
            + "   - delete id/ [ID]\n"
            + "\n7. List Students\n"
            + "   - list\n"
            + "\n8. Clear Data\n"
            + "   - clear\n"
            + "\n9. Exit Application\n"
            + "   - exit\n"
            + "\n============================================================================================\n"
            + "For more details, refer to the user guide: \n" + USERGUIDE_URL;

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
