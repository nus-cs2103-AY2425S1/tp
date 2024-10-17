package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

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
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label company;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane products;
    @FXML
    private Label status;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        company.setText(person.getCompany().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getProducts().stream()
                .sorted(Comparator.comparing(product -> product.productName))
                .forEach(product -> products.getChildren().add(new Label(product.productName)));
        setStatusStyle(person.getStatus().status);
        status.setText(person.getStatus().status.toUpperCase());
    }

    private void setStatusStyle(String currentStatus) {
        this.status.getStyleClass().clear();

        switch (currentStatus) {
        case "active":
            status.getStyleClass().add("status-success");
            status.getStyleClass().add("cell_small_label");
            break;
        case "inactive":
            status.getStyleClass().add("status-error");
            status.getStyleClass().add("cell_small_label");
            break;
        default:
            status.getStyleClass().add("status-default");
            status.getStyleClass().add("cell_small_label");
            break;
        }
    }
}
