package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.order.SupplyOrder;

/**
 * Panel containing the list of persons.
 */
public class SupplyOrderListPanel extends UiPart<Region> {
    private static final String FXML = "SupplyOrderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SupplyOrderListPanel.class);

    @FXML
    private ListView<SupplyOrder> orderListView;

    /**
     * Creates a {@code SupplyOrderListPanel} with the given {@code ObservableList}.
     */
    public SupplyOrderListPanel(ObservableList<SupplyOrder> orderList) {
        super(FXML);
        orderListView.setItems(orderList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code SupplyOrder} using a {@code SupplyOrderCard}.
     */
    class OrderListViewCell extends ListCell<SupplyOrder> {
        @Override
        protected void updateItem(SupplyOrder order, boolean empty) {
            super.updateItem(order, empty);

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SupplyOrderCard(order, getIndex() + 1).getRoot());
            }
        }
    }

}
