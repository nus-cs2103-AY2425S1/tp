package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.PropertyBook;

public class JsonPropertyBookStorage implements PropertyBookStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonPropertyBookStorage.class);
    private final Path filePath;

    public JsonPropertyBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getPropertyBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<PropertyBook> readPropertyBook() throws DataLoadingException {
        return readPropertyBook(filePath);
    }

    public Optional<PropertyBook> readPropertyBook(Path filePath) throws DataLoadingException {
        Optional<JsonSerializablePropertyBook> jsonPropertyBook = JsonUtil.readJsonFile(
                filePath, JsonSerializablePropertyBook.class);
        if (!jsonPropertyBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPropertyBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void savePropertyBook(PropertyBook propertyBook) throws IOException {
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePropertyBook(propertyBook), filePath);
    }
}
