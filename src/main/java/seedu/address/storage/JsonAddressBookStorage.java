package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.security.EncryptionManager;


/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;

    public JsonAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        try {
            byte[] encryptedData = Files.readAllBytes(filePath);
            String jsonData = EncryptionManager.decrypt(encryptedData, null);
            JsonSerializableAddressBook jsonAddressBook = JsonUtil.fromJsonString(
                    jsonData, JsonSerializableAddressBook.class);

            try {
                return Optional.of(jsonAddressBook.toModelType());
            } catch (IllegalValueException ive) {
                logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
                throw new DataLoadingException(ive);
            }

        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            // Data not encrypted yet. Use normal json file.
            Optional<JsonSerializableAddressBook> jsonAddressBook = JsonUtil.readJsonFile(
                    filePath, JsonSerializableAddressBook.class);
            if (!jsonAddressBook.isPresent()) {
                return Optional.empty();
            }

            try {
                return Optional.of(jsonAddressBook.get().toModelType());
            } catch (IllegalValueException ive) {
                logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
                throw new DataLoadingException(ive);
            }

        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        // Convert address book to JSON
        String jsonData = JsonUtil.toJsonString(new JsonSerializableAddressBook(addressBook));

        // Encrypt the JSON data
        byte[] encryptedData = new byte[0];
        try {
            encryptedData = EncryptionManager.encrypt(jsonData, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Files.write(filePath, encryptedData, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

}
