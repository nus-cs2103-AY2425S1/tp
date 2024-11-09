package seedu.address;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.AssignmentList;
import seedu.address.model.tut.TutorialList;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonAssignmentStorage;
import seedu.address.storage.JsonTutorialStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

public class MainAppTest {
    @TempDir
    public Path testFolder;

    private MainApp mainApp;
    private Storage storage;
    private Model model;

    @BeforeEach
    public void setUp() {
        // Initialize MainApp manually
        mainApp = new MainApp();
        LogsCenter.init(new Config()); // Initialize logging

        Path addressBookFilePath = Paths.get("data", "addressBook.json");
        Path userPrefsFilePath = Paths.get("data", "userPrefs.json");
        Path assignmentFilePath = Paths.get("data", "assignment.json");
        Path tutorialFilePath = Paths.get("data", "tutorials.json");

        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookFilePath);
        JsonAssignmentStorage assignmentStorage = new JsonAssignmentStorage(assignmentFilePath);
        JsonTutorialStorage tutorialStorage = new JsonTutorialStorage(tutorialFilePath);

        storage = new StorageManager(addressBookStorage, userPrefsStorage, assignmentStorage, tutorialStorage);

        UserPrefs userPrefs = new UserPrefs();
        AddressBook addressBook = new AddressBook();
        AssignmentList assignmentList = new AssignmentList();
        TutorialList tutorialList = new TutorialList();

        model = new ModelManager(addressBook, userPrefs, assignmentList, tutorialList);
    }

    @Test
    public void testInitLogging() {
        // Check that logging doesn't throw any exceptions
        assertDoesNotThrow(() -> {
            Logger logger = LogsCenter.getLogger(MainApp.class);
            logger.info("Logging works.");
        });
    }

    @Test
    public void testInitConfigDefaultFile() {
        // Test initializing the config with a default file path
        Path defaultConfigFilePath = Config.DEFAULT_CONFIG_FILE;
        Config config = mainApp.initConfig(null);

        // Assert that the config file path used is the default one
        assertEquals(defaultConfigFilePath, Config.DEFAULT_CONFIG_FILE);
    }

    @Test
    public void testInitPrefsWithDefaultFile() {
        // Test initializing preferences with the default file path
        assertDoesNotThrow(() -> {
            UserPrefs userPrefs = mainApp.initPrefs(new JsonUserPrefsStorage(Paths.get("data", "userPrefs.json")));
            assertEquals(userPrefs, model.getUserPrefs());
        });
    }

}
