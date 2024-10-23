package seedu.address.ui;

import java.util.Comparator;

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

    // Add color constants for priority levels
    private static final String HIGH_PRIORITY_COLOR = "#eb3434"; // Red
    private static final String MEDIUM_PRIORITY_COLOR = "#ebb134"; // Orange
    private static final String LOW_PRIORITY_COLOR = "#289e35"; // Green

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label lastSeen;
    @FXML
    private FlowPane tags;
    @FXML
    private Label priority;
    @FXML
    private Label organisation;
    @FXML
    private Label remark;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        organisation.setText(person.getOrganisation().value);
        lastSeen.setText(person.getLastSeen().toString());
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        setPriorityLabel(person.getPriority().toString());
        remark.setText(person.getRemark().value);
    }

    /**
     * Sets the priority label with the appropriate text and color.
     */
    private void setPriorityLabel(String priorityValue) {
        priority.setText(priorityValue);

        switch (priorityValue.toLowerCase()) {
        case "high":
            priority.setStyle("-fx-background-color: " + HIGH_PRIORITY_COLOR + ";");
            break;
        case "medium":
            priority.setStyle("-fx-background-color: " + MEDIUM_PRIORITY_COLOR + ";");
            break;
        case "low":
            priority.setStyle("-fx-background-color: " + LOW_PRIORITY_COLOR + ";");
            break;
        default:
            // Keep default styling if priority is not recognized
            break;
        }
    }
}
