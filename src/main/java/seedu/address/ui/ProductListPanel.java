package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.product.Product;

/**
 * Panel containing the list of products.
 */
public class ProductListPanel extends UiPart<Region> {
    private static final String FXML = "ProductListPanel.fxml";

    @FXML
    private ListView<Product> productListView;

    /**
     * Creates a {@code ProductListPanel} with the given list of products.
     */
    public ProductListPanel(ObservableList<Product> productList) {
        super(FXML);
        productListView.setItems(productList);
        productListView.setCellFactory(listView -> new ProductListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Product} using a {@code ProductCard}.
     */
    class ProductListViewCell extends ListCell<Product> {
        @Override
        protected void updateItem(Product product, boolean empty) {
            super.updateItem(product, empty);

            if (empty || product == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ProductCard(product, getIndex() + 1).getRoot());
            }
        }
    }
}
