package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyReminderAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * A class to access ReminderAddressBook data stored as a json file on the hard disk.
 */
public class JsonReminderAddressBookStorage implements ReminderAddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonReminderAddressBookStorage.class);

    private Path filePath;

    public JsonReminderAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getReminderAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyReminderAddressBook> readReminderAddressBook() throws DataLoadingException {
        return readReminderAddressBook(filePath);
    }

    /**
     * Similar to {@link #readReminderAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyReminderAddressBook> readReminderAddressBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableReminderAddressBook> jsonReminderAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableReminderAddressBook.class);
        if (!jsonReminderAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonReminderAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveReminderAddressBook(ReadOnlyReminderAddressBook reminderAddressBook) throws IOException {
        saveReminderAddressBook(reminderAddressBook, filePath);
    }

    /**
     * Similar to {@link #saveReminderAddressBook(ReadOnlyReminderAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveReminderAddressBook(ReadOnlyReminderAddressBook reminderAddressBook,
                                        Path filePath) throws IOException {
        requireNonNull(reminderAddressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableReminderAddressBook(reminderAddressBook), filePath);
    }

}
