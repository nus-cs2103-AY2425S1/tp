package seedu.sellsavvy.ui;

import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.sellsavvy.commons.core.LogsCenter;
import seedu.sellsavvy.logic.commands.ordercommands.ListOrderCommand;
import seedu.sellsavvy.model.customer.Customer;
import seedu.sellsavvy.model.order.Order;

/**
 * Panel containing the list of orders of a selected customer.
 */
public class OrderListPanel extends UiPart<Region> {
    private static final String FXML = "OrderListPanel.fxml";
    private static final String DEFAULT_TITLE = "Orders";
    private static final String TITLE_WITH_SELECTED_CUSTOMER = "Orders (%1$s";
    private static final String EMPTY_ORDER_LIST_MESSAGE = "This customer does not have any orders currently.";
    private static final String NO_RELATED_ORDERS_FOUND = "No related orders found.";

    private final Logger logger = LogsCenter.getLogger(OrderListPanel.class);
    private final ListChangeListener<Order> orderChangeListener = change -> handleChangeInOrders();
    private Customer selectedCustomer;

    @FXML
    private ListView<Order> orderListView;
    @FXML
    private Label orderGuide;
    @FXML
    private Label orderListEmpty;
    @FXML
    private Label orderListTitle;
    @FXML
    private Label closingParenthesis;
    // closing bracket is separated from the rest of the label title to handle cases where name are too long

    /**
     * Creates a {@code OrderListPanel} with the given {@code ReadOnlyObjectProperty} of {@code Customer}.
     */
    public OrderListPanel(ReadOnlyObjectProperty<Customer> selectedCustomerProperty) {
        super(FXML);
        orderGuide.setText("Use the following command to view order(s):\n"
                + ListOrderCommand.MESSAGE_USAGE);
        orderListEmpty.setText(EMPTY_ORDER_LIST_MESSAGE);
        updateOrderList(selectedCustomerProperty.get());

        selectedCustomerProperty.addListener(((observable, oldCustomer, newCustomer) -> {
            updateOrderList(newCustomer);
        }));
    }

    /**
     * Updates the orderListView with the new selected customer's orders.
     * @param customer The newly selected customer whose orders will be displayed.
     */
    private void updateOrderList(Customer customer) {
        if (customer == null) {
            clearOrderList();
            return;
        }

        this.selectedCustomer = customer;
        orderListTitle.setText(String.format(TITLE_WITH_SELECTED_CUSTOMER, customer.getName().fullName));
        toggleClosingParenthesisDisplay(true);
        FilteredList<Order> orderList = customer.getFilteredOrderList();
        moveOrderChangeListenerTo(orderList);
        orderListView.setItems(orderList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());

        if (orderList.isEmpty()) {
            showNoOrdersLabel();
            return;
        }

        showOrderList();
    }

    /**
     * Toggles the display of the closing parenthesis element in the UI.
     *
     * @param shouldDisplay {@code true} to show the closing parenthesis; {@code false} to hide it.
     */
    private void toggleClosingParenthesisDisplay(boolean shouldDisplay) {
        closingParenthesis.setManaged(shouldDisplay);
        closingParenthesis.setVisible(shouldDisplay);
    }

    /**
     * Moves the {@code orderChangeListener} to the new {@code orderList}
     */
    private void moveOrderChangeListenerTo(FilteredList<Order> orderList) {
        orderListView.getItems().removeListener(orderChangeListener);
        orderList.addListener(orderChangeListener);
    }

    /**
     * Clears the order list and shows the guide on how to see customer's order.
     */
    private void clearOrderList() {
        orderListView.setItems(FXCollections.observableArrayList());
        clearComponentVisibility();
        orderGuide.setManaged(true);
        orderGuide.setVisible(true);

        orderListTitle.setText(DEFAULT_TITLE);
        toggleClosingParenthesisDisplay(false);
    }

    /**
     * Handles events whether a customer's orders changes.
     */
    private void handleChangeInOrders() {
        if (orderListView.getItems().isEmpty()) {
            showNoOrdersLabel();
        } else {
            showOrderList();
        }
    }

    /**
     * Displays the list of customer's orders.
     */
    private void showOrderList() {
        clearComponentVisibility();
        orderListView.setManaged(true);
        orderListView.setVisible(true);
    }

    /**
     * Displays the label to show that the order list is empty.
     */
    private void showNoOrdersLabel() {
        clearComponentVisibility();
        if (selectedCustomer.areOrdersFiltered()) {
            orderListEmpty.setText(NO_RELATED_ORDERS_FOUND);
        } else {
            orderListEmpty.setText(EMPTY_ORDER_LIST_MESSAGE);
        }

        orderListEmpty.setManaged(true);
        orderListEmpty.setVisible(true);
    }

    /**
     * Makes all components invisible.
     */
    private void clearComponentVisibility() {
        orderGuide.setManaged(false);
        orderGuide.setVisible(false);
        orderListView.setManaged(false);
        orderListView.setVisible(false);
        orderListEmpty.setManaged(false);
        orderListEmpty.setVisible(false);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Order} using a {@code OrderCard}.
     */
    class OrderListViewCell extends ListCell<Order> {
        @Override
        protected void updateItem(Order order, boolean empty) {
            super.updateItem(order, empty);

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OrderCard(order, getIndex() + 1).getRoot());
            }
        }
    }

}
