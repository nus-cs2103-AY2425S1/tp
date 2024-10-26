package seedu.edulog.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.edulog.commons.core.LogsCenter;
import seedu.edulog.commons.exceptions.DataLoadingException;
import seedu.edulog.commons.exceptions.IllegalValueException;
import seedu.edulog.commons.util.FileUtil;
import seedu.edulog.commons.util.JsonUtil;
import seedu.edulog.model.ReadOnlyGiftList;

/**
 * A class to access GiftList data stored as a json file on the hard disk.
 */
public class JsonGiftListStorage implements GiftListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonGiftListStorage.class);

    private Path filePath;

    public JsonGiftListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getGiftListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyGiftList> readGiftList() throws DataLoadingException {
        return readGiftList(filePath);
    }

    /**
     * Similar to {@link #readGiftList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyGiftList> readGiftList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableGiftList> jsonGiftList = JsonUtil.readJsonFile(
                filePath, JsonSerializableGiftList.class);
        if (!jsonGiftList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonGiftList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveGiftList(ReadOnlyGiftList giftList) throws IOException {
        saveGiftList(giftList, filePath);
    }

    /**
     * Similar to {@link #saveGiftList(ReadOnlyGiftList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveGiftList(ReadOnlyGiftList giftList, Path filePath) throws IOException {
        requireNonNull(giftList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableGiftList(giftList), filePath);
    }

}
