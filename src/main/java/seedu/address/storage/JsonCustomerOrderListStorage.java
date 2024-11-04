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
import seedu.address.model.order.CustomerOrderList;

public class JsonCustomerOrderListStorage implements CustomerOrderListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCustomerOrderListStorage.class);

    private Path filePath;

    public JsonCustomerOrderListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCustomerOrderListFilePath() {
        return filePath;
    }

    @Override
    public Optional<CustomerOrderList> readCustomerOrderList() throws DataLoadingException {
        return readCustomerOrderList(filePath);
    }

    public Optional<CustomerOrderList> readCustomerOrderList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonAdaptedCustomerOrderList> jsonCustomerOrderList = JsonUtil.readJsonFile(
                filePath, JsonAdaptedCustomerOrderList.class);
        if (!jsonCustomerOrderList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCustomerOrderList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveCustomerOrderList(CustomerOrderList customerOrderList) throws IOException {
        saveCustomerOrderList(customerOrderList, filePath);
    }

    public void saveCustomerOrderList(CustomerOrderList customerOrderList, Path filePath) throws IOException {
        requireNonNull(customerOrderList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonAdaptedCustomerOrderList(customerOrderList), filePath);
    }
}