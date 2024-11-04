package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";

    @FXML
    private ListView<Task> taskListView;

    /**
     * Creates a {@code TaskListPanel} with the given {@code ObservableList}.
     */
    public TaskListPanel(ObservableList<Task> taskList) {
        super(FXML);
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());

        taskList.forEach(this::addTaskListener);

        // Listen for changes in the taskList to add listeners for new tasks
        taskList.addListener((ListChangeListener<Task>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(this::addTaskListener);
                }
            }
        });
    }

    private void addTaskListener(Task task) {
        task.isCompleteProperty().addListener((observable, oldValue, newValue) -> taskListView.refresh());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code TaskListCard}.
     */
    class TaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
                getStyleClass().clear();
            } else {
                TaskListCard card = new TaskListCard(task, getIndex() + 1);
                setGraphic(card.getRoot());
                // Apply initial style based on task status
                updateStyle(task);
            }
        }

        private void updateStyle(Task task) {
            getStyleClass().clear();
            if (task.getStatus()) {
                getStyleClass().add("task-complete"); // Style for completed tasks
            } else {
                getStyleClass().add("task-incomplete"); // Style for incomplete tasks
            }
        }
    }
}
