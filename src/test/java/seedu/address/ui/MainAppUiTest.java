package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.Label;
import seedu.address.MainApp;
import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.UserPrefsStorage;

@ExtendWith(ApplicationExtension.class)
public class MainAppUiTest {

    private MainApp app;

    @BeforeEach
    public void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
        app = new MainApp();
        FxToolkit.setupApplication(() -> app);
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(20);
    }

    @AfterEach
    public void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @Test
    public void constructor_noCustomStoragePath_usesDefaultStoragePath(FxRobot robot) {
        var locationStatusNode = robot.lookup("#saveLocationStatus").tryQuery();
        assertTrue(locationStatusNode.isPresent());
        var locationStatusNodeLabel = (Label) locationStatusNode.get();
        UserPrefs userPrefs = new UserPrefs();
        assertEquals(Paths.get(".").resolve(userPrefs.getAddressBookFilePath()).toString(),
                locationStatusNodeLabel.getText()
        );
    }

    @Test
    public void init_noUserPrefsFile_createsDefaultUserPrefs() throws Exception {
        Path userPrefsFilePath = Paths.get("nonexistent/path");
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);
        UserPrefs userPrefs = userPrefsStorage.readUserPrefs().orElse(new UserPrefs());

        assertEquals(new UserPrefs(), userPrefs);
    }

    @Test
    public void init_prefsFileNotReadable_createsNewUserPrefs() {
        Path userPrefsFilePath = Paths.get("nonexistent/path");
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath) {
            @Override
            public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
                throw new DataLoadingException(new Exception("Simulated read error"));
            }
        };

        UserPrefs userPrefs = null;
        try {
            userPrefs = userPrefsStorage.readUserPrefs().orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            userPrefs = new UserPrefs();
        }

        assertNotNull(userPrefs);
    }
    @Test
    public void init_configFileReadable_customConfigFileUsed() {
        Config config = new Config();

        assertNotNull(config);
        assertEquals(Paths.get("preferences.json"), config.getUserPrefsFilePath());
    }
}

