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

/**
 * A class to access CustomerOrderList data stored as a JSON file on the hard disk.
 */
public class JsonCustomerOrderListStorage implements CustomerOrderListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCustomerOrderListStorage.class);

    private final Path filePath;

    /**
     * Constructs a {@code JsonCustomerOrderListStorage} with the specified file path.
     *
     * @param filePath Path to the data file.
     */
    public JsonCustomerOrderListStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getCustomerOrderListFilePath() {
        return filePath;
    }

    @Override
    public Optional<CustomerOrderList> readCustomerOrderList() throws DataLoadingException {
        return readCustomerOrderList(filePath);
    }

    /**
     * Reads CustomerOrderList data from the specified file path.
     *
     * @param filePath Path to the data file.
     * @return An {@code Optional<CustomerOrderList>} containing the data if successful.
     * @throws DataLoadingException If the data file is not in the correct format or contains illegal values.
     */
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

    /**
     * Saves the given {@code CustomerOrderList} to the specified file path.
     *
     * @param customerOrderList The data to save.
     * @param filePath Path to the data file.
     * @throws IOException If an error occurs while saving to the file.
     */
    public void saveCustomerOrderList(CustomerOrderList customerOrderList, Path filePath) throws IOException {
        requireNonNull(customerOrderList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonAdaptedCustomerOrderList(customerOrderList), filePath);
    }
}