package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

public class DetailPanel extends UiPart<VBox> {
    private static final String FXML = "DetailPanel.fxml";

    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    // Initialize other UI elements

    public DetailPanel() {
        super(FXML);
        // Set up further interactions or styling
    }

    public void setPerson(Person person) {
        nameLabel.setText(person.getName().fullName);
        emailLabel.setText(person.getEmail().value);
        // Update other fields as needed
    }
}
