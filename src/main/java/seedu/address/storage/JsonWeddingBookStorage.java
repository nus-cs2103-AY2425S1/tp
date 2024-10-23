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
import seedu.address.model.ReadOnlyWeddingBook;

/**
 * A class to access WeddingBook data stored as a json file on the hard disk.
 */
public class JsonWeddingBookStorage implements WeddingBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;

    public JsonWeddingBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getWeddingBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyWeddingBook> readWeddingBook() throws DataLoadingException {
        return readWeddingBook(filePath);
    }

    /**
     * Similar to {@link #readWeddingBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyWeddingBook> readWeddingBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableWeddingBook> jsonWeddingBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableWeddingBook.class);
        if (!jsonWeddingBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonWeddingBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveWeddingBook(ReadOnlyWeddingBook weddingBook) throws IOException {
        saveWeddingBook(weddingBook, filePath);
    }

    /**
     * Similar to {@link #saveWeddingBook(ReadOnlyWeddingBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveWeddingBook(ReadOnlyWeddingBook weddingBook, Path filePath) throws IOException {
        requireNonNull(weddingBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableWeddingBook(weddingBook), filePath);
    }

}
