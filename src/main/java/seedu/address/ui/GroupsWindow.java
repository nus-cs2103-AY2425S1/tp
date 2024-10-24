package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

/**
 * Controller for a help page
 */
public class GroupsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(GroupsWindow.class);
    private Logic logic;
    private static final String FXML = "GroupWindow.fxml";
    private GroupListPanel groupListPanel;
    @FXML
    private StackPane groupListPanelPlaceholder;
    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public GroupsWindow(Stage root, Logic logic) {
        super(FXML, root);
        this.logic = logic;
    }

    /**
     * Creates a new HelpWindow.
     */
    public GroupsWindow(Logic logic) {
        this(new Stage(), logic);
    }

    void fillInnerParts() {
        groupListPanel = new GroupListPanel(logic.getFilteredGroupList());
        groupListPanelPlaceholder.getChildren().add(groupListPanel.getRoot());
    }

    /**
     * Shows the group window.
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
        logger.fine("Showing groups.");
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
