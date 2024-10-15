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
 * Detail view of a Person.
 */
public class PersonDetailView extends UiPart<Region> implements DetailView<Person> {

    private static final String FXML = "PersonDetailView.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Person person;

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
     * Initializes a new instance of a DetailView for a person.
     *
     * @param p The Person to display.
     */
    public PersonDetailView(Person p) {
        super(FXML);
        this.person = p;
        update(p);
    }

    @Override
    public void update(Person person) {
        requireNonNull(person);
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        relationship.setText(person.getRelationship().relationship);
    }
}
