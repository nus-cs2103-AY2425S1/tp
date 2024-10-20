package seedu.address.ui;

import java.util.Comparator;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;

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
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane sellingProperties;
    @FXML
    private FlowPane buyingProperties;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        // Initialize the properties display
        updateSellingProperties();
        updateBuyingProperties();

        // Listen to changes in selling properties and buying properties
        person.getListOfSellingProperties().addListener((ListChangeListener<Property>)
                change -> updateSellingProperties());
        person.getListOfBuyingProperties().addListener((ListChangeListener<Property>)
                change -> updateBuyingProperties());
    }

    private void updateSellingProperties() {
        sellingProperties.getChildren().clear(); // Clear old data
        int[] sellingIndex = {1};
        person.getListOfSellingProperties().forEach(property -> {
            Label label = new Label(sellingIndex[0] + ". " + property.toString());
            label.getStyleClass().add("cell_small_label");
            sellingProperties.getChildren().add(label);
            sellingIndex[0]++;
        });
    }

    private void updateBuyingProperties() {
        buyingProperties.getChildren().clear(); // Clear old data
        int[] buyingIndex = {1};
        person.getListOfBuyingProperties().forEach(property -> {
            Label label = new Label(buyingIndex[0] + ". " + property.toString());
            label.getStyleClass().add("cell_small_label");
            buyingProperties.getChildren().add(label);
            buyingIndex[0]++;
        });
    }
}
