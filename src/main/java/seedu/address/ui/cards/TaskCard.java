package seedu.address.ui.cards;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.group.Group;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label deadline;
    @FXML
    private Label groups;

    /**
     * Creates a {@code GroupCode} with the given {@code Group} and index to display.
     */
    public TaskCard(ObservableList<Group> groupList, Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        id.setWrapText(true);
        name.setText(task.getTaskName().toString());
        name.setWrapText(true);
        deadline.setText("Deadline: " + task.getDeadline().toString());
        ZoneId zid = ZoneId.of("Asia/Singapore");
        LocalDateTime currentTime = LocalDateTime.now(zid);
        if (task.getDeadline().getTime().isBefore(currentTime)) {
            String groupsOverdue = "Overdue Groups:";
            String groupsComplete = "Complete Groups:";
            ObservableList<Group> completeGroupList = groupList;
            try {
                groupsOverdue = groupList.stream()
                    .filter(x -> x.getTasks().stream().filter(y -> y.equals(task))
                    .filter(c -> c.getStatus().equals(Status.OVERDUE)).count() > 0)
                    .map(z -> z.getGroupName().getGroupName())
                    .reduce(groupsOverdue, (a, b) -> a + "\n" + b);
            } catch (IndexOutOfBoundsException ioe) {
                groupsOverdue = "Overdue Groups:";
            }
            try {
                groupsComplete = completeGroupList.stream()
                    .filter(x -> x.getTasks().stream().filter(y -> y.equals(task))
                    .filter(c -> c.getStatus().equals(Status.COMPLETED)).count() > 0)
                    .map(z -> z.getGroupName().getGroupName())
                    .reduce(groupsComplete, (a, b) -> a + "\n" + b);
            } catch (IndexOutOfBoundsException ioe) {
                groupsComplete = "Complete Groups:";
            }
            groups.setText(groupsOverdue + "\n" + "\n" + groupsComplete);
        } else {
            String groupsPending = "Pending Groups:";
            String groupsComplete = "Complete Groups:";
            ObservableList<Group> completeGroupList = groupList;
            try {
                groupsPending = groupList.stream()
                    .filter(x -> x.getTasks().stream().filter(y -> y.equals(task))
                    .filter(c -> c.getStatus().equals(Status.PENDING)).count() > 0)
                    .map(z -> z.getGroupName().getGroupName())
                    .reduce(groupsPending, (a, b) -> a + "\n" + b);
            } catch (IndexOutOfBoundsException ioe) {
                groupsPending = "Pending Groups:";
            }
            try {
                groupsComplete = completeGroupList.stream()
                    .filter(x -> x.getTasks().stream().filter(y -> y.equals(task))
                    .filter(c -> c.getStatus().equals(Status.COMPLETED)).count() > 0)
                    .map(z -> z.getGroupName().getGroupName())
                    .reduce(groupsComplete, (a, b) -> a + "\n" + b);
            } catch (IndexOutOfBoundsException ioe) {
                groupsComplete = "Complete Groups:";
            }
            groups.setText(groupsPending + "\n" + "\n" + groupsComplete);
        }

    }
}
