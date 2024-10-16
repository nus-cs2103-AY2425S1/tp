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
    public static final String HELP_MESSAGE =
            "============================================================================================\n"
            + "   H E L P   M E N U\n"
            + "============================================================================================\n"
            + "\n1. Add Student\n"
            + "   - Purpose: Adds a student and their details to the address book.\n"
            + "   - Command Format:\n"
            + "       add id/ [STUDENT_ID] n/ [STUDENT_NAME] p/ [PHONE_NUMBER] a/ [ADDRESS] c/ [COURSE] t/ [TAGS]\n"
            + "   - Example:\n"
            + "       add id/ 12345678 n/ John Doe p/ 99999999 a/ 123 Jane Doe Road "
            + "c/ Computer Science t/ Student\n"
            + "\n2. Add a Student Grade\n"
            + "   - Purpose: Adds a grade for a student in a module.\n"
            + "   - Command Format:\n"
            + "       grade id/ [STUDENT_ID] m/ [MODULE] g/ [GRADE]\n"
            + "   - Example:\n"
            + "       grade id/ 12345678 m/ CS2103T g/ A\n"
            + "\n3. Add Module\n"
            + "   - Purpose: Adds a module for a student.\n"
            + "   - Command Format:\n"
            + "       module id/ [STUDENT_ID] m/ [MODULE]\n"
            + "   - Example:\n"
            + "       module id/ 12345678 m/ CS2103T\n"
            + "\n4. Edit Student\n"
            + "   - Purpose: Edits a student's details according to the fields specified.\n"
            + "   - Command Format:\n"
            + "       edit [STUDENT_ID] [FIELD_TO_EDIT_PREFIX] [NEW_VALUE]\n"
            + "   - Editable Fields:\n"
            + "       n/ [STUDENT_NAME], p/ [PHONE_NUMBER], e/ [EMAIL], a/ [ADDRESS], c/ [COURSE], t/ [TAG]\n"
            + "   - Example:\n"
            + "       edit 12345678 n/ Jane Doe p/ 88888888 e/ janedoe@gmail.com "
            + "a/ 456 John Doe Road c/ Physics t/ Student\n"
            + "\n5. Delete Student\n"
            + "   - Purpose: Removes a student from the address book.\n"
            + "   - Command Format:\n"
            + "       delete id/ [STUDENT_ID]\n"
            + "   - Example:\n"
            + "       delete id/ 12345678\n"
            + "\n6. List Students\n"
            + "   - Purpose: Displays all students currently stored in the address book.\n"
            + "   - Command Format:\n"
            + "       list\n"
            + "   - Example:\n"
            + "       list\n"
            + "\n7. Clear Data\n"
            + "   - Purpose: Clears all student data from the address book.\n"
            + "   - Command Format:\n"
            + "       clear\n"
            + "   - Example:\n"
            + "       clear\n"
            + "\n8. Exit Application\n"
            + "   - Purpose: Exits the application.\n"
            + "   - Command Format:\n"
            + "       exit\n"
            + "   - Example:\n"
            + "       exit\n"
            + "\n============================================================================================\n"
            + "For more details, refer to the user guide: " + USERGUIDE_URL;

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
