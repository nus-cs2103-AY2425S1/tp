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
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.OrderList;
import seedu.sellsavvy.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final ReadOnlyObjectWrapper<Person> selectedPerson;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        selectedPerson = new ReadOnlyObjectWrapper<>(); // initially no order is displayed
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
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public Model createCopy() {
        Model modelCopy = new ModelManager(addressBook.createCopy(), userPrefs);
        Person selectedPersonCopy = modelCopy.findEquivalentPerson(getSelectedPerson2());
        modelCopy.updateSelectedPerson(selectedPersonCopy);
        return modelCopy;
    }

    @Override
    public Person findEquivalentPerson(Person person) {
        if (person == null) {
            return null;
        }
        return addressBook.findEquivalentPerson(person);
    }


    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Selected Person Accessors ==================================================================

    /**
     * Returns an unmodifiable view of selected {@code Person}
     */
    @Override
    public ReadOnlyObjectProperty<Person> getSelectedPerson() {
        return selectedPerson.getReadOnlyProperty();
    }

    @Override
    public void updateSelectedPerson(Person person) {
        selectedPerson.set(person);
    }

    @Override
    public boolean isSelectedPerson(Person person) {
        if (getSelectedPerson2() == null) {
            return person == null;
        }
        return getSelectedPerson2().equals(person);
    }

    @Override
    public Person getSelectedPerson2() {
        return selectedPerson.get();
    }

    @Override
    public FilteredList<Order> getFilteredOrderList() {
        if (getSelectedPerson2() == null) {
            return null;
        }
        return getSelectedPerson2().getFilteredOrderList();
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        assert getSelectedPerson2() != null; // we shouldn't be calling this method if there is no selectedPerson
        OrderList orderList = getSelectedPerson2().getOrderList();
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

        boolean isSameDisplayedPerson = getSelectedPerson2() == null
                ? otherModelManager.getSelectedPerson2() == null
                : getSelectedPerson2().equals(otherModelManager.getSelectedPerson2());

        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && isSameDisplayedPerson;
    }

}
