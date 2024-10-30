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
import seedu.address.commons.util.CsvUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class MultiFormatAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(MultiFormatAddressBookStorage.class);

    private Path saveFilePath;
    private Path exportFilePath;

    public MultiFormatAddressBookStorage(Path saveFilePath, Path exportFilePath) {
        this.saveFilePath = saveFilePath;
        this.exportFilePath = exportFilePath;
    }

    public Path getAddressBookSaveFilePath() {
        return saveFilePath;
    }

    public Path getAddressBookExportFilePath() {
        return exportFilePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(saveFilePath);
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
        saveAddressBook(addressBook, saveFilePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createSaveFileIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
    }

    @Override
    public void exportAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        exportAddressBook(addressBook, exportFilePath);
    }

    @Override
    public void exportAddressBook(ReadOnlyAddressBook addressBook, Path exportFilePath) throws IOException {
        FileUtil.createExportFileIfMissing(exportFilePath);
        CsvUtil.exportCsvFile(addressBook, exportFilePath);
    }

}
