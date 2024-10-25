package seedu.address.ui;

import static seedu.address.logic.commands.AddCommand.HELP_ADD_COMMAND;
import static seedu.address.logic.commands.DeleteCommand.HELP_DELETE_COMMAND;
import static seedu.address.logic.commands.EditCommand.HELP_EDIT_COMMAND;
import static seedu.address.logic.commands.ExitCommand.HELP_EXIT_COMMAND;
import static seedu.address.logic.commands.FindCommand.HELP_FIND_COMMAND;
import static seedu.address.logic.commands.FindTagCommand.HELP_FINDTAG_COMMAND;
import static seedu.address.logic.commands.ListCommand.HELP_LIST_COMMAND;
import static seedu.address.logic.commands.ListingAddCommand.HELP_LISTING_ADD_COMMAND;
import static seedu.address.logic.commands.ListingDeleteCommand.HELP_LISTING_DELETE_COMMAND;
import static seedu.address.logic.commands.RemarkCommand.HELP_REMARK_COMMAND;
import static seedu.address.logic.commands.ShowCommand.HELP_SHOW_COMMAND;
import static seedu.address.logic.commands.SortCommand.HELP_SORT_COMMAND;

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

    private static final String HELP_MORE_INFORMATION = "For more detailed information,"
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
            + HELP_LISTING_ADD_COMMAND
            + "\n\n"
            + HELP_LISTING_DELETE_COMMAND
            + "\n\n"
            + HELP_SHOW_COMMAND
            + "\n\n"
            + HELP_SORT_COMMAND
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
