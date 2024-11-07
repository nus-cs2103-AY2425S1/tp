package seedu.sellsavvy.ui;

import static seedu.sellsavvy.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.sellsavvy.commons.core.LogsCenter;
import seedu.sellsavvy.model.customer.Customer;

/**
 * Panel containing the list of customers.
 */
public class CustomerListPanel extends UiPart<Region> {
    private static final String FXML = "CustomerListPanel.fxml";
    private static final String EMPTY_CUSTOMER_LIST_MESSAGE = "You do not have any recorded customers currently.";
    private static final String NO_RELATED_CUSTOMERS_FOUND = "No related customers found.";
    private final Logger logger = LogsCenter.getLogger(CustomerListPanel.class);
    private final ListChangeListener<Customer> orderChangeListener = change -> handleChangeInCustomers();
    private ObservableList<Customer> customerList;

    @FXML
    private ListView<Customer> customerListView;
    @FXML
    private Label customerListEmpty;

    /**
     * Creates a {@code CustomerListPanel} with the given {@code ObservableList}.
     */
    public CustomerListPanel(ObservableList<Customer> customerList) {
        super(FXML);
        this.customerList = customerList;
        customerList.addListener(orderChangeListener);;
        customerListView.setItems(customerList);
        customerListView.setCellFactory(listView -> new CustomerListViewCell());
        handleChangeInCustomers();
    }

    /**
     * Handles events whether a displayed customer list changes.
     */
    private void handleChangeInCustomers() {
        toggleNoCustomersLabel(customerListView.getItems().isEmpty());
    }

    /**
     * Toggles whether the {@code customerListEmpty} is displayed and determines the
     * message displayed.
     */
    private void toggleNoCustomersLabel(boolean customerListIsEmpty) {
        setCustomerListEmptyText();
        customerListEmpty.setManaged(customerListIsEmpty);
        customerListEmpty.setVisible(customerListIsEmpty);
        customerListView.setManaged(!customerListIsEmpty);
        customerListView.setVisible(!customerListIsEmpty);
    }

    /**
     * Sets the text displayed in {@code customerListEmpty}.
     */
    private void setCustomerListEmptyText() {
        FilteredList<Customer> filteredList = (FilteredList<Customer>) customerList;
        if (filteredList.getPredicate() == PREDICATE_SHOW_ALL_CUSTOMERS) {
            customerListEmpty.setText(EMPTY_CUSTOMER_LIST_MESSAGE);
        } else {
            customerListEmpty.setText(NO_RELATED_CUSTOMERS_FOUND);
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Customer} using a {@code CustomerCard}.
     */
    class CustomerListViewCell extends ListCell<Customer> {
        @Override
        protected void updateItem(Customer customer, boolean empty) {
            super.updateItem(customer, empty);

            if (empty || customer == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CustomerCard(customer, getIndex() + 1).getRoot());
            }
        }
    }

}
