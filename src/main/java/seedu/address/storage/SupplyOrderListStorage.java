package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.order.SupplyOrderList;

/**
 * Represents a storage for {@link SupplyOrderList} data.
 * Defines methods to read from and write to a storage location.
 */
public interface SupplyOrderListStorage {

    /**
     * Returns the file path where the SupplyOrderList data is stored.
     *
     * @return The file path of the SupplyOrderList storage
     */
    Path getSupplyOrderListFilePath();

    /**
     * Reads SupplyOrderList data from the default storage file.
     *
     * @return An Optional containing the SupplyOrderList if successful,
     *         or an empty Optional if the data cannot be read
     * @throws DataLoadingException if there was an error loading the data
     */
    Optional<SupplyOrderList> readSupplyOrderList() throws DataLoadingException;

    /**
     * Reads SupplyOrderList data from the specified file path.
     *
     * @param filePath The file path to read the SupplyOrderList data from
     * @return An Optional containing the SupplyOrderList if successful,
     *         or an empty Optional if the data cannot be read
     * @throws DataLoadingException if there was an error loading the data
     */
    Optional<SupplyOrderList> readSupplyOrderList(Path filePath) throws DataLoadingException;

    /**
     * Saves the given SupplyOrderList to the default storage file.
     *
     * @param supplyOrderList The SupplyOrderList to save. Cannot be null.
     * @throws IOException if there was an error saving the data
     */
    void saveSupplyOrderList(SupplyOrderList supplyOrderList) throws IOException;

    /**
     * Saves the given SupplyOrderList to the specified file path.
     *
     * @param supplyOrderList The SupplyOrderList to save. Cannot be null.
     * @param filePath The file path to save the SupplyOrderList data to
     * @throws IOException if there was an error saving the data
     */
    void saveSupplyOrderList(SupplyOrderList supplyOrderList, Path filePath) throws IOException;
}