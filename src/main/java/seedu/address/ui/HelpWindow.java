package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    private static final String HELP_INTRO = "Below are some basic instructions to get started using PROperty!";
    private static final String HELP_ADD_COMMAND = "Add Command \n"
            + "- Format: add n/NAME p/NUMBER [e/EMAIL] [a/ADDRESS] [t/TAG] [r/REMARK]\n"
            + "- Example: add n/John Doe p/12345678 t/Student\n"
            + "- Items in the [square brackets] are optional and can be input in any order!";
    private static final String HELP_EDIT_COMMAND = "Edit Command \n"
            + "- Format: edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG] [dt/TAG] [r/REMARK]\n"
            + "- Example: edit 1 p/87654321 t/Tutor dt/Student"
            + "- INDEX is the index of the customer displayed. Items in the [square brackets] are optional"
            + " and can be input in any order! TAG can be both a tag to be added or removed, depending on"
            + " the prefix for the item.";
    private static final String HELP_DELETE_COMMAND = "Delete Command\n"
            + "- Format: delete INDEX\n"
            + "- Example: delete 1\n"
            + "- INDEX is the index of the customer displayed.";
    private static final String HELP_LIST_COMMAND = "List Command\n"
            + "- Format: list\n"
            + "- Example: list";
    private static final String HELP_FIND_COMMAND = "Find Command\n"
            + "- Format: find KEYWORDS [MORE_KEYWORDS]\n"
            + "- Example: find John Doe\n"
            + "- Only names can be searched; Only full words will be matched. "
            + "The command is case insensitive. When more than one keyword is used,"
            + " persons matching at least one keyword will be returned.";
    private static final String HELP_FINDTAG_COMMAND = "Findtag Command\n"
            + "- Format: findtag TAG [MORE_TAGS]\n"
            + "- Example: findtag HDB Condo\n"
            + "- Tags are case insensitive.\n"
            + "- Items in the [square brackets] are optional. Persons with at least one matching tag will be returned.";
    private static final String HELP_REMARK_COMMAND = "Remark Command\n"
            + "- Format: remark INDEX r/[REMARKS]\n"
            + "- Example: remark 1 r/Prefers high rise apartments\n"
            + "- Items in the [square brackets] are optional.\n"
            + "- If [REMARKS] is left blank, the remark will be deleted from the specified Person at INDEX.";
    private static final String HELP_EXIT_COMMAND = "Exit Command\n"
            + "- Format: exit\n"
            + "- Example: exit";
    private static final String HELP_MORE_INFORMATION = "For more detailed information, "
            + " visit the PROperty User Guide at: https://ay2425s1-cs2103t-f15-3.github.io/tp/UserGuide.html";


    private static final String HELP_MESSAGE = HELP_INTRO
            + "\n\n"
            + HELP_ADD_COMMAND
            + "\n\n"
            + HELP_EDIT_COMMAND
            + "\n\n"
            + HELP_DELETE_COMMAND
            + "\n\n"
            + HELP_LIST_COMMAND
            + "\n\n"
            + HELP_FIND_COMMAND
            + "\n\n"
            + HELP_FINDTAG_COMMAND
            + "\n\n"
            + HELP_REMARK_COMMAND
            + "\n\n"
            + HELP_EXIT_COMMAND
            + "\n\n"
            + HELP_MORE_INFORMATION;

    private static final double DEFAULT_WIDTH = 800;
    private static final double DEFAULT_HEIGHT = 650;
    private static final double MIN_WIDTH = 800;
    private static final double MIN_HEIGHT = 700;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Text helpMessage;

    @FXML
    private ScrollPane scrollPane;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        setWindowDefaultSize(root);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Sets the default size of the window.
     */
    private void setWindowDefaultSize(Stage root) {
        root.setWidth(DEFAULT_WIDTH);
        root.setHeight(DEFAULT_HEIGHT);
        root.setMinWidth(MIN_WIDTH);
        root.setMinHeight(MIN_HEIGHT);
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
}
