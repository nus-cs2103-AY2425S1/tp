package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyReceiptLog;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final AddressBookStorage addressBookStorage;
    private final UserPrefsStorage userPrefsStorage;
    private final GoodsStorage goodsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage,
                          UserPrefsStorage userPrefsStorage,
                          GoodsStorage goodsStorage) {
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.goodsStorage = goodsStorage;
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

    // ================ Goods methods ==============================
    @Override
    public Path getGoodsFilePath() {
        return goodsStorage.getGoodsFilePath();
    }

    @Override
    public Path getExportGoodsFilePath() {
        return goodsStorage.getExportGoodsFilePath();
    }

    @Override
    public Optional<ReadOnlyReceiptLog> readGoods() throws DataLoadingException {
        return readGoods(goodsStorage.getGoodsFilePath());
    }

    @Override
    public Optional<ReadOnlyReceiptLog> readGoods(Path filePath) throws DataLoadingException {
        return goodsStorage.readGoods(filePath);
    }

    @Override
    public void saveGoods(ReadOnlyReceiptLog goods) throws IOException {
        saveGoods(goods, goodsStorage.getGoodsFilePath());
    }
    @Override
    public void saveGoods(ReadOnlyReceiptLog goods, Path filePath) throws IOException {
        goodsStorage.saveGoods(goods, filePath);
    }
    @Override
    public void saveFilteredGoods(ReadOnlyReceiptLog goods) throws IOException {
        saveGoods(goods, goodsStorage.getExportGoodsFilePath());
    }


}
