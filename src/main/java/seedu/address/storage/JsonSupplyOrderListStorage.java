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
import seedu.address.model.order.SupplyOrderList;

/**
 * A class to access SupplyOrderList data stored as a json file on the hard disk.
 */
public class JsonSupplyOrderListStorage implements SupplyOrderListStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonSupplyOrderListStorage.class);
    private Path filePath;

    public JsonSupplyOrderListStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getSupplyOrderListFilePath() {
        return filePath;
    }

    @Override
    public Optional<SupplyOrderList> readSupplyOrderList() throws DataLoadingException {
        return readSupplyOrderList(filePath);
    }

    @Override
    public Optional<SupplyOrderList> readSupplyOrderList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);
        Optional<JsonAdaptedSupplyOrderList> jsonSupplyOrderList = JsonUtil.readJsonFile(
                filePath, JsonAdaptedSupplyOrderList.class);
        if (!jsonSupplyOrderList.isPresent()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonSupplyOrderList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveSupplyOrderList(SupplyOrderList supplyOrderList) throws IOException {
        saveSupplyOrderList(supplyOrderList, filePath);
    }

    @Override
    public void saveSupplyOrderList(SupplyOrderList supplyOrderList, Path filePath) throws IOException {
        requireNonNull(supplyOrderList);
        requireNonNull(filePath);
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonAdaptedSupplyOrderList(supplyOrderList), filePath);
    }
}