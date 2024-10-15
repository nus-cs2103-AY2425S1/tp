package seedu.address.ui;

import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
            + "       add id/ 12345678 n/ John Doe p/ 99999999 a/ 123 Jane Doe Road c/ Computer Science t/ Student\n"
            + "\n2. Add a Student Grade\n"
            + "   - Purpose: Adds a grade for a student in a module.\n"
            + "   - Command Format:\n"
            + "       grade id/ [STUDENT_ID] m/ [MODULE] g/ [GRADE]\n"
            + "   - Example:\n"
            + "       grade id/ 12345678 m/ CS2103T g/ A\n"
            + "\n3. Edit Student\n"
            + "   - Purpose: Edits a student's details according to the fields specified.\n"
            + "   - Command Format:\n"
            + "       edit [STUDENT_ID] [FIELD_TO_EDIT_PREFIX] [NEW_VALUE]\n"
            + "   - Editable Fields:\n"
            + "       n/ [STUDENT_NAME], p/ [PHONE_NUMBER], e/ [EMAIL], a/ [ADDRESS], c/ [COURSE], t/ [TAG]\n"
            + "   - Example:\n"
            + "       edit 12345678 n/ Jane Doe p/ 88888888 e/ janedoe@gmail.com "
            +         "a/ 456 John Doe Road c/ Physics t/ Student\n"
            + "\n4. Delete Student\n"
            + "   - Purpose: Removes a student from the address book.\n"
            + "   - Command Format:\n"
            + "       delete id/ [STUDENT_ID]\n"
            + "   - Example:\n"
            + "       delete id/ 12345678\n"
            + "\n5. List Students\n"
            + "   - Purpose: Displays all students currently stored in the address book.\n"
            + "   - Command Format:\n"
            + "       list\n"
            + "   - Example:\n"
            + "       list\n"
            + "\n6. Clear Data\n"
            + "   - Purpose: Clears all student data from the address book.\n"
            + "   - Command Format:\n"
            + "       clear\n"
            + "   - Example:\n"
            + "       clear\n"
            + "\n7. Exit Application\n"
            + "   - Purpose: Exits the application.\n"
            + "   - Command Format:\n"
            + "       exit\n"
            + "   - Example:\n"
            + "       exit\n"
            + "\n============================================================================================\n"
            + "For more details, refer to the user guide:";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    protected Label helpMessage;

    @FXML
    protected Text hyperlinkText;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        hyperlinkText.setOnMouseClicked(this::openUserGuide);
        hyperlinkText.setFill(Color.rgb(173, 216, 230));
        hyperlinkText.setUnderline(true);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Opens the user guide in the default web browser when the hyperlink is clicked.
     *
     * @param event The mouse event triggered when the hyperlink is clicked.
     * @throws Exception If there is an error while trying to open the user guide,
     *                   such as an invalid URI or inability to access the browser.
     */
    protected void openUserGuide(MouseEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(USERGUIDE_URL));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * Returns the fill color of the hyperlink text.
     *
     * @return the Color object representing the fill color of the hyperlink text.
     */
    public Color getHyperlinkTextFill() {
        return (Color) hyperlinkText.getFill();
    }

    /**
     * Checks if the hyperlink text is underlined.
     *
     * @return true if the hyperlink text is underlined, false otherwise.
     */
    public boolean isHyperlinkTextUnderlined() {
        return hyperlinkText.isUnderline();
    }
}
