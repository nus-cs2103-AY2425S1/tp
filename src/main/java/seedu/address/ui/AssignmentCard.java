package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.assignment.Assignment;

/**
 * An UI component that displays information of a {@code Assignment}.
 */
public class AssignmentCard extends UiPart<Region> {
    private static final String FXML = "AssignmentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/TaskMasterPro-level4/issues/336">The issue on TaskMasterPro level 4</a>
     */

    public final Assignment assignment;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label assignmentId;
    @FXML
    private Label projectId;
    @FXML
    private Label employeeId;

    /**
     * Creates a {@code AssignmentCode} with the given {@code Assignment} and index to display.
     */
    public AssignmentCard(Assignment assignment, int displayedIndex) {
        super(FXML);
        this.assignment = assignment;
        assignmentId.setText(displayedIndex + ". ");
        projectId.setText(assignment.getProject().getId().toString());
        employeeId.setText(assignment.getEmployee().getEmployeeId().toString());
    }
}
