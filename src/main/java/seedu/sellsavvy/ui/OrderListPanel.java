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
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.OrderList;
import seedu.sellsavvy.model.person.Person;

/**
 * Panel containing the list of orders of a person.
 */
public class OrderListPanel extends UiPart<Region> {
    private static final String FXML = "OrderListPanel.fxml";
    private static final String DEFAULT_TITLE = "Order";
    private static final String TITLE_WITH_SELECTED_PERSON = "Order (%1$s)";

    private final Logger logger = LogsCenter.getLogger(OrderListPanel.class);
    private final ListChangeListener<Order> orderChangeListener = change -> handleChangeInOrders();

    @FXML
    private ListView<Order> orderListView;
    @FXML
    private Label orderGuide;
    @FXML
    private Label orderListEmpty;
    @FXML
    private Label orderListTitle;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public OrderListPanel(ReadOnlyObjectProperty<Person> selectedPerson) {
        super(FXML);
        orderGuide.setText("Use one of the following commands below to view order:\n" + "1. "
                + ListOrderCommand.MESSAGE_USAGE);
        orderListEmpty.setText("This customer does not have any orders currently");
        updateOrderList(selectedPerson.get());

        selectedPerson.addListener(((observable, oldPerson, newPerson) -> {
            updateOrderList(newPerson);
        }));
    }

    /**
     * Updates the orderListView with the new selected person's orders.
     * @param person The newly selected person whose orders will be displayed.
     */
    private void updateOrderList(Person person) {
        if (person == null) {
            clearOrderList();
            return;
        }

        orderListTitle.setText(String.format(TITLE_WITH_SELECTED_PERSON, person.getName().fullName));
        FilteredList<Order> orderList = person.getFilteredOrderList();
        moveOrderChangeListener(orderList);
        if (orderList.isEmpty()) {
            showEmptyOrderLabel();
            return;
        }

        orderListView.setItems(orderList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());
        showOrderList();
    }

    /**
     * Moves the {@code orderChangeListener} to the new {@code orderList}
     */
    private void moveOrderChangeListener(FilteredList<Order> orderList) {
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
    }

    /**
     * Handles events whether a customer's orders changes.
     */
    private void handleChangeInOrders() {
        System.out.println("called");
        if (orderListView.getItems().isEmpty()) {
            showEmptyOrderLabel();
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
    private void showEmptyOrderLabel() {
        clearComponentVisibility();
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
