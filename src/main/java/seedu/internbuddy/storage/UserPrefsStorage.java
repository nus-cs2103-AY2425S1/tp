package seedu.internbuddy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.internbuddy.commons.exceptions.DataLoadingException;
import seedu.internbuddy.model.ReadOnlyUserPrefs;
import seedu.internbuddy.model.UserPrefs;

/**
 * Represents a storage for {@link seedu.internbuddy.model.UserPrefs}.
 */
public interface UserPrefsStorage {

    /**
     * Returns the file path of the UserPrefs data file.
     */
    Path getUserPrefsFilePath();

    /**
     * Returns UserPrefs data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if the loading of data from preference file failed.
     */
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    /**
     * Saves the given {@link seedu.internbuddy.model.ReadOnlyUserPrefs} to the storage.
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

}
