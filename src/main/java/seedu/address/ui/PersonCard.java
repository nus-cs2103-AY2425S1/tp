package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
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

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label studentId;
    @FXML
    private Label major;
    @FXML
    private Label year;
    @FXML
    private Label email;
    @FXML
    private FlowPane groups;
    @FXML
    private VBox fields;
    @FXML
    private Label comment;
    @FXML
    private Separator separator;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        studentId.setText(person.getStudentId().value);
        major.setText(person.getMajor().value);
        year.setText(person.getYear().value);
        email.setText(person.getEmail().value);
        comment.setText(person.getComment().value);
        person.getGroups().stream()
                .sorted(Comparator.comparing(group -> group.groupName))
                .forEach(group -> groups.getChildren().add(new Label(group.groupName)));


        removeOptionalLabels(fields);

        if (!fields.getChildren().contains(comment)) {
            fields.getChildren().remove(separator);
        }
        comment.setText("Comment: " + comment.getText());
        year.setText("Year " + year.getText());
    }

    private static void removeOptionalLabels(Pane parentNode) {
        parentNode.getChildren().removeIf(node -> {
            if (node instanceof Label) {
                Label label = (Label) node;
                return label.getText().isEmpty();
            }
            return false;
        });
    }

}
