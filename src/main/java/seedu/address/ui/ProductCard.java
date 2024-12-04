package seedu.address.ui;

import java.util.Comparator;

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
    private FlowPane tags;

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

        product.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        if (stock.getStockLevel() < stock.getMinStockLevel()) {
            cardPane.setStyle("-fx-background-color: #9d6260;");
        }
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
