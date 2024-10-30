package seedu.eventtory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eventtory.commons.exceptions.DataLoadingException;
import seedu.eventtory.model.ReadOnlyEventTory;
import seedu.eventtory.model.ReadOnlyUserPrefs;
import seedu.eventtory.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends EventToryStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getEventToryFilePath();

    @Override
    Optional<ReadOnlyEventTory> readEventTory() throws DataLoadingException;

    @Override
    void saveEventTory(ReadOnlyEventTory eventTory) throws IOException;

}
