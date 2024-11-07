package seedu.sellsavvy.model;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.sellsavvy.commons.core.GuiSettings;
import seedu.sellsavvy.commons.core.LogsCenter;
import seedu.sellsavvy.model.customer.Customer;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.OrderList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Customer> filteredCustomers;
    private final ReadOnlyObjectWrapper<Customer> selectedPersonProperty;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCustomers = new FilteredList<>(this.addressBook.getPersonList());
        selectedPersonProperty = new ReadOnlyObjectWrapper<>(); // initially no order is displayed
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Customer customer) {
        requireNonNull(customer);
        return addressBook.hasPerson(customer);
    }

    @Override
    public boolean hasSimilarPerson(Customer customer) {
        requireNonNull(customer);
        return addressBook.hasSimilarPerson(customer);
    }

    @Override
    public void deletePerson(Customer target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Customer customer) {
        addressBook.addPerson(customer);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Customer target, Customer editedCustomer) {
        requireAllNonNull(target, editedCustomer);

        addressBook.setPerson(target, editedCustomer);
    }

    @Override
    public Model createCopy() {
        Model modelCopy = new ModelManager(addressBook.createCopy(), userPrefs);
        Customer selectedCustomerCopy = modelCopy.findEquivalentPerson(getSelectedPerson());
        modelCopy.updateSelectedPerson(selectedCustomerCopy);
        return modelCopy;
    }

    @Override
    public Customer findEquivalentPerson(Customer customer) {
        if (customer == null) {
            return null;
        }
        return addressBook.findEquivalentPerson(customer);
    }


    //=========== Filtered Customer List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Customer} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Customer> getFilteredPersonList() {
        return filteredCustomers;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Customer> predicate) {
        requireNonNull(predicate);
        filteredCustomers.setPredicate(predicate);
    }

    //=========== Selected Customer Accessors ==================================================================

    @Override
    public ReadOnlyObjectProperty<Customer> getSelectedPersonProperty() {
        return selectedPersonProperty.getReadOnlyProperty();
    }

    @Override
    public void updateSelectedPerson(Customer customer) {
        selectedPersonProperty.set(customer);
    }

    @Override
    public boolean isSelectedPerson(Customer customer) {
        if (getSelectedPerson() == null) {
            return customer == null;
        }
        return getSelectedPerson().equals(customer);
    }

    @Override
    public Customer getSelectedPerson() {
        return selectedPersonProperty.get();
    }

    @Override
    public OrderList getSelectedOrderList() {
        if (getSelectedPerson() == null) {
            return null;
        }
        return getSelectedPerson().getOrderList();
    }

    @Override
    public FilteredList<Order> getFilteredOrderList() {
        if (getSelectedPerson() == null) {
            return null;
        }
        return getSelectedPerson().getFilteredOrderList();
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        assert getSelectedPerson() != null; // we shouldn't be calling this method if there is no selectedPerson
        OrderList orderList = getSelectedPerson().getOrderList();
        orderList.setOrder(target, editedOrder);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;

        boolean isSameDisplayedPerson = getSelectedPerson() == null
                ? otherModelManager.getSelectedPerson() == null
                : getSelectedPerson().equals(otherModelManager.getSelectedPerson());

        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredCustomers.equals(otherModelManager.filteredCustomers)
                && isSameDisplayedPerson;
    }

}
