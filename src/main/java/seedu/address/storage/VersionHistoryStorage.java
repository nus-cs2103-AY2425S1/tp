package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.VersionHistory;

/**
 * Represents a storage for {@link seedu.address.model.VersionHistory}.
 */
public interface VersionHistoryStorage {

    /**
     * Returns the file path of the VersionHistory data file.
     */
    Path getVersionHistoryFilePath();

    /**
     * Returns VersionHistory data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if the loading of data from history file failed.
     */
    Optional<VersionHistory> readVersionHistory() throws DataLoadingException;

    /**
     * Saves the given {@link seedu.address.model.ReadOnlyUserPrefs} to the storage.
     * @param versionHistory cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveVersionHistory(VersionHistory versionHistory) throws IOException;

}
