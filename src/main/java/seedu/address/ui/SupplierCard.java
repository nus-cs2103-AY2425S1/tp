package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.supplier.Supplier;

/**
 * An UI component that displays information of a {@code Supplier}.
 */
public class SupplierCard extends UiPart<Region> {

    private static final String FXML = "SupplierListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Supplier supplier;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label company;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane products;
    @FXML
    private Label status;

    /**
     * Creates a {@code SupplierCode} with the given {@code Supplier} and index to display.
     */
    public SupplierCard(Supplier supplier, int displayedIndex) {
        super(FXML);
        this.supplier = supplier;
        id.setText(displayedIndex + ". ");
        name.setText(supplier.getName().fullName);
        phone.setText(supplier.getPhone().value);
        company.setText(supplier.getCompany().value);
        email.setText(supplier.getEmail().value);
        supplier.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        supplier.getProducts().stream()
                .sorted(Comparator.comparing(product -> product.productName))
                .forEach(product -> products.getChildren().add(new Label(product.productName)));
        setStatusStyle(supplier.getStatus().status);
        status.setText(supplier.getStatus().status.toUpperCase());
    }

    private void setStatusStyle(String currentStatus) {
        this.status.getStyleClass().clear();

        switch (currentStatus) {
        case "active":
            status.getStyleClass().add("status-success");
            status.getStyleClass().add("cell_small_label");
            break;
        case "inactive":
            status.getStyleClass().add("status-error");
            status.getStyleClass().add("cell_small_label");
            break;
        default:
            status.getStyleClass().add("status-default");
            status.getStyleClass().add("cell_small_label");
            break;
        }
    }
}
