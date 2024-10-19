package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

//Solution below inspired by ChatGPT
/**
 * A UI component that displays detailed information of a selected {@code Person}.
 */
public class PersonPane extends UiPart<VBox> {

    private static final String FXML = "PersonPane.fxml";

    public final Person person;

    @FXML
    private VBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label remark;

    /**
     * Creates a {@code PersonPane} with the given {@code Person} to display.
     */
    public PersonPane(Person person) {
        super(FXML);
        this.person = person;
        displayPersonDetails();
    }

    /**
     * Default constructor for an empty pane.
     */
    public PersonPane() {
        super(FXML);
        this.person = null;
        emptyPane(); // Call to set the UI to an empty state
    }

    /**
     * Displays the details of the selected {@code Person}.
     */
    private void displayPersonDetails() {
        if (person != null) {
            name.setText(person.getName().fullName);
            tags.getChildren().clear();
            phone.setText(person.getPhone().value);
            address.setText(person.getAddress().value);
            email.setText(person.getEmail().value);
            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
            String remarkValue = person.getRemark().value;
            if (remarkValue != null && !remarkValue.trim().isEmpty()) {
                remark.setText(remarkValue);
                remark.setManaged(true);
            } else {
                remark.setManaged(false);
            }
        }
    }

    /**
     * Empties pane when no person is selected.
     */
    public void emptyPane() {
        name.setText("");
        phone.setText("");
        address.setText("");
        email.setText("");
        tags.getChildren().clear();
        remark.setText("");
        remark.setManaged(false);
    }
}
