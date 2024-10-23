package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.property.Property;

/**
 * A UI component that displays information of a {@code Property}.
 */
public class PropertyCard extends UiPart<Region> {

    private static final String FXML = "PropertyCard.fxml";

    public final Property property;

    @FXML
    private HBox propertyCardPane;
    @FXML
    private Label id;
    @FXML
    private Label postalCode;
    @FXML
    private Label typeLabel;
    @FXML
    private Label unit;
    @FXML
    private Label type;
    @FXML
    private Label bid;
    @FXML
    private Label ask;

    /**
     * Creates a {@code PropertyCard} with the given {@code Property} and index to display.
     */
    public PropertyCard(Property property, int displayedIndex) {
        super(FXML);
        this.property = property;
        id.setText(displayedIndex + ". ");
        postalCode.setText(property.getPostalCode().value);
        unit.setText(property.getUnit().value);
        type.setText("Type: " + property.getType().value);
        bid.setText("Bid: $" + property.getBid().value);
        ask.setText("Ask: $" + property.getAsk().value);
    }

    public HBox getPropertyCardPane() {
        return this.propertyCardPane;
    }
}
