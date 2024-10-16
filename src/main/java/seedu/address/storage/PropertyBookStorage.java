package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.PropertyBook;


/**
 * Provides methods to manage the storage of PropertyBook data.
 * This class facilitates loading and saving PropertyBook data to and from storage.
 */
public interface PropertyBookStorage {
    Path getPropertyBookFilePath();

    Optional<PropertyBook> readPropertyBook() throws DataLoadingException;

    void savePropertyBook(PropertyBook propertyBook) throws IOException;
}
