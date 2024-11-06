package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.PastryCatalogue;
import seedu.address.model.order.CustomerOrderList;
import seedu.address.model.order.SupplyOrderList;

/**
 * Manages storage of AddressBook, IngredientCatalogue, and PastryCatalogue data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);

    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private IngredientCatalogueStorage ingredientCatalogueStorage;
    private PastryCatalogueStorage pastryCatalogueStorage;
    private CustomerOrderListStorage customerOrderListStorage;
    private SupplyOrderListStorage supplyOrderListStorage;

    /**
     * Creates a {@code StorageManager} with the given storages.
     */
    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
                          IngredientCatalogueStorage ingredientCatalogueStorage,
                          PastryCatalogueStorage pastryCatalogueStorage, CustomerOrderListStorage customerOrderListStorage, SupplyOrderListStorage supplyOrderListStorage) {
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.ingredientCatalogueStorage = ingredientCatalogueStorage;
        this.pastryCatalogueStorage = pastryCatalogueStorage;
        this.customerOrderListStorage = customerOrderListStorage;
        this.supplyOrderListStorage = supplyOrderListStorage;
    }

    // Default constructor with properly initialized parameters
    public StorageManager() {
        this(new JsonAddressBookStorage(Path.of("data", "addressbook.json")),
                new JsonUserPrefsStorage(Path.of("data", "userprefs.json")),
                new JsonIngredientCatalogueStorage(Path.of("data", "ingredientcatalogue.json")),
                new JsonPastryCatalogueStorage(Path.of("data", "pastrycatalogue.json")),
                new JsonCustomerOrderListStorage(Path.of("data", "customerorders.json")),
                new JsonSupplyOrderListStorage(Path.of("data", "supplyorders.json")));
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ IngredientCatalogue methods ==============================

    @Override
    public Path getIngredientCatalogueFilePath() {
        return ingredientCatalogueStorage.getIngredientCatalogueFilePath();
    }

    @Override
    public Optional<IngredientCatalogue> readIngredientCatalogue() throws DataLoadingException {
        return ingredientCatalogueStorage.readIngredientCatalogue();
    }

    @Override
    public Optional<IngredientCatalogue> readIngredientCatalogue(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read ingredient catalogue data from file: " + filePath);
        return ingredientCatalogueStorage.readIngredientCatalogue(filePath);
    }

    @Override
    public void saveIngredientCatalogue(IngredientCatalogue ingredientCatalogue) throws IOException {
        saveIngredientCatalogue(ingredientCatalogue, ingredientCatalogueStorage.getIngredientCatalogueFilePath());
    }

    @Override
    public void saveIngredientCatalogue(IngredientCatalogue ingredientCatalogue, Path filePath) throws IOException {
        logger.fine("Attempting to write ingredient catalogue data to file: " + filePath);
        ingredientCatalogueStorage.saveIngredientCatalogue(ingredientCatalogue, filePath);
    }

    // ================ PastryCatalogue methods ==============================

    @Override
    public Path getPastryCatalogueFilePath() {
        return pastryCatalogueStorage.getPastryCatalogueFilePath();
    }

    @Override
    public Optional<PastryCatalogue> readPastryCatalogue() throws DataLoadingException {
        return pastryCatalogueStorage.readPastryCatalogue();
    }

    @Override
    public Optional<PastryCatalogue> readPastryCatalogue(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read pastry catalogue data from file: " + filePath);
        return pastryCatalogueStorage.readPastryCatalogue(filePath);
    }

    @Override
    public void savePastryCatalogue(PastryCatalogue pastryCatalogue) throws IOException {
        savePastryCatalogue(pastryCatalogue, pastryCatalogueStorage.getPastryCatalogueFilePath());
    }

    @Override
    public void savePastryCatalogue(PastryCatalogue pastryCatalogue, Path filePath) throws IOException {
        logger.fine("Attempting to write pastry catalogue data to file: " + filePath);
        pastryCatalogueStorage.savePastryCatalogue(pastryCatalogue, filePath);
    }
  
    // ================ CustomerOrderList methods ==============================

    @Override
    public Path getCustomerOrderListFilePath() {
        return customerOrderListStorage.getCustomerOrderListFilePath();
    }

    @Override
    public Optional<CustomerOrderList> readCustomerOrderList() throws DataLoadingException {
        return readCustomerOrderList(customerOrderListStorage.getCustomerOrderListFilePath());
    }

    @Override
    public Optional<CustomerOrderList> readCustomerOrderList(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return customerOrderListStorage.readCustomerOrderList(filePath);
    }

    @Override
    public void saveCustomerOrderList(CustomerOrderList customerOrderList) throws IOException {
        saveCustomerOrderList(customerOrderList, customerOrderListStorage.getCustomerOrderListFilePath());
    }

    @Override
    public void saveCustomerOrderList(CustomerOrderList customerOrderList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        customerOrderListStorage.saveCustomerOrderList(customerOrderList, filePath);
    }

    // ================ SupplyOrderList methods ==============================

    @Override
    public Path getSupplyOrderListFilePath() {
        return supplyOrderListStorage.getSupplyOrderListFilePath();
    }

    @Override
    public Optional<SupplyOrderList> readSupplyOrderList() throws DataLoadingException {
        return readSupplyOrderList(supplyOrderListStorage.getSupplyOrderListFilePath());
    }

    @Override
    public Optional<SupplyOrderList> readSupplyOrderList(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return supplyOrderListStorage.readSupplyOrderList(filePath);
    }

    @Override
    public void saveSupplyOrderList(SupplyOrderList supplyOrderList) throws IOException {
        saveSupplyOrderList(supplyOrderList, supplyOrderListStorage.getSupplyOrderListFilePath());
    }

    @Override
    public void saveSupplyOrderList(SupplyOrderList supplyOrderList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        supplyOrderListStorage.saveSupplyOrderList(supplyOrderList, filePath);
    }
}
