package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.listing.Listing;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class ListingCard extends UiPart<Region> {
    private static final String FXML = "ListingListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Listing listing;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label price;
    @FXML
    private Label area;
    @FXML
    private Label region;
    @FXML
    private FlowPane buyers;
    @FXML
    private Label address;
    @FXML
    private Label seller;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public ListingCard(Listing listing, int displayedIndex) {
        super(FXML);
        this.listing = listing;
        id.setText(displayedIndex + ". ");
        name.setText(listing.getName().fullName);
        price.setText(listing.getPrice().toString());
        area.setText(listing.getArea().toString());
        region.setText(listing.getRegion().toString());
        address.setText(listing.getAddress().toString());
        seller.setText(listing.getSeller().getName().fullName);
    }

    // Getter methods for private fields
    public Label getId() {
        return id;
    }

    public Label getName() {
        return name;
    }

    public Label getPrice() {
        return price;
    }

    public Label getArea() {
        return area;
    }

    public Label getRegion() {
        return region;
    }

    public Label getAddress() {
        return address;
    }

    public Label getSeller() {
        return seller;
    }
}
