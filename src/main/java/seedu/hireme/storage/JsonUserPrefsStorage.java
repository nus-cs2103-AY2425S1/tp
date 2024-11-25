package seedu.hireme.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.hireme.commons.exceptions.DataLoadingException;
import seedu.hireme.commons.util.JsonUtil;
import seedu.hireme.model.ReadOnlyUserPrefs;
import seedu.hireme.model.UserPrefs;

/**
 * A class to access UserPrefs stored in the hard disk as a json file
 */
public class JsonUserPrefsStorage implements UserPrefsStorage {

    private final Path filePath;

    /**
     * Stores the UserPrefs the filepath provided.
     *
     * @param filePath The file path where the UserPrefs will be stored at.
     */
    public JsonUserPrefsStorage(Path filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    @Override
    public Path getUserPrefsFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        requireNonNull(filePath);
        return readUserPrefs(filePath);
    }

    /**
     * Similar to {@link #readUserPrefs()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataLoadingException if the file format is not as expected.
     */
    public Optional<UserPrefs> readUserPrefs(Path prefsFilePath) throws DataLoadingException {
        requireNonNull(prefsFilePath);
        return JsonUtil.readJsonFile(prefsFilePath, UserPrefs.class);
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        requireNonNull(userPrefs);
        requireNonNull(filePath);
        JsonUtil.saveJsonFile(userPrefs, filePath);
    }

}
