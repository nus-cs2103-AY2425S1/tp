package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.order.CustomerOrder;
import seedu.address.model.order.CustomerOrderList;
import seedu.address.model.order.SupplyOrder;
import seedu.address.model.order.SupplyOrderList;
import seedu.address.model.person.Person;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.Inventory;
import seedu.address.model.product.Pastry;
import seedu.address.model.product.PastryCatalogue;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs address book file path.
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

    //=========== Pastry and Ingredient Management ========================================================

    /**
     * Adds a pastry to the pastry catalogue.
     *
     * @param pastry The pastry to add.
     */
    void addPastry(Pastry pastry);

    /**
     * Returns the pastry catalogue.
     *
     * @return The current pastry catalogue.
     */
    PastryCatalogue getPastryCatalogue();

    /**
     * Adds an ingredient to the ingredient catalogue.
     *
     * @param ingredient The ingredient to add.
     */
    void addIngredient(Ingredient ingredient);

    /**
     * Returns the ingredient catalogue.
     *
     * @return The current ingredient catalogue.
     */
    IngredientCatalogue getIngredientCatalogue();

    //=========== Order Management ========================================================================

    /**
     * Adds a customer order.
     *
     * @param customerOrder The customer order to add.
     */
    void addCustomerOrder(CustomerOrder customerOrder);

    /**
     * Adds a supply order.
     *
     * @param supplyOrder The supply order to add.
     */
    void addSupplyOrder(SupplyOrder supplyOrder);

    /**
     * Returns the list of customer orders.
     *
     * @return The current customer order list.
     */
    CustomerOrderList getCustomerOrderList();

    /**
     * Returns the list of supply orders.
     *
     * @return The current supply order list.
     */
    SupplyOrderList getSupplyOrderList();

    /**
     * Returns the inventory.
     *
     * @return The current inventory.
     */
    Inventory getInventory();
}