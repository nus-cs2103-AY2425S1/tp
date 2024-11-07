package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.order.SupplyOrderList;

/**
 * Represents a storage for {@link seedu.address.model.order.SupplyOrderList}.
 */
public interface SupplyOrderListStorage {

    Path getSupplyOrderListFilePath();

    Optional<SupplyOrderList> readSupplyOrderList() throws DataLoadingException;

    Optional<SupplyOrderList> readSupplyOrderList(Path filePath) throws DataLoadingException;

    void saveSupplyOrderList(SupplyOrderList supplyOrderList) throws IOException;

    void saveSupplyOrderList(SupplyOrderList supplyOrderList, Path filePath) throws IOException;
}