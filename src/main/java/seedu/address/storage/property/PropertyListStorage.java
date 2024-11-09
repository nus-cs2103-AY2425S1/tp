package seedu.address.storage.property;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyPropertyList;

/**
 * Represents a storage for {@link seedu.address.model.PropertyList}.
 */
public interface PropertyListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPropertyListFilePath();

    /**
     * Returns Property data as a {@link ReadOnlyPropertyList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyPropertyList> readPropertyList() throws DataLoadingException;

    /**
     * @see #getPropertyListFilePath()
     */
    Optional<ReadOnlyPropertyList> readPropertyList(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyPropertyList} to the storage.
     * @param propertyList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePropertyList(ReadOnlyPropertyList propertyList) throws IOException;

    /**
     * @see #savePropertyList(ReadOnlyPropertyList)
     */
    void savePropertyList(ReadOnlyPropertyList property, Path filePath) throws IOException;

}
