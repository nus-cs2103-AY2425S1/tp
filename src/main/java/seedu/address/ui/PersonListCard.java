package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Person;
import seedu.address.model.person.Vendor;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonListCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private ScrollPane contentContainer;
    @FXML
    private VBox cardPaneContents;
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
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonListCard(Person person, int displayedIndex, ListView<Person> personListView) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText("Phone: " + person.getPhone().value);
        address.setText("Address: " + person.getAddress().value);
        email.setText("Email: " + person.getEmail().value);

        if (person instanceof Guest) {
            Guest g = (Guest) person;
            Label rsvpLabel = new Label("RSVP: " + g.getRsvp());
            addLabel(rsvpLabel);

            Label relationLabel = new Label("Relation: " + g.getRelation());
            addLabel(relationLabel);
        } else {
            Vendor v = (Vendor) person;
            Label companyLabel = new Label("Company: " + v.getCompany().value);
            addLabel(companyLabel);

            Label budgetLabel = new Label("Budget: $" + v.getBudget().toString());
            addLabel(budgetLabel);
        }

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        contentContainer.setOnMouseClicked(event -> {
            personListView.getSelectionModel().select(displayedIndex - 1);
            event.consume();
        });
    }

    /**
     * Adds a label to the cardPaneContents with a specific style class.
     * @param label The Label to be added to the UI.
     */
    public void addLabel(Label label) {
        label.getStyleClass().add("cell_small_label");
        cardPaneContents.getChildren().add(label);
    }
}
