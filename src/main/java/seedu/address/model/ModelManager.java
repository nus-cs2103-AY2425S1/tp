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
import seedu.address.model.product.*;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.model.product.Catalogue;

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
    private final Storage storage;  // Add Storage as a field

    // Constructor that accepts IngredientCatalogue, PastryCatalogue, and Storage
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        IngredientCatalogue ingredientCatalogue, PastryCatalogue pastryCatalogue,
                        Storage storage, CustomerOrderList customerOrderList, SupplyOrderList supplyOrderList) {
        requireAllNonNull(addressBook, userPrefs, ingredientCatalogue, pastryCatalogue, storage, customerOrderList, supplyOrderList);

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

    // Default constructor with properly initialized parameters
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

    public void associateOrdersWithPersons() {
        for (CustomerOrder customerOrder : customerOrderList.getOrders()) {
            addressBook.findPersonByPhone(customerOrder.getPerson().getPhone())
                    .ifPresent(person -> {
                        customerOrder.setOriginalPerson(person);
                        person.addOrder(customerOrder);
                    });
        }

        for (SupplyOrder supplyOrder : supplyOrderList.getOrders()) {
            addressBook.findPersonByPhone(supplyOrder.getPerson().getPhone())
                    .ifPresent(person -> {
                        supplyOrder.setOriginalPerson(person);
                        person.addOrder(supplyOrder);
                    });
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
