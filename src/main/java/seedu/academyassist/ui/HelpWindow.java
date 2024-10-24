package seedu.academyassist.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.academyassist.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-w11-3.github.io/tp/UserGuide.html#quick-start";
    public static final String PRODUCT_WEBSITE = "https://ay2425s1-cs2103t-w11-3.github.io/tp/";
    public static final String HELP_MESSAGE = "Welcome to the AcademyAssist Help Window!\n\n"
            + "Here are some useful commands to get started:\n\n"
            + "1. add: Adds a student's details\n"
            + "   Format: add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS i/IC_NUMBER s/SUBJECT t/tag\n\n"
            + "2. view: Shows a list of all students\n"
            + "   Format: view\n\n"
            + "3. edit: Edits an existing student's details\n"
            + "   Format: edit STUDENT_ID FIELD/NEW_VALUE\n\n"
            + "4. find: Find students whose names contain any of the given keywords\n"
            + "   Format: find NAMES [MORE_NAMES]\n\n"
            + "5. addc: Adds a class to an existing student's record\n"
            + "   Format: addc STUDENT_ID s/SUBJECT\n\n"
            + "6. delete: Deletes the specified student\n"
            + "   Format: delete STUDENT_ID\n\n"
            + "7. sort: arranges the list of students based on a specified field\n"
            + "   Format: sort s/FIELD\n\n"
            + "8. detail: Shows the details of a specific student\n"
            + "   Format: detail STUDENT_ID\n\n"
            + "9. clear: Clears all entries from the management system\n"
            + "   Format: clear\n\n"
            + "10. exit: Exits the program\n"
            + "   Format: exit\n\n"
            + "For more detailed information, click the buttons below to open:\n"
            + "- User Guide\n"
            + "- README";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button openUserGuideButton;

    @FXML
    private Button openProductWebsiteButton;

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
     * Opens the URL to the user guide to the clipboard.
     */
    @FXML
    private void openUserGuideUrl() {
        openUrlInBrowser(USERGUIDE_URL);
    }

    /**
     * Opens the URL to the README to the clipboard.
     */
    @FXML
    private void openProductWebsite() {
        openUrlInBrowser(PRODUCT_WEBSITE);
    }

    private void openUrlInBrowser(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            logger.warning("Error opening URL: " + url);
        }
    }
}
