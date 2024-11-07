package seedu.sellsavvy.model.customer;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.sellsavvy.model.customer.exceptions.CustomerNotFoundException;
import seedu.sellsavvy.model.customer.exceptions.DuplicateCustomerException;
import seedu.sellsavvy.model.order.Order;

/**
 * A list of customers that enforces uniqueness between its elements and does not allow nulls.
 * A customer is considered unique by comparing using {@code Customer#isSameCustomer(Customer)}. As such, adding and
 * updating of customers uses Customer#isSameCustomer(Customer) for equality so as to ensure that the customer being
 * added or updated is unique in terms of identity in the UniqueCustomerList. However, the removal of a customer uses
 * Customer#equals(Object) to ensure that the customer with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Customer#isSameCustomer(Customer)
 */
public class UniqueCustomerList implements Iterable<Customer> {

    private final ObservableList<Customer> internalList = FXCollections.observableArrayList();
    private final ObservableList<Customer> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent customer as the given argument.
     */
    public boolean contains(Customer toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCustomer);
    }

    /**
     * Returns true if there is a different customer
     * with the similar name as the given {@code Customer} exists in the {@code UniqueCustomerList}.
     */
    public boolean hasSimilarCustomer(Customer toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(customer -> toCheck.isSimilarTo(customer) && customer != toCheck);
    }

    /**
     * Adds a customer to the list.
     * The customer must not already exist in the list.
     */
    public void add(Customer toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCustomerException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the customer {@code target} in the list with {@code editedCustomer}.
     * {@code target} must exist in the list.
     * The customer identity of {@code editedCustomer} must not be the same as another existing customer in the list.
     */
    public void setCustomer(Customer target, Customer editedCustomer) {
        requireAllNonNull(target, editedCustomer);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CustomerNotFoundException();
        }

        if (!target.isSameCustomer(editedCustomer) && contains(editedCustomer)) {
            throw new DuplicateCustomerException();
        }

        Predicate<? super Order> predicate = target.getOrderPredicate();
        internalList.set(index, editedCustomer);
        editedCustomer.updateFilteredOrderList(predicate);
    }

    /**
     * Removes the equivalent customer from the list.
     * The customer must exist in the list.
     */
    public void remove(Customer toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CustomerNotFoundException();
        }
    }

    public void setCustomers(UniqueCustomerList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code customers}.
     * {@code customers} must not contain duplicate customers.
     */
    public void setCustomers(List<Customer> customers) {
        requireAllNonNull(customers);
        if (!customersAreUnique(customers)) {
            throw new DuplicateCustomerException();
        }

        internalList.setAll(customers);
    }

    /**
     * Creates a {@code UniqueCustomerList} copy of all data in this list.
     */
    public UniqueCustomerList copyCustomers() {
        UniqueCustomerList copy = new UniqueCustomerList();
        for (Customer customer : internalList) {
            copy.add(customer.createCopy());
        }
        return copy;
    }

    /**
     * Returns a {@code Customer} in the {@code UniqueCustomerList} equivalent to target Customer given.
     */
    public Customer findEquivalentCustomer(Customer target) {
        requireNonNull(target);

        if (!internalList.contains(target)) {
            throw new CustomerNotFoundException();
        }

        int index = internalList.indexOf(target);
        return internalList.get(index);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Customer> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Customer> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueCustomerList)) {
            return false;
        }

        UniqueCustomerList otherUniqueCustomerList = (UniqueCustomerList) other;
        return internalList.equals(otherUniqueCustomerList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code customers} contains only unique customers.
     */
    private boolean customersAreUnique(List<Customer> customers) {
        for (int i = 0; i < customers.size() - 1; i++) {
            for (int j = i + 1; j < customers.size(); j++) {
                if (customers.get(i).isSameCustomer(customers.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
