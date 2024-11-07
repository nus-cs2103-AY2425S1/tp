package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Student student;
    public final Color GRADED = Color.BLUE;
    public final Color SUBMITTED = Color.YELLOW;
    public final Color PENDING = Color.RED;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label contactNumber;
    @FXML
    private Label tutorialGroup;
    @FXML
    private Label studentNumber;
    @FXML
    private FlowPane assignments;

    public Color getAssignmentColor(Assignment assignment) {
        return assignment.getState() == Assignment.State.GRADED
                ? GRADED
                : assignment.getState() == Assignment.State.SUBMITTED
                    ? SUBMITTED
                    : PENDING;
    }

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public StudentCard(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        contactNumber.setText(student.getPhone().value);
        tutorialGroup.setText(student.getTutorialGroup().value);
        studentNumber.setText(student.getStudentNumber().value);

        student.getAssignments().forEach(assignment -> {
            Label label = new Label(assignment.getAssignmentName().toString());
            label.setBackground(new Background(new BackgroundFill(
                    getAssignmentColor(assignment),
                    null,
                    null
            )));
            assignments.getChildren().add(label);
        });

        // Updates the flow pane when the list of assignments changes
        student.getAssignments().addListener((ListChangeListener<Assignment>) change -> {
            while (change.next()) {
                assignments.getChildren().clear();
                student.getAssignments().forEach(
                        assignment -> {
                            Label label = new Label(assignment.getAssignmentName().toString());
                            label.setBackground(new Background(new BackgroundFill(
                                    getAssignmentColor(assignment),
                                    null,
                                    null
                            )));
                            assignments.getChildren().add(label);
                        });
            }
        });
    }
}
