package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.person.Major;
import seedu.address.model.person.Person;

/**
 * Represents a Detail Panel that displays individual person details in the UI.
 * This class is responsible for handling the display of personal information such as name and email.
 * It extends {@code UiPart<Region>} where {@code Region} is a part of the JavaFX framework that deals with rendering
 * parts of the user interface.
 *
 * <p>The FXML file 'DetailPanel.fxml' is used to define the layout of the panel, including labels for displaying the
 * person's name and email.
 *
 * <p>Usage example:
 * <pre>
 *     DetailPanel detailPanel = new DetailPanel();
 *     detailPanel.setPerson(new Person("John Doe", "john.doe@example.com"));
 * </pre>
 */
public class DetailPanel extends UiPart<Region> implements SelectionListener {
    /**
     * The FXML file that represents the layout of the DetailPanel.
     */
    private static final String FXML = "DetailPanel.fxml";

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
    private Label role;
    @FXML
    private Label major;
    @FXML
    private FlowPane tags;

    // add meetings later

    private Person person;
    private int displayedIndex;

    /**
     * Constructs a new DetailPanel.
     * This constructor loads the layout from the specified FXML file and initializes
     * the UI components configured in the FXML.
     */
    public DetailPanel() {
        super(FXML);
        // Set up further interactions or styling
    }

    /**
     * Sets the person details on the DetailPanel.
     * This method updates the text of the labels with the provided person's details.
     *
     */
    public void updateDetails() {
        name.setText("Name: " + person.getName().fullName);
        id.setText("ID: " + displayedIndex);
        phone.setText("Phone: " + person.getPhone().value);
        address.setText("Address: " + person.getAddress().value);
        email.setText("Email: " + person.getEmail().value);
        role.setText("Role: " + person.getRole());
        major.setText("Major: " + getMajorFullName(person.getMajor()));
        // Update other fields as needed
    }

    @Override
    public void onPersonSelected(Person person, int index) {
        setPerson(person, index);
    }

    public void setPerson(Person person, int index) {
        this.person = person;
        this.displayedIndex = index + 1;
        updateDetails();
    }

    private String getMajorFullName(Major major) {
        switch (major.toString()) {  // Using toLowerCase to handle case variations
        case "cs":
            return "Computer Science";
        case "bza":
            return "Business Analytics";
        case "isys":
            return "Information System";
        case "isec":
            return "Information Security";
        case "ceg":
            return "Computer Engineering";
        default:
            return "Unknown Major";  // Default case if the major code doesn't match known ones
        }
    }
}
