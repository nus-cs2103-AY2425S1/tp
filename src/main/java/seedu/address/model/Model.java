package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Person;
import seedu.address.model.supplier.Supplier;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Delivery> PREDICATE_SHOW_ALL_DELIVERIES = unused -> true;
    Predicate<Supplier> PREDICATE_SHOW_ALL_SUPPLIERS = unused -> true;

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
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if a delivery with the same identity as {@code delivery} exists in the address book.
     */
    boolean hasDelivery(Delivery delivery);

    /**
     * Adds the given delivery.
     * {@code delivery} must not already exist in the address book.
     */
    void addDelivery(Delivery delivery);

    /**
     * Deletes the given delivery.
     * The delivery must exist in the address book.
     */
    void deleteDelivery(Delivery delivery);

    /**
     * Replaces the given delivery {@code target} with {@code editedDelivery}.
     * {@code target} must exist in the address book.
     * The delivery identity of {@code editedDelivery} must not be the same as
     * another existing delivery in the address book.
     */
    void setDelivery(Delivery target, Delivery updatedDelivery);

    /**
     * Returns an unmodifiable view of the filtered delivery list
     */
    ObservableList<Delivery> getFilteredDeliveryList();

    /**
     * Updates the filter of the filtered delivery list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDeliveryList(Predicate<Delivery> predicate);

    /**
     * Returns an unmodifiable view of the filtered supplier list
     */
    ObservableList<Supplier> getFilteredSupplierList();

    /**
     * Deletes the given supplier.
     * The supplier must exist in the address book.
     * @param target
     */
    void deleteSupplier(Supplier target);

    /**
     * Replace the given supplier {@code target} with {@code updatedSupplier}.
     * {@code target} must exist in the address book.
     * The supplier identity of {@code updatedSupplier} must not be the same as
     * another existing supplier in the address book.
     */
    void setSupplier(Supplier target, Supplier updatedSupplier);

    /**
     * Updates the filter of the filtered supplier list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSupplierList(Predicate<Supplier> predicate);
}
