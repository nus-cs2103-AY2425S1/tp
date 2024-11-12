package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Event;
import seedu.address.model.task.Task;

/**
 * A UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String DEFAULT_FXML = "TaskListCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label isDone;

    /**
     * Creates a {@code TaskCard} with the given {@code Task} and index to display.
     * Uses the default FXML file (TaskListCard.fxml).
     */
    public TaskCard(Task task, int displayedIndex) {
        this(task, displayedIndex, DEFAULT_FXML);
    }


    /**
     * Creates a {@code TaskCard} with the given {@code Task} and index to display,
     * using the specified FXML file.
     */
    public TaskCard(Task task, int displayedIndex, String fxmlFileName) {
        super(fxmlFileName);
        this.task = task;
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription());

        if (task instanceof Deadline) {
            date.setText("By: " + ((Deadline) task).getBy().toString());
        } else if (task instanceof Event) {
            date.setText(("From: " + ((Event) task).getFrom() + " to " + ((Event) task).getTo()));
        }

        isDone.setText(task.getIsDone() ? "Completed" : "Incomplete");
        isDone.setStyle(task.getIsDone() ? "-fx-text-fill: #008000" : "-fx-text-fill: #800020");
    }

    public Label getDescriptionLabel() {
        return description;
    }

    public Label getIdLabel() {
        return id;
    }

    public Label getDateLabel() {
        return date;
    }

    public Label getIsDoneLabel() {
        return isDone;
    }
}

