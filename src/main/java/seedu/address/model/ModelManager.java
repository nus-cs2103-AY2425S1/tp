package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
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
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final PastryCatalogue pastryCatalogue;
    private final IngredientCatalogue ingredientCatalogue;
    private final SupplyOrderList supplyOrderList;
    private final CustomerOrderList customerOrderList;
    private final ObservableList<SupplyOrder> supplyOrderObservableList;
    private final ObservableList<CustomerOrder> customerOrderObservableList;
    private final Inventory inventory;
    private final Storage storage; // Add Storage as a field

    /**
     * Constructs a {@code ModelManager} using the provided address book, user preferences, ingredient catalogue,
     * pastry catalogue, storage, customer order list, and supply order list.
     * Initializes the internal structures and associates orders with their respective persons in the address book.
     *
     * @param addressBook The read-only address book containing person data.
     * @param userPrefs The read-only user preferences.
     * @param ingredientCatalogue The catalogue containing ingredients.
     * @param pastryCatalogue The catalogue containing pastries.
     * @param storage The storage manager to handle saving and loading data.
     * @param customerOrderList The list of customer orders.
     * @param supplyOrderList The list of supply orders.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        IngredientCatalogue ingredientCatalogue, PastryCatalogue pastryCatalogue,
                        Storage storage, CustomerOrderList customerOrderList, SupplyOrderList supplyOrderList) {
        requireAllNonNull(addressBook, userPrefs, ingredientCatalogue, pastryCatalogue,
                storage, customerOrderList, supplyOrderList);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.supplyOrderList = supplyOrderList;
        this.customerOrderList = customerOrderList;
        this.supplyOrderObservableList = supplyOrderList.getOrders();
        this.customerOrderObservableList = customerOrderList.getOrders();

        // Initialize catalogues and storage
        this.ingredientCatalogue = ingredientCatalogue;
        this.pastryCatalogue = pastryCatalogue;
        this.storage = storage;

        // Initialize inventory with the ingredient catalogue
        this.inventory = new Inventory(ingredientCatalogue);

        // Associate orders with persons after all objects are initialized
        associateOrdersWithPersons();
    }

    /**
     * Constructs a {@code ModelManager} with default parameters initialized to empty or default values.
     * Initializes an empty address book, user preferences, and default ingredient and pastry catalogues.
     * Also initializes the storage manager and creates empty customer and supply order lists.
     */
    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), IngredientCatalogue.getInstance(),
                new PastryCatalogue(), new StorageManager(), new CustomerOrderList(), new SupplyOrderList());
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

    /**
     * Associates customer and supply orders with their respective persons in the address book based on phone numbers.
     * This method iterates through the customer and supply orders and links each order with the person in the
     * address book that matches the phone number provided in the order. If a matching person is found,
     * the order is associated with that person, and the order is added to the person's list of orders.
     */
    public void associateOrdersWithPersons() {
        for (CustomerOrder customerOrder : customerOrderList.getOrders()) {
            addressBook.findPersonByPhone(customerOrder.getPerson().getPhone())
                    .ifPresent(person -> person.addOrder(customerOrder));
        }

        for (SupplyOrder supplyOrder : supplyOrderList.getOrders()) {
            addressBook.findPersonByPhone(supplyOrder.getPerson().getPhone())
                    .ifPresent(person -> person.addOrder(supplyOrder));
        }
    }

    //=========== Filtered Person List Accessors =============================================================

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ModelManager)) {
            return false;
        }
        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    //=========== Pastry and Ingredient Methods =============================================================

    @Override
    public void addPastry(Pastry pastry) {
        pastryCatalogue.addPastry(pastry.getName(), pastry.getCost(), pastry.getIngredients());
        if (storage != null) {
            try {
                storage.savePastryCatalogue(pastryCatalogue);
                logger.info("Pastry catalogue saved successfully after adding pastry: " + pastry.getName());
            } catch (IOException e) {
                logger.warning("Failed to save pastry catalogue: " + e.getMessage());
            }
        }
    }

    @Override
    public PastryCatalogue getPastryCatalogue() {
        return pastryCatalogue;
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        ingredientCatalogue.addIngredient(ingredient);
        if (storage != null) {
            try {
                storage.saveIngredientCatalogue(ingredientCatalogue);
                logger.info("Ingredient catalogue saved successfully after adding ingredient: " + ingredient.getName());
            } catch (IOException e) {
                logger.warning("Failed to save ingredient catalogue: " + e.getMessage());
            }
        }
    }

    @Override
    public IngredientCatalogue getIngredientCatalogue() {
        return ingredientCatalogue;
    }

    //=========== Order Management Methods ==================================================================

    @Override
    public void addCustomerOrder(CustomerOrder customerOrder) {
        customerOrderList.addOrder(customerOrder);
    }

    @Override
    public void addSupplyOrder(SupplyOrder supplyOrder) {
        supplyOrderList.addOrder(supplyOrder);
    }

    @Override
    public CustomerOrderList getCustomerOrderList() {
        return customerOrderList;
    }

    @Override
    public SupplyOrderList getSupplyOrderList() {
        return supplyOrderList;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
