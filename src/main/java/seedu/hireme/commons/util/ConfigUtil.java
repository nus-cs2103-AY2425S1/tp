package seedu.hireme.commons.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.hireme.commons.core.Config;
import seedu.hireme.commons.exceptions.DataLoadingException;

/**
 * Provides utility methods for reading from and writing to a configuration file.
 */
public class ConfigUtil {

    /**
     * Reads the configuration file and returns an {@code Optional<Config>} if the file is successfully read.
     * If the file cannot be read or parsed, it throws a {@code DataLoadingException}.
     *
     * @param configFilePath Path to the configuration file.
     * @return Optional containing the {@code Config} object if successfully read, empty otherwise.
     * @throws DataLoadingException If the configuration file cannot be loaded or parsed.
     */
    public static Optional<Config> readConfig(Path configFilePath) throws DataLoadingException {
        return JsonUtil.readJsonFile(configFilePath, Config.class);
    }

    /**
     * Saves the given configuration to the specified file path.
     * If an I/O error occurs during saving, an {@code IOException} is thrown.
     *
     * @param config The {@code Config} object to be saved.
     * @param configFilePath Path to the configuration file.
     * @throws IOException If an error occurs during file writing.
     */
    public static void saveConfig(Config config, Path configFilePath) throws IOException {
        JsonUtil.saveJsonFile(config, configFilePath);
    }

}
