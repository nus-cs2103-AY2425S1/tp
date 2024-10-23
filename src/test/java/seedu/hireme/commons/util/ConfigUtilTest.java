package seedu.hireme.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.hireme.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.hireme.commons.core.Config;
import seedu.hireme.commons.exceptions.DataLoadingException;

public class ConfigUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "ConfigUtilTest");

    @TempDir
    public Path tempDir;

    /**
     * Tests that a NullPointerException is thrown when reading a null file path.
     */
    @Test
    public void read_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> read(null));
    }

    /**
     * Tests that reading a non-existent configuration file returns an empty result.
     */
    @Test
    public void read_missingFile_emptyResult() throws DataLoadingException {
        assertFalse(read("NonExistentFile.json").isPresent());
    }

    /**
     * Tests that reading a file that is not in JSON format throws a DataLoadingException.
     */
    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> read("NotJsonFormatConfig.json"));
    }

    /**
     * Tests that a typical configuration file is read successfully.
     */
    @Test
    public void read_fileInOrder_successfullyRead() throws DataLoadingException {
        Config expected = getTypicalConfig();
        Config actual = read("TypicalConfig.json").get();
        assertEquals(expected, actual);
    }

    /**
     * Tests that if values are missing from the configuration file, default values are used.
     */
    @Test
    public void read_valuesMissingFromFile_defaultValuesUsed() throws DataLoadingException {
        Config actual = read("EmptyConfig.json").get();
        assertEquals(new Config(), actual);
    }

    /**
     * Tests that extra values in the configuration file are ignored during reading.
     */
    @Test
    public void read_extraValuesInFile_extraValuesIgnored() throws DataLoadingException {
        Config expected = getTypicalConfig();
        Config actual = read("ExtraValuesConfig.json").get();
        assertEquals(expected, actual);
    }

    private Config getTypicalConfig() {
        Config config = new Config();
        config.setLogLevel(Level.INFO);
        config.setUserPrefsFilePath(Paths.get("preferences.json"));
        return config;
    }

    private Optional<Config> read(String configFileInTestDataFolder) throws DataLoadingException {
        Path configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        return ConfigUtil.readConfig(configFilePath);
    }

    /**
     * Tests that saving a null configuration throws a NullPointerException.
     */
    @Test
    public void save_nullConfig_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> save(null, "SomeFile.json"));
    }

    /**
     * Tests that saving a configuration to a null file path throws a NullPointerException.
     */
    @Test
    public void save_nullFile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> save(new Config(), null));
    }

    /**
     * Tests that saving a configuration file works as expected when the file does not exist
     * and when it already exists, ensuring updates are reflected correctly.
     */
    @Test
    public void saveConfig_allInOrder_success() throws DataLoadingException, IOException {
        Config original = getTypicalConfig();
        Path configFilePath = tempDir.resolve("TempConfig.json");

        // Try writing when the file doesn't exist
        ConfigUtil.saveConfig(original, configFilePath);
        Config readBack = ConfigUtil.readConfig(configFilePath).get();
        assertEquals(original, readBack);

        // Try saving when the file exists
        original.setLogLevel(Level.FINE);
        ConfigUtil.saveConfig(original, configFilePath);
        readBack = ConfigUtil.readConfig(configFilePath).get();
        assertEquals(original, readBack);
    }

    private void save(Config config, String configFileInTestDataFolder) throws IOException {
        Path configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        ConfigUtil.saveConfig(config, configFilePath);
    }

    private Path addToTestDataPathIfNotNull(String configFileInTestDataFolder) {
        return configFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(configFileInTestDataFolder)
                : null;
    }

}
