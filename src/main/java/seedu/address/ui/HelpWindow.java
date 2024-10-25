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

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-t12-2.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = """
        Replace UPPERCASE content with relevant details. Details in Square Brackets are OPTIONAL to provide.\n
            General Commands:
                1. help: Show a basic help message with a link to the PlanPerfect User Guide for advanced support.\n
            Contact Commands:
                1. add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG1 TAG2 ...]: Add a contact with up to 6 tags.
                2. list: Show all saved contacts.
                3. edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS]: Edit a contact’s personal detail(s).
                4. tag INDEX t/TAG1 TAG2 ... : Add between 1 to 6 tag(s) to a contact.
                5. untag INDEX t/TAG1 TAG2 ... : Remove the specified tag(s) from a contact.
                6. find KEYWORD1 KEYWORD2 ... : Find contacts with any of the given keywords in their name.
                7. filter t/TAG1 TAG2 ... : Filter contacts by the specified tag(s).
                8. delete INDEX: Delete the contact in the specified index.
                9. sort: Sort the contacts in the list by name.
                10. clear: Clear all contacts (following a confirmation message).\n
            Wedding Commands:
                1. addw n/WEDDING_NAME d/DATE [c/CONTACT1_INDEX ...]: Add wedding with 0 or more contacts.
                2. view WEDDING_INDEX: List the contacts assigned to the wedding at the specified index.
                3. editw WEDDING_INDEX [n/WEDDING_NAME] [d/DATE]: Edit a wedding's detail(s).
                4. assign WEDDING_INDEX c/CONTACT1_INDEX ... : Assign 1 or more contacts to the specified wedding.
                5. unassign WEDDING_INDEX c/CONTACT1_INDEX ... : Unassign 1 or more contacts from the specified wedding.
                6. deletew WEDDING_INDEX: Delete the specified wedding from PlanPerfect (contacts are not deleted).\n
            Other Commands:
                1. taglist: List all tags currently in use in PlanPerfect.
                2. exit: Exit the program.\n
        For more detailed help, refer to the PlanPerfect User Guide:""" + ' ' + USERGUIDE_URL;

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
