package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.goodsreceipt.GoodsReceipt;

/**
 * A UI component that displays information of a {@code GoodsReceipt}
 */
public class GoodsCard extends UiPart<Region> {

    private static final String FXML = "GoodsListCard.fxml";

    public final GoodsReceipt goodsReceipt;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label goodsName;
    @FXML
    private FlowPane goodsCategoryFlowPane;
    @FXML
    private Label goodsCategory;
    @FXML
    private Label supplierName;
    @FXML
    private Label procurementDate;
    @FXML
    private Label arrivalDate;
    @FXML
    private Label isDelivered;
    @FXML
    private Label isDeliveredContent;
    @FXML
    private Label quantity;
    @FXML
    private Label price;


    /**
     * Creates a {@code GoodsCard} with the given {@code GoodsReceipt} and index to display.
     */
    public GoodsCard(GoodsReceipt goodsReceipt, int displayedIndex) {
        super(FXML);
        this.goodsReceipt = goodsReceipt;
        String deliveryStatus = goodsReceipt.isDelivered() ? "Delivered" : "Pending";
        id.setText(displayedIndex + ". ");
        goodsName.setText(goodsReceipt.getGoods().toString());
        goodsCategory = new Label();
        goodsCategory.setText(goodsReceipt.getGoods().getCategory().toString());
        goodsCategoryFlowPane.getChildren().add(goodsCategory);
        supplierName.setText("From: " + goodsReceipt.getSupplierName().toString());
        procurementDate.setText("Ordered on " + goodsReceipt.getProcurementDate().getReadableDateTimeString());
        arrivalDate.setText("Arriving on " + goodsReceipt.getArrivalDate().getReadableDateTimeString());
        isDelivered.setText("Delivery Status: ");
        isDeliveredContent.setText(deliveryStatus);
        setStyle(isDeliveredContent, goodsReceipt.isDelivered()
                ? "cell_small_label_success" : "cell_small_label_warning");
        quantity.setText("Quantity: " + String.valueOf(goodsReceipt.getQuantity()));
        price.setText("Total Cost: " + String.valueOf(goodsReceipt.getPriceTotal()));
    }

    /**
     * Sets the style of the node
     * @param node The label to be styled.
     * @param style the style to be applied to the label.
     */
    private void setStyle(Node node, String style) {
        node.getStyleClass().clear();
        node.getStyleClass().add(style);
    }

}
