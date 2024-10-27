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
import seedu.address.model.ReadOnlyEduContacts;

/**
 * A class to access EduContacts data stored as a json file on the hard disk.
 */
public class JsonEduContactsStorage implements EduContactsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonEduContactsStorage.class);

    private Path filePath;

    public JsonEduContactsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEduContactsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEduContacts> readEduContacts() throws DataLoadingException {
        return readEduContacts(filePath);
    }

    /**
     * Similar to {@link #readEduContacts()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyEduContacts> readEduContacts(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableEduContacts> jsonEduContacts = JsonUtil.readJsonFile(
                filePath, JsonSerializableEduContacts.class);
        if (!jsonEduContacts.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEduContacts.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveEduContacts(ReadOnlyEduContacts eduContacts) throws IOException {
        saveEduContacts(eduContacts, filePath);
    }

    /**
     * Similar to {@link #saveEduContacts(ReadOnlyEduContacts)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveEduContacts(ReadOnlyEduContacts eduContacts, Path filePath) throws IOException {
        requireNonNull(eduContacts);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEduContacts(eduContacts), filePath);
    }

}
