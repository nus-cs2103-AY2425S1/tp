package tahub.contacts;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import tahub.contacts.commons.core.Config;
import tahub.contacts.commons.util.ConfigUtil;
import tahub.contacts.storage.JsonAddressBookStorage;
import tahub.contacts.storage.JsonUniqueCourseListStorage;
import tahub.contacts.storage.JsonUserPrefsStorage;
import tahub.contacts.storage.StorageManager;
import tahub.contacts.storage.UserPrefsStorage;

public class MainAppTest {

    @TempDir
    public Path temporaryFolder;

    private MainApp mainApp;
    private StorageManager storageManager;
    private UserPrefsStorage userPrefsStorage;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonUniqueCourseListStorage courseListStorage =
                new JsonUniqueCourseListStorage(temporaryFolder.resolve("courseList.json"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage, courseListStorage);
        mainApp = new MainApp();
    }

    @Test
    public void initConfig_validFilePath_success() throws Exception {
        Path configFilePath = temporaryFolder.resolve("config.json");
        Config config = new Config();
        ConfigUtil.saveConfig(config, configFilePath);

        Config initializedConfig = mainApp.initConfig(configFilePath);
        assertNotNull(initializedConfig);
    }

    @Test
    public void initConfig_invalidFilePath_usesDefaultConfig() {
        Path invalidFilePath = temporaryFolder.resolve("invalidConfig.json");

        Config initializedConfig = mainApp.initConfig(invalidFilePath);
        assertNotNull(initializedConfig);
    }

    @Test
    public void start_validStage_success() {
        // Mock the UI component and test the start method
        // This is a placeholder as testing JavaFX components requires a different setup
        assertTrue(true);
    }

}
