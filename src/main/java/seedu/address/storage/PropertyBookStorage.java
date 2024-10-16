package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.PropertyBook;

public interface PropertyBookStorage {
    Path getPropertyBookFilePath();

    Optional<PropertyBook> readPropertyBook() throws DataLoadingException;

    void savePropertyBook(PropertyBook propertyBook) throws IOException;
}
