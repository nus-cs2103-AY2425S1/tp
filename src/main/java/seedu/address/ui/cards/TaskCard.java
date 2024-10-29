package seedu.address.ui.cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
    private ImageView imageView;

    /**
     * Creates a {@code GroupCode} with the given {@code Group} and index to display.
     */
    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        name.setText(task.getTaskName().toString());
        deadline.setText("Deadline: " + task.getDeadline().toString());
        Image image = new Image(getClass().getResourceAsStream("/images/overdue_icon.png"));
        ZoneId zid = ZoneId.of("Asia/Singapore");
        LocalDateTime currentTime = LocalDateTime.now(zid);
        if (task.getDeadline().time.compareTo(currentTime) < 0) {
            imageView.setImage(image);
        }
    }
}
