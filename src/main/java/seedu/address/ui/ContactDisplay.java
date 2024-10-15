package seedu.address.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

public class ContactDisplay extends VBox {
    private Label nameLabel;
    private Label phoneLabel;
    private Label emailLabel;

    public ContactDisplay() {
        nameLabel = new Label("Name:");
        phoneLabel = new Label("Phone:");
        emailLabel = new Label("Email:");

        getChildren().addAll(nameLabel, phoneLabel, emailLabel);
        setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0; -fx-border-color: #ccc;");
    }

    public void updateContactDetails(Person person) {
        nameLabel.setText("Name: " + person.getName());
        phoneLabel.setText("Phone: " + person.getPhone());
        emailLabel.setText("Email: " + person.getEmail());
    }

    public void clear() {
        nameLabel.setText("Name:");
        phoneLabel.setText("Phone:");
        emailLabel.setText("Email:");
    }
}

