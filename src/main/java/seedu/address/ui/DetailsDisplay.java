package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * A UI component that displays details of a specific {@code Person}.
 */
public class DetailsDisplay extends UiPart<Region> {

    private static final String FXML = "DetailsDisplay.fxml";

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
    private Label phone;
    @FXML
    private Label emergencyContact;
    @FXML
    private Label address;
    @FXML
    private Label note;
    @FXML
    private FlowPane subjects;
    @FXML
    private Label level;
    @FXML
    private Label tasks;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public DetailsDisplay(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        phone.setText("Phone number: " + person.getPhone().value);
        emergencyContact.setText("Emergency Contact: " + person.getEmergencyContact().value);
        address.setText("Address: " + person.getAddress().value);
        note.setText(person.getNote().value);
        person.getSubjects().stream()
                .sorted(Comparator.comparing(subject -> subject.subjectName))
                .forEach(subject -> subjects.getChildren()
                        .add(new Label(person.getLevel().levelName + " " + subject.subjectName)));
        tasks.setText(person.getTaskList().toDescription());
    }
}

