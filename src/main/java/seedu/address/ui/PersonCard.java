package seedu.address.ui;

import java.util.Comparator;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
    private Label nric;
    @FXML
    private Label dateOfBirth;
    @FXML
    private Label gender;
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
    private FlowPane priority;
    @FXML
    private FlowPane appointments;
    @FXML
    private FlowPane medCons;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        nric.setText(person.getNric().value);
        dateOfBirth.setText(person.getDateOfBirth().value);
        gender.setText(person.getGender().value);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        applyPriorityBackground(priority, person.getPriority().priority);
        addLabelsToFlowPane(person.getMedCons(), medCons);
        addLabelsToFlowPane(person.getTags(), tags);
        addLabelsToFlowPane(person.getAppointments(), appointments);
    }

    private <T extends Comparable<T>> void addLabelsToFlowPane(Set<T> items, FlowPane flowPane) {
        items.stream()
                .sorted(Comparator.naturalOrder())
                .forEach(item -> flowPane.getChildren().add(new Label(item.toString())));
    }

    // Apply background color styles based on the priority value
    private void applyPriorityBackground(FlowPane flowPane, String priorityValue) {
        // Set up the label for priority
        Label priorityLabel = new Label(person.getPriority().priority);
        priorityLabel.getStyleClass().add("priority-label");
        // Remove any existing priority styles
        priority.getStyleClass().removeAll("priority-high-bg", "priority-medium-bg",
                "priority-low-bg", "priority-none-bg");

        // Add the new label with the corresponding background style
        switch (priorityValue) {
        case "HIGH":
            priorityLabel.getStyleClass().add("priority-high-bg");
            break;
        case "MEDIUM":
            priorityLabel.getStyleClass().add("priority-medium-bg");
            break;
        case "LOW":
            priorityLabel.getStyleClass().add("priority-low-bg");
            break;
        case "NONE":
            priorityLabel.getStyleClass().add("priority-none-bg");
            break;
        default:
            break;
        }

        // Add the label to the FlowPane
        priority.getChildren().add(priorityLabel);

    }
}
