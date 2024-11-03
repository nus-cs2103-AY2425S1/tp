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
import seedu.address.model.order.CustomerOrderList;
import seedu.address.model.order.SupplyOrderList;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private CustomerOrderListStorage customerOrderListStorage;
    private SupplyOrderListStorage supplyOrderListStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
                          CustomerOrderListStorage customerOrderListStorage, SupplyOrderListStorage supplyOrderListStorage) {
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.customerOrderListStorage = customerOrderListStorage;
        this.supplyOrderListStorage = supplyOrderListStorage;
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
