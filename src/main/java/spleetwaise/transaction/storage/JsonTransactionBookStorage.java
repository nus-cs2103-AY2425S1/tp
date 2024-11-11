package spleetwaise.transaction.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.storage.JsonAddressBookStorage;
import spleetwaise.commons.core.LogsCenter;
import spleetwaise.commons.exceptions.DataLoadingException;
import spleetwaise.commons.exceptions.IllegalValueException;
import spleetwaise.commons.util.FileUtil;
import spleetwaise.commons.util.JsonUtil;
import spleetwaise.transaction.model.ReadOnlyTransactionBook;


/**
 * A class to access TransactionBook data stored as a json file on the hard disk.
 */
public class JsonTransactionBookStorage implements TransactionBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;

    public JsonTransactionBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTransactionBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTransactionBook> readTransactionBook(AddressBookModel addressBookModel)
            throws DataLoadingException {
        return readTransactionBook(filePath, addressBookModel);
    }

    /**
     * Reads transaction book from file
     *
     * @param filePath         location of the data. Cannot be null.
     * @param addressBookModel reference to address book. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyTransactionBook> readTransactionBook(Path filePath, AddressBookModel addressBookModel)
            throws DataLoadingException {
        requireNonNull(filePath);
        requireNonNull(addressBookModel);

        Optional<JsonSerializableTransactionBook> jsonTransactionBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableTransactionBook.class);
        if (!jsonTransactionBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTransactionBook.get().toModelType(addressBookModel));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveTransactionBook(ReadOnlyTransactionBook txnBook) throws IOException {
        saveTransactionBook(txnBook, filePath);
    }

    /**
     * Similar to {@link #saveTransactionBook(ReadOnlyTransactionBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTransactionBook(ReadOnlyTransactionBook txnBook, Path filePath) throws IOException {
        requireNonNull(txnBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTransactionBook(txnBook), filePath);
    }

}
