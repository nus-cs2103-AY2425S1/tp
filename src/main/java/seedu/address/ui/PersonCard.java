package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.buyer.Buyer;

/**
 * An UI component that displays information of a {@code Buyer}.
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

    public final Buyer buyer;

    @FXML
    private HBox personCardPane;
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
    private Label personType;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Buyer} and index to display.
     */
    public PersonCard(Buyer buyer, int displayedIndex) {
        super(FXML);
        this.buyer = buyer;
        id.setText(displayedIndex + ". ");
        name.setText(buyer.getName().fullName);
        phone.setText(buyer.getPhone().value);
        address.setText(buyer.getAddress().value);
        email.setText(buyer.getEmail().value);
        personType.setText(buyer.getPersonType().value.toString());
        buyer.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
