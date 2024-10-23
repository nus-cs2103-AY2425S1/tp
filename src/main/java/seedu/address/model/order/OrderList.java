package seedu.address.model.order;

import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class to manage a list of orders, including both supply and customer orders.
 */
public class OrderList {
    private ObservableList<SupplyOrder> supplyOrders = FXCollections.observableArrayList();;
    private ObservableList<SupplyOrder> internalUnmodifiableSupplyOrders =
            FXCollections.unmodifiableObservableList(supplyOrders);
    private ObservableList<CustomerOrder> customerOrders = FXCollections.observableArrayList();;
    private ObservableList<CustomerOrder> internalUnmodifiableCustomerOrders =
            FXCollections.unmodifiableObservableList(customerOrders);

    /**
     * Constructor for OrderList.
     * Initializes the lists of supply and customer orders.
     */
    public OrderList() {
    }

    /**
     * Adds a supply order to the list.
     *
     * @param supplyOrder The supply order to be added.
     */
    public void addSupplyOrder(SupplyOrder supplyOrder) {
        supplyOrders.add(supplyOrder);
    }

    /**
     * Adds a customer order to the list.
     *
     * @param customerOrder The customer order to be added.
     */
    public void addCustomerOrder(CustomerOrder customerOrder) {
        customerOrders.add(customerOrder);
    }

    /**
     * Retrieves all supply orders.
     *
     * @return A list of all supply orders.
     */
    public ObservableList<SupplyOrder> getSupplyOrders() {
        return internalUnmodifiableSupplyOrders;  // Return a copy of the list to avoid modification
    }

    /**
     * Retrieves all customer orders.
     *
     * @return A list of all customer orders.
     */
    public ObservableList<CustomerOrder> getCustomerOrders() {
        return internalUnmodifiableCustomerOrders;  // Return a copy of the list to avoid modification
    }

    /**
     * Finds a supply order by the customer's phone number.
     *
     * @param phoneNumber The phone number of the customer.
     * @return The supply order associated with the given phone number, or null if not found.
     */
    public SupplyOrder findSupplyOrderByPhoneNumber(String phoneNumber) {
        for (SupplyOrder order : supplyOrders) {
            if (order.getPhoneNumber().equals(phoneNumber)) {
                return order;
            }
        }
        return null;  // Return null if not found
    }

    /**
     * Finds a customer order by the customer's phone number.
     *
     * @param phoneNumber The phone number of the customer.
     * @return The customer order associated with the given phone number, or null if not found.
     */
    public CustomerOrder findCustomerOrderByPhoneNumber(String phoneNumber) {
        for (CustomerOrder order : customerOrders) {
            if (order.getPhoneNumber().equals(phoneNumber)) {
                return order;
            }
        }
        return null;  // Return null if not found
    }

    /**
     * Removes a supply order by the customer's phone number.
     *
     * @param phoneNumber The phone number of the customer.
     * @return True if the order was successfully removed, false otherwise.
     */
    public boolean removeSupplyOrder(String phoneNumber) {
        return supplyOrders.removeIf(order -> order.getPhoneNumber().equals(phoneNumber));
    }

    /**
     * Removes a customer order by the customer's phone number.
     *
     * @param phoneNumber The phone number of the customer.
     * @return True if the order was successfully removed, false otherwise.
     */
    public boolean removeCustomerOrder(String phoneNumber) {
        return customerOrders.removeIf(order -> order.getPhoneNumber().equals(phoneNumber));
    }

    @Override
    public String toString() {
        return "OrderList{" +
                "supplyOrders=" + supplyOrders +
                ", customerOrders=" + customerOrders +
                '}';
    }

    public String viewSupplyOrders() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < supplyOrders.size(); i++) {
            Order order = supplyOrders.get(i);
            sb.append(String.format("Order %d", i + 1));
            sb.append("\n");
            sb.append(order.viewOrder());
            sb.append("\n");
        }
        return sb.toString();
    }
    public String viewCustomerOrders() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < customerOrders.size(); i++) {
            Order order = customerOrders.get(i);
            sb.append(String.format("Order %d:", i + 1));
            sb.append("\n");
            sb.append(order.viewOrder());
            sb.append("\n");
        }
        return sb.toString();
    }
}
