package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Status;

/**
 * An UI component that displays information of a {@code Delivery}.
 */
public class DeliveryCard extends UiPart<Region> {

    private static final String FXML = "DeliveryListCard.fxml";

    public final Delivery delivery;

    @FXML
    private HBox cardPane;
    @FXML
    private Label productName;
    @FXML
    private Label id;
    @FXML
    private Label senderName;
    @FXML
    private Label status;
    @FXML
    private Label deliveryTime;
    @FXML
    private Label cost;
    @FXML
    private Label quantity;


    /**
     * Creates a {@code DeliveryCard} with the given {@code Delivery} and index to display.
     */
    public DeliveryCard(Delivery delivery, int displayedIndex) {
        super(FXML);
        this.delivery = delivery;
        id.setText(displayedIndex + ". ");
        productName.setText(delivery.getDeliveryProduct().toString());
        senderName.setText(delivery.getDeliverySender().getName().getName());
        status.setText(delivery.getDeliveryStatus().name());
        setStatusStyle(delivery.getDeliveryStatus());
        deliveryTime.setText(delivery.getDeliveryDate().toString());
        cost.setText(delivery.getDeliveryCost().displayString());
        quantity.setText(delivery.getDeliveryQuantity().toString());
    }

    private void setStatusStyle(Status currentStatus) {
        this.status.getStyleClass().clear();

        switch (currentStatus) {
        case PENDING:
            status.getStyleClass().add("status-warning");
            status.getStyleClass().add("cell_small_label");
            break;
        case DELIVERED:
            status.getStyleClass().add("status-success");
            status.getStyleClass().add("cell_small_label");
            break;
        case CANCELLED:
            status.getStyleClass().add("status-error");
            status.getStyleClass().add("cell_small_label");
            break;
        default:
            status.getStyleClass().add("status-default");
            status.getStyleClass().add("cell_small_label");
            break;
        }
    }

    public String getProductName() {
        return productName.getText();
    }

    public String getSenderName() {
        return senderName.getText();
    }

    public String getDeliveryTime() {
        return deliveryTime.getText();
    }

    public String getStatus() {
        return status.getText();
    }

    public String getCost() {
        return cost.getText();
    }

    public String getQuantity() {
        return quantity.getText();
    }

}
