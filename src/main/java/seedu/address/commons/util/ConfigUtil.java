package seedu.address.commons.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataLoadingException;

/**
 * A class for accessing the Config File.
 */
public class ConfigUtil {

    /**
     * Reads the config details from a JSON file.
     */
    public static Optional<Config> readConfig(Path configFilePath) throws DataLoadingException {
        return JsonUtil.readJsonFile(configFilePath, Config.class);
    }

    /**
     * Saves the config details into a JSON file.
     */
    public static void saveConfig(Config config, Path configFilePath) throws IOException {
        JsonUtil.saveJsonFile(config, configFilePath);
    }

}
