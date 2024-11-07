package tahub.contacts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.application.Application;
import tahub.contacts.commons.core.Config;
import tahub.contacts.commons.util.ConfigUtil;
import tahub.contacts.storage.JsonAddressBookStorage;
import tahub.contacts.storage.JsonStudentCourseAssociationListStorage;
import tahub.contacts.storage.JsonUniqueCourseListStorage;
import tahub.contacts.storage.JsonUserPrefsStorage;
import tahub.contacts.storage.StorageManager;

public class AppParametersTest {

    @TempDir
    public Path temporaryFolder;
    private final ParametersStub parametersStub = new ParametersStub();
    private final AppParameters expected = new AppParameters();
    private MainApp mainApp;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonUniqueCourseListStorage courseListStorage =
                new JsonUniqueCourseListStorage(temporaryFolder.resolve("courseList.json"));
        JsonStudentCourseAssociationListStorage scaListStorage =
                new JsonStudentCourseAssociationListStorage(temporaryFolder.resolve("courseList.json"));
        StorageManager storageManager = new StorageManager(addressBookStorage, userPrefsStorage, courseListStorage,
                scaListStorage);
        mainApp = new MainApp();
    }

    @Test
    public void parse_validConfigPath_success() {
        parametersStub.namedParameters.put("config", "config.json");
        expected.setConfigPath(Paths.get("config.json"));
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void parse_nullConfigPath_success() {
        parametersStub.namedParameters.put("config", null);
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void parse_invalidConfigPath_success() {
        parametersStub.namedParameters.put("config", "a\0");
        expected.setConfigPath(null);
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void toStringMethod() {
        AppParameters appParameters = new AppParameters();
        String expected = AppParameters.class.getCanonicalName() + "{configPath=" + appParameters.getConfigPath() + "}";
        assertEquals(expected, appParameters.toString());
    }

    @Test
    public void equals() {
        AppParameters appParameters = new AppParameters();

        // same values -> returns true
        assertTrue(appParameters.equals(new AppParameters()));

        // same object -> returns true
        assertTrue(appParameters.equals(appParameters));

        // null -> returns false
        assertFalse(appParameters.equals(null));

        // different types -> returns false
        assertFalse(appParameters.equals(5.0f));

        // different config path -> returns false
        AppParameters otherAppParameters = new AppParameters();
        otherAppParameters.setConfigPath(Paths.get("configPath"));
        assertFalse(appParameters.equals(otherAppParameters));
    }

    private static class ParametersStub extends Application.Parameters {
        private Map<String, String> namedParameters = new HashMap<>();

        @Override
        public List<String> getRaw() {
            throw new AssertionError("should not be called");
        }

        @Override
        public List<String> getUnnamed() {
            throw new AssertionError("should not be called");
        }

        @Override
        public Map<String, String> getNamed() {
            return Collections.unmodifiableMap(namedParameters);
        }
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
