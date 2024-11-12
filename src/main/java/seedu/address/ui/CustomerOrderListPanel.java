package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.order.CustomerOrder;

/**
 * Panel containing the list of persons.
 */
public class CustomerOrderListPanel extends UiPart<Region> {
    private static final String FXML = "CustomerOrderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CustomerOrderListPanel.class);

    @FXML
    private ListView<CustomerOrder> orderListView;

    /**
     * Creates a {@code CustomerOrderListPanel} with the given {@code ObservableList}.
     */
    public CustomerOrderListPanel(ObservableList<CustomerOrder> orderList) {
        super(FXML);
        orderListView.setItems(orderList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CustomerOrder} using a {@code CustomerOrderCard}.
     */
    class OrderListViewCell extends ListCell<CustomerOrder> {
        @Override
        protected void updateItem(CustomerOrder order, boolean empty) {
            super.updateItem(order, empty);

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CustomerOrderCard(order, getIndex() + 1).getRoot());
            }
        }
    }

}
