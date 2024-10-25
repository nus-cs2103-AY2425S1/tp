package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.employee.Employee;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class EmployeeCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Employee person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label employeeId;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane skills;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public EmployeeCard(Employee person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        employeeId.setText("id: " + person.getEmployeeId().value);
        name.setText(person.getName().fullName);
        phone.setText("phone no: " + person.getPhone().value);
        address.setText("address: " + person.getAddress().value);
        email.setText("email: " + person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getSkills().stream()
                .sorted(Comparator.comparing(skill -> skill.skill))
                .forEach(skill -> skills.getChildren().add(new Label(skill.skill)));
    }
}
