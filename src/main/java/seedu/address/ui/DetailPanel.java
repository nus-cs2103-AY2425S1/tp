package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.address.model.person.Person;

/**
 * Represents a Detail Panel that displays individual person details in the UI.
 * This class is responsible for handling the display of personal information such as name and email.
 * It extends {@code UiPart<Region>} where {@code Region} is a part of the JavaFX framework that deals with rendering parts of the user interface.
 *
 * <p>The FXML file 'DetailPanel.fxml' is used to define the layout of the panel, including labels for displaying the person's name and email.
 *
 * <p>Usage example:
 * <pre>
 *     DetailPanel detailPanel = new DetailPanel();
 *     detailPanel.setPerson(new Person("John Doe", "john.doe@example.com"));
 * </pre>
 */
public class DetailPanel extends UiPart<Region> {
    /**
     * The FXML file that represents the layout of the DetailPanel.
     */
    private static final String FXML = "DetailPanel.fxml";

    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    // Initialize other UI elements

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
     * @param person The person whose details are to be displayed on the panel. This object should contain all the relevant information.
     */
    public void setPerson(Person person) {
        nameLabel.setText(person.getName().fullName);
        emailLabel.setText(person.getEmail().value);
        // Update other fields as needed
    }
}
