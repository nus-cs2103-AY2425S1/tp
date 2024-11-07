package seedu.sellsavvy.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.sellsavvy.commons.core.GuiSettings;
import seedu.sellsavvy.model.customer.Customer;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.OrderList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    static Predicate<Customer> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the address book.
     */
    boolean hasPerson(Customer customer);

    /**
     * Returns true if there is a different customer
     * with the similar name as given {@code Customer} exists in the address book.
     */
    boolean hasSimilarPerson(Customer customer);

    /**
     * Deletes the given customer.
     * The customer must exist in the address book.
     */
    void deletePerson(Customer target);

    /**
     * Adds the given customer.
     * {@code customer} must not already exist in the address book.
     */
    void addPerson(Customer customer);

    /**
     * Replaces the given customer {@code target} with {@code editedCustomer}.
     * {@code target} must exist in the address book.
     * The customer identity of {@code editedCustomer} must not be the same as another existing customer in the address book.
     */
    void setPerson(Customer target, Customer editedCustomer);

    /** Returns an unmodifiable view of the filtered customer list */
    ObservableList<Customer> getFilteredPersonList();

    /**
     * Creates a copy of the entire {@code Model}.
     */
    Model createCopy();

    /**
     * Updates the filter of the filtered customer list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Customer> predicate);

    /** Returns a {@code ReadOnlyObjectProperty} of selected {@code Customer} */
    ReadOnlyObjectProperty<Customer> getSelectedPersonProperty();

    /** Returns the selected customer displayed*/
    Customer getSelectedPerson();

    /** Returns the selected OrderList displayed*/
    OrderList getSelectedOrderList();

    /** Returns the {@code OrderList} displayed*/
    FilteredList<Order> getFilteredOrderList();

    /**
     * Updates the selected customer whose orders are displayed.
     */
    void updateSelectedPerson(Customer customer);

    /**
     * Checks if the given {@code customer} is the selected customer whose orders are displayed.
     */
    boolean isSelectedPerson(Customer customer);

    /**
     * Replaces the given order {@code target} with {@code editedOrder}.
     * {@code target} must exist in the displayed order list.
     */
    void setOrder(Order target, Order editedOrder);

    /**
     * Returns a {@code Customer} in the {@code UniqueCustomerList} equivalent to target Customer given.
     * Returns null if target is null.
     */
    Customer findEquivalentPerson(Customer customer);
}
