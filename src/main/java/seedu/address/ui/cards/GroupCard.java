package seedu.address.ui.cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.group.Group;
import seedu.address.model.student.Student;
import seedu.address.ui.UiPart;


/**
 * An UI component that displays information of a {@code Group}.
 */
public class GroupCard extends UiPart<Region> {

    private static final String FXML = "GroupListCard.fxml";
    private static final String NO_STUDENTS_YET = "No students yet!";

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
    private Label students;

    /**
     * Creates a {@code GroupCode} with the given {@code Group} and index to display.
     */
    public GroupCard(Group group, int displayedIndex) {
        super(FXML);
        this.group = group;
        id.setText(displayedIndex + ". ");
        name.setText(group.getGroupName().getGroupName());
        String studentList = "";
        for (Student student : group.getStudents()) {
            studentList += student.toDisplayString();
            studentList += "\n";
        }
        if (group.getStudents().size() == 0) {
            students.setText(NO_STUDENTS_YET);
        } else {
            students.setText(studentList);
        }
    }
}
