package seedu.address.ui;

import java.util.Comparator;

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
        initializeBuyers();
    }

    private void initializeName() {
        String actualName = listing.getName().fullName;

        // Check if the listing name length is greater than 45
        if (actualName.length() > 45) {
            // Truncate the listing name to 45 characters and add "..."
            name.setText(actualName.substring(0, 45) + "...");
        } else {
            name.setText(actualName);
        }
    }

    private void initializeUnderline() {
        // Bind underline width to name label with adjustment
        underline.endXProperty().bind(name.widthProperty().add(45));
    }

    private void initializePrice() {
        String actualPrice = listing.getPrice().toString();

        // Check if the price length is greater than 15
        if (actualPrice.length() > 15) {
            // Truncate the price to 15 characters and add "..."
            actualPrice = actualPrice.substring(0, 15) + "...";
        }

        price.setText(String.format("$%s", actualPrice));
    }

    private void initializeArea() {
        String actualArea = listing.getArea().toString();

        // Check if the area length is greater than 10
        if (actualArea.length() > 10) {
            // Truncate the area to 10 characters and add "..."
            actualArea = actualArea.substring(0, 10) + "...";
        }

        area.setText(String.format("%s m²", actualArea));
    }

    private void initializeRegion() {
        region.setText(listing.getRegion().toString());

        String existingStyle = region.getStyle();
        String newBackgroundColor = "-fx-background-color: " + listing.getRegion().getColor() + ";";
        region.setStyle(existingStyle + newBackgroundColor);
    }

    private void initializeAddress() {
        String actualAddress = listing.getAddress().toString();

        // Check if the address length is greater than 55
        if (actualAddress.length() > 55) {
            // Truncate the address to 55 characters and add "..."
            address.setText(actualAddress.substring(0, 55) + "...");
        } else {
            address.setText(actualAddress);
        }
    }

    private void initializeSeller() {
        String actualSeller = listing.getSeller().getName().fullName;

        // Check if the seller name length is greater than 55
        if (actualSeller.length() > 55) {
            // Truncate the seller name to 55 characters and add "..."
            actualSeller = actualSeller.substring(0, 55) + "...";
        }

        seller.setText(actualSeller);
    }

    private void initializeBuyers() {
        buyers.setHgap(10);
        buyers.setVgap(10);

        listing.getBuyers().stream()
                .sorted(Comparator.comparing(buyer -> buyer.getName().fullName))
                .forEach(buyer -> {
                    String actualName = buyer.getName().fullName;

                    // Check if the buyer name length is greater than 55
                    if (actualName.length() > 55) {
                        // Truncate the buyer name to 55 characters and add "..."
                        actualName = actualName.substring(0, 55) + "...";
                    }

                    Label buyerLabel = new Label(actualName);
                    buyers.getChildren().add(buyerLabel);
                });
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

    public FlowPane getBuyers() {
        return buyers;
    }
}
