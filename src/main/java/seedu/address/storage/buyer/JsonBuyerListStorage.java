package seedu.address.storage.buyer;

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
import seedu.address.model.ReadOnlyBuyerList;

/**
 * A class to access BuyerList data stored as a json file on the hard disk.
 */
public class JsonBuyerListStorage implements BuyerListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonBuyerListStorage.class);

    private Path filePath;

    public JsonBuyerListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getBuyerListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyBuyerList> readBuyerList() throws DataLoadingException {
        return readBuyerList(filePath);
    }

    /**
     * Similar to {@link #readBuyerList()}.
     *
     * @param filePath address of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyBuyerList> readBuyerList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableBuyerList> jsonBuyerList = JsonUtil.readJsonFile(
                filePath, JsonSerializableBuyerList.class);
        if (!jsonBuyerList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonBuyerList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveBuyerList(ReadOnlyBuyerList buyerList) throws IOException {
        saveBuyerList(buyerList, filePath);
    }

    /**
     * Similar to {@link #saveBuyerList(ReadOnlyBuyerList)}.
     *
     * @param filePath address of the data. Cannot be null.
     */
    public void saveBuyerList(ReadOnlyBuyerList buyerList, Path filePath) throws IOException {
        requireNonNull(buyerList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBuyerList(buyerList), filePath);
    }

}
