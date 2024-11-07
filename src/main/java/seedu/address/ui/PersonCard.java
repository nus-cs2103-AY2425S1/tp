package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

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
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label identification;
    @FXML
    private Label status;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        status.setText(person.getStatus().toString());
        identification.setText(person.getIdentityNumber().toString());
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);

        setStatusStyle(person.getStatus().toString());
    }

    /**
     * Sets the color of the status label.
     */
    private void setStatusStyle(String statusText) {
        // Remove any previously set style classes
        status.getStyleClass().removeAll("status-low", "status-medium", "status-high", "status-new");

        // Add the correct style class based on the status value
        switch (statusText) {
        case "LOW":
            status.getStyleClass().add("status-low");
            break;
        case "MEDIUM":
            status.getStyleClass().add("status-medium");
            break;
        case "HIGH":
            status.getStyleClass().add("status-high");
            break;
        case "NEW":
            status.getStyleClass().add("status-new");
            break;
        default:
            // Do nothing, field will remain grey
        }
    }
}
