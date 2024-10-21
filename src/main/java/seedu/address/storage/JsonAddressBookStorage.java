package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonAddressBookStorage implements AddressBookStorage {

    private static final Path DEFAULT_ARCHIVEPATH = Paths.get("archived", "archiveaddressbook.json");
    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;
    private Path archivePath;

    /**
     * Create a  JsonAddressBookStorage with file Path and default archive path
     * @param filePath the file path.
     * */
    public JsonAddressBookStorage(Path filePath) {
        this.filePath = filePath;
        this.archivePath = DEFAULT_ARCHIVEPATH;
    }

    /**
     * Create a JsonAddressBookStorage with file path and archive path
     * @param filePath the file path.
     * @param archivePath  the archive path.
     * */
    public JsonAddressBookStorage(Path filePath, Path archivePath) {
        this.filePath = filePath;
        this.archivePath = archivePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    public Path getArchivedAddressBookFilePath() {
        return archivePath;
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
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
    }

    @Override
    public void saveArchivedAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, archivePath);
    }

    /**
     * Similar to {@link #saveArchivedAddressBook(ReadOnlyAddressBook)}.
     *
     * @param archivePath location of the data. Cannot be null.
     */
    public void saveArchivedAddressBook(ReadOnlyAddressBook addressBook, Path archivePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(archivePath);

        FileUtil.createIfMissing(archivePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), archivePath);
    }

    @Override
    public void setArchivePath(Path path) {
        this.archivePath = path;
    }

}
