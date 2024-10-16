package seedu.address.storage;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAddressBook;
public class JsonStudentAddressBookStorage implements AddressBookStorage{
    private static final Logger logger = LogsCenter.getLogger(JsonStudentAddressBookStorage.class);
    private Path filePath;
    public JsonStudentAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }
    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(filePath);
    }

    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException{
        requireNonNull(filePath);
        Optional<JsonSerializableStudentAddressBook> jsonAddressBook =
                JsonUtil.readJsonFile(filePath, JsonSerializableStudentAddressBook.class);
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

    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableStudentAddressBook(addressBook), filePath);
    }

}
