package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.order.CustomerOrderList;

/**
 * Represents a storage for {@link seedu.address.model.order.CustomerOrderList}.
 */
public interface CustomerOrderListStorage {

    Path getCustomerOrderListFilePath();

    Optional<CustomerOrderList> readCustomerOrderList() throws DataLoadingException;

    Optional<CustomerOrderList> readCustomerOrderList(Path filePath) throws DataLoadingException;

    void saveCustomerOrderList(CustomerOrderList customerOrderList) throws IOException;

    void saveCustomerOrderList(CustomerOrderList customerOrderList, Path filePath) throws IOException;
}