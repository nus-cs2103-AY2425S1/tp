package seedu.address.ui.cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.group.Group;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;


/**
 * An UI component that displays information of a {@code Task} in a {@code Group}.
 */
public class GroupTaskCard extends UiPart<Region> {

    private static final String FXML = "GroupTaskCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Group group;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label tasks;

    /**
     * Creates a {@code GroupCode} with the given {@code Group} and index to display.
     */
    public GroupTaskCard(Group group, int displayedIndex) {
        super(FXML);
        this.group = group;
        name.setText(group.getGroupName().getGroupName() + " Tasks");
        name.setWrapText(true);
        String taskList = "";
        int count = 1;
        for (Task task : group.getTasks()) {
            taskList += ("" + count + ". " + task.getTaskName() + " [" + task.getDeadline() + "] "
                + "[" + task.getStatus() + "]");
            taskList += "\n";
            count++;
        }
        tasks.setText(taskList);
        tasks.setWrapText(true);
    }
}
