package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;



/**
 * A detailed view that displays the full details of a {@code Person}.
 * This view updates dynamically when a new {@code Person} is provided.
 */
public class PersonDetailView extends UiPart<Region> implements DetailView<Person> {

    private static final String FXML = "PersonDetailView.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private VBox personDetailView;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label relationship;

    /**
     * Initializes a new {@code PersonDetailView} for displaying the details of the provided {@code Person}.
     * It sets the person's details (name, phone number, email, and relationship) in the view immediately.
     *
     */
    public PersonDetailView() {
        super(FXML);
    }

    @Override
    public void update(Person person) {
        requireNonNull(person);
        getRoot().setVisible(true);
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        relationship.setText(person.getRelationship().relationship);
    }
}
