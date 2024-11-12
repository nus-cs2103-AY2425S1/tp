package seedu.internbuddy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.internbuddy.commons.core.LogsCenter;
import seedu.internbuddy.commons.exceptions.DataLoadingException;
import seedu.internbuddy.commons.util.JsonUtil;
import seedu.internbuddy.model.ReadOnlyUserPrefs;
import seedu.internbuddy.model.UserPrefs;

/**
 * A class to access UserPrefs stored in the hard disk as a json file
 */
public class JsonUserPrefsStorage implements UserPrefsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonUserPrefsStorage.class);
    private Path filePath;

    public JsonUserPrefsStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getUserPrefsFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return readUserPrefs(filePath);
    }

    /**
     * Similar to {@link #readUserPrefs()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataLoadingException if the file format is not as expected.
     */
    public Optional<UserPrefs> readUserPrefs(Path prefsFilePath) throws DataLoadingException {
        logger.info("Reading user prefs...");
        return JsonUtil.readJsonFile(prefsFilePath, UserPrefs.class);
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        logger.info("Saving user prefs...");
        JsonUtil.saveJsonFile(userPrefs, filePath);
    }

}
