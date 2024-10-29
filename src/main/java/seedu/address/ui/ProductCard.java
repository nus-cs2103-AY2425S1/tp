package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.product.Product;
import seedu.address.model.product.StockLevel;

/**
 * An UI component that displays information of a {@code Product}.
 */
public class ProductCard extends UiPart<Region> {
    private static final String FXML = "ProductCard.fxml";

    public final Product product;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label stockLevel;
    @FXML
    private Label supplierName;
    @FXML
    private Label minStockLevel;
    @FXML
    private Label maxStockLevel;
    @FXML
    private FlowPane tags; // FlowPane for displaying tags

    /**
     * Creates a {@code ProductCard} with the given {@code Product} and index to display.
     */
    public ProductCard(Product product, int displayedIndex) {
        super(FXML);
        this.product = product;

        id.setText(displayedIndex + ". ");
        name.setText(product.getName().toString());

        // Retrieve StockLevel object
        StockLevel stock = product.getStockLevel();
        stockLevel.setText("Stock Level: " + stock.getStockLevel());
        minStockLevel.setText("Min Stock Level: " + stock.getMinStockLevel());
        maxStockLevel.setText("Max Stock Level: " + stock.getMaxStockLevel());

        supplierName.setText("Supplier: " + (product.isAssigned() ? product.getSupplierName().toString() : "None"));

        // Populate tags in the FlowPane
        product.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.toString());
            tagLabel.getStyleClass().add("tag_label"); // Optional: add a style class for custom styling
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // Check for self-comparison
        if (other == this) {
            return true;
        }

        if (!(other instanceof ProductCard)) {
            return false;
        }

        ProductCard card = (ProductCard) other;
        return id.getText().equals(card.id.getText())
                && product.equals(card.product);
    }
}
