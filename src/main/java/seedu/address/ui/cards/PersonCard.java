package seedu.address.ui.cards;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label studentNumber;
    @FXML
    private Label group;

    /**
     * Creates a {@code PersonCode} with the given {@code Student} and index to display.
     */
    public PersonCard(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        id.setWrapText(true);
        name.setText(student.getName().getFullName());
        name.setWrapText(true);
        email.setText(student.getEmail().getEmail());
        email.setWrapText(true);
        studentNumber.setText(student.getStudentNumber().getStudentNumber());
        studentNumber.setWrapText(true);
        if (student.getGroupName().isEmpty()) {
            group.setText("Not in group yet.");
        } else {
            group.setText(student.getGroupName().get().toString());
            group.setWrapText(true);
        }
        student.getTags().stream()
            .sorted(Comparator.comparing(Tag::getTagName))
            .forEach(tag -> {
                Label tagLabel = new Label(tag.getTagName());
                tagLabel.setWrapText(true);
                tags.getChildren().add(tagLabel);
            });
    }
}
