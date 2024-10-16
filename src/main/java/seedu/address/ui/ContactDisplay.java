package seedu.address.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;
import seedu.address.logic.commands.HelpCommand;

/**
 * A UI component that displays detailed information of a selected contact.
 */
public class ContactDisplay extends VBox {
    private Label nameLabel;
    private Label phoneLabel;
    private Label emailLabel;
    private String msg = HelpCommand.SHOWING_TEMP_MESSAGE;

    /**
     * Constructs a ContactDisplay with default placeholder labels.
     */

    public ContactDisplay() {
        nameLabel = new Label("Name:");
        phoneLabel = new Label("Phone:");
        emailLabel = new Label("Email:");

        getChildren().addAll(nameLabel, phoneLabel, emailLabel);
        setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");
    }

    /**
     * Updates the contact display with the details of the specified person.
     *
     * @param person The person whose details are to be displayed.
     */
    public void updateContactDetails(Person person) {
        nameLabel.setText("Name: " + person.getName());
        phoneLabel.setText("Phone: " + person.getPhone());
        emailLabel.setText("Email: " + person.getEmail());
    }

    /**
     * Clears the contact details, resetting the labels to their default placeholders.
     */
    public void clear() {
        nameLabel.setText("Name:");
        phoneLabel.setText("Phone:");
        emailLabel.setText("Email:");
    }

    public void showHelpDisplay() {
        nameLabel.setText(msg);
        phoneLabel.setText(null);
        emailLabel.setText(null);
    }
}

