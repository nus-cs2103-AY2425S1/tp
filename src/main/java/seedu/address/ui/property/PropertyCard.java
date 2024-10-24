package seedu.address.ui.property;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.property.Property;
import seedu.address.ui.UiPart;


/**
 * An UI component that displays information of a {@code Buyer}.
 */
public class PropertyCard extends UiPart<Region> {

    private static final String FXML = "PropertyListCard.fxml";
    public final Property property;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on BuyerList level 4</a>
     */
    @FXML
    private HBox propertyCardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label locationA;
    @FXML
    private Label askingPrice;
    @FXML
    private Label propertyType;

    /**
     * Creates a {@code BuyerCode} with the given {@code Buyer} and index to display.
     */
    public PropertyCard(Property property, int displayedIndex) {
        super(FXML);
        this.property = property;
        id.setText(displayedIndex + ". ");
        name.setText(property.getName().toString());
        phone.setText(property.getPhone().toString());
        locationA.setText(property.getLocation().toString());
        askingPrice.setText(property.getAskingPrice().toPrettyString());
        propertyType.setText(property.getPropertyType().toString());
    }
}
