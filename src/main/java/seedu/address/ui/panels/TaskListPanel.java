package seedu.address.ui.panels;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.group.Group;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;
import seedu.address.ui.cards.TaskCard;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    private ObservableList<Group> groupList;

    @FXML
    private ListView<Task> taskListView;

    @FXML
    private VBox taskHeader;

    /**
     *   Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public TaskListPanel(ObservableList<Task> taskList, ObservableList<Group> groupList) {
        super(FXML);
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        this.groupList = groupList;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Student} using a {@code PersonCard}.
     */
    class TaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(groupList, task, getIndex() + 1).getRoot());
            }
        }
    }
}
