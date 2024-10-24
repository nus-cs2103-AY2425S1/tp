package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import seedu.address.model.listing.Listing;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class ListingCard extends UiPart<Region> {
    private static final String FXML = "ListingListCard.fxml";

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
    @FXML
    private Line underline;

    /**
     * Creates a {@code ListingCard} with the given {@code Listing} and index to display.
     */
    public ListingCard(Listing listing, int displayedIndex) {
        super(FXML);
        this.listing = listing;
        id.setText(displayedIndex + ". ");
        initializeName();
        initializeUnderline();
        initializePrice();
        initializeArea();
        initializeRegion();
        initializeAddress();
        initializeSeller();
    }

    private void initializeName() {
        name.setText(listing.getName().fullName);
    }

    private void initializeUnderline() {
        // Bind underline width to name label with adjustment
        underline.endXProperty().bind(name.widthProperty().add(45));
    }

    private void initializePrice() {
        price.setText(String.format("$%s", listing.getPrice().toString()));
    }

    private void initializeArea() {
        area.setText(String.format("%s m²", listing.getArea().toString()));
    }

    private void initializeRegion() {
        region.setText(listing.getRegion().toString());
    }

    private void initializeAddress() {
        address.setText(listing.getAddress().toString());
    }

    private void initializeSeller() {
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
