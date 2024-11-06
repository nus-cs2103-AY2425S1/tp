package hallpointer.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import hallpointer.address.commons.exceptions.DataLoadingException;
import hallpointer.address.model.ReadOnlyHallPointer;
import hallpointer.address.model.ReadOnlyUserPrefs;
import hallpointer.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends HallPointerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getHallPointerFilePath();

    @Override
    Optional<ReadOnlyHallPointer> readHallPointer() throws DataLoadingException;

    @Override
    void saveHallPointer(ReadOnlyHallPointer hallPointer) throws IOException;

}
