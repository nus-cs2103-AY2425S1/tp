package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Person}.
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
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label tag;
    @FXML
    private Label course;

    @FXML
    private Label module;

    @FXML
    private StackPane tagPane;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        studentId.setText("StudentID: " + person.getStudentId().value);
        phone.setText(person.getPhone().value);
        tag.setText(person.getTag().role.getRole());
        course.setText(person.getCourse().course);
        course.getStyleClass().add("bold-text");

        if (person.getTag().role.getRole().equalsIgnoreCase("Student")) {
            tagPane.getStyleClass().add("student-pane");
        } else if (person.getTag().role.getRole().equalsIgnoreCase("Tutor")) {
            tagPane.getStyleClass().add("tutor-pane");
        }

        String modulesAsString = person.getModules().stream()
                .map(m -> m.toString() + "\n")
                .reduce("", (x, y) -> x + y);

        module.setText(modulesAsString.isEmpty() ? "No enrolled modules" : modulesAsString);
        /*person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));*/
    }
}
