package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * A UI component that displays detailed information of a selected contact.
 */
public class ContactDisplay extends UiPart<Region> {
    private static final String FXML = "ContactDisplay.fxml";

    @FXML
    private VBox cardPane;
    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label tagLabel;
    @FXML
    private FlowPane tags;

    /**
     * Constructs a ContactDisplay with default placeholder labels.
     */

    public ContactDisplay() {
        super(FXML);
    }

    /**
     * Updates the contact display with the details of the specified person.
     *
     * @param person The person whose details are to be displayed.
     */
    public void updateContactDetails(Person person) {
        nameLabel.setText("Name: " + person.getName().fullName);
        categoryLabel.setText("Category: " + person.getCategory());
        phoneLabel.setText("Phone: " + person.getPhone().value);
        emailLabel.setText("Email: " + person.getEmail().value);
        addressLabel.setText("Address: " + person.getAddress().value);
        tags.getChildren().clear();
        person.getTags().stream()
        .sorted(Comparator.comparing(tag -> tag.tagName))
        .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Clears the contact details, resetting the labels to their default placeholders.
     */
    public void clear() {
        nameLabel.setText("Name:");
        categoryLabel.setText("Category:");
        phoneLabel.setText("Phone:");
        emailLabel.setText("Email:");
        addressLabel.setText("Address:");
        tags.getChildren().clear();
    }
}

