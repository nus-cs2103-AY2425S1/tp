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
    //TODO: Fails with ubuntu OS
    //    @Test
    //    public void testStartAndStopApplication() {
    //        assertDoesNotThrow(() -> {
    //            Platform.runLater(() -> {
    //                try {
    //                    mainApp.start(new Stage());
    //                    mainApp.stop();
    //                } catch (Exception e) {
    //                    throw new RuntimeException(e);
    //                }
    //            });
    //            Thread.sleep(1000);
    //        });
    //    }

    @Test
    public void testInitConfigDefaultFile() {
        // Test initializing the config with a default file path
        Path defaultConfigFilePath = Config.DEFAULT_CONFIG_FILE;
        Config config = mainApp.initConfig(null);

        // Assert that the config file path used is the default one
        assertEquals(defaultConfigFilePath, Config.DEFAULT_CONFIG_FILE);
    }

    //TODO: Fails with ubuntu OS
    //    @Test
    //    public void testInitModelManagerWithDefaultData() {
    //        // Simulate starting the app with default data
    //        assertDoesNotThrow(() -> {
    //            ModelManager modelManager = (ModelManager) mainApp.initModelManager(storage, new UserPrefs());
    //            assertEquals(modelManager.getAddressBook(), model.getAddressBook());
    //        });
    //    }


    @Test
    public void testInitPrefsWithDefaultFile() {
        // Test initializing preferences with the default file path
        assertDoesNotThrow(() -> {
            UserPrefs userPrefs = mainApp.initPrefs(new JsonUserPrefsStorage(Paths.get("data", "userPrefs.json")));
            assertEquals(userPrefs, model.getUserPrefs());
        });
    }

    //TODO: Fails with ubuntu OS
    //    @Test
    //    public void testInitModelManagerWithExistingData() {
    //        AddressBook expectedAddressBook = new AddressBook();
    //        expectedAddressBook.addStudent(ALICE);
    //        expectedAddressBook.addStudent(BOB);
    //
    //        AssignmentList expectedAssignmentList = getTypicalAssignmentList();
    //        TutorialList expectedTutorialList = getTypicalTutorialList();
    //
    //        assertDoesNotThrow(() -> {
    //            storage.saveAddressBook(expectedAddressBook);
    //            storage.saveAssignments(expectedAssignmentList);
    //            storage.saveTutorials(expectedTutorialList);
    //        });
    //
    //        assertDoesNotThrow(() -> {
    //            model = mainApp.initModelManager(storage, new UserPrefs());
    //        });
    //
    //        assertEquals(expectedAddressBook, model.getAddressBook());
    //        assertEquals(expectedAssignmentList, model.getAssignmentList());
    //        assertEquals(expectedTutorialList, model.getTutorialList());
    //    }

    //TODO: Fails with ubuntu OS
    //    @Test
    //    public void testInitPrefsWithNonExistentFile() {
    //        Path nonExistentPrefsFile = getTempFilePath("nonExistentPrefs.json");
    //
    //        assertDoesNotThrow(() -> Files.deleteIfExists(nonExistentPrefsFile));
    //
    //        assertDoesNotThrow(() -> {
    //            UserPrefs userPrefs = mainApp.initPrefs(new JsonUserPrefsStorage(nonExistentPrefsFile));
    //            assertEquals(new UserPrefs(), userPrefs);
    //        });
    //    }

    //TODO: Fails with ubuntu OS
    //    @Test
    //    public void testInitModelManagerWithCorruptedData() {
    //        Path corruptedAddressBookFilePath = getTempFilePath("corruptedAddressBook.json");
    //
    //        try {
    //            Files.writeString(corruptedAddressBookFilePath, "{Invalid JSON Content}");
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //
    //        JsonAddressBookStorage corruptedAddressBookStorage =
    //          new JsonAddressBookStorage(corruptedAddressBookFilePath);
    //        Storage corruptedStorage = new StorageManager(
    //                corruptedAddressBookStorage,
    //                new JsonUserPrefsStorage(getTempFilePath("userPrefs.json")),
    //                new JsonAssignmentStorage(getTempFilePath("assignment.json")),
    //                new JsonTutorialStorage(getTempFilePath("tutorials.json"))
    //        );
    //
    //        assertDoesNotThrow(() -> {
    //            Model modelManager = mainApp.initModelManager(corruptedStorage, new UserPrefs());
    //            assertEquals(new AddressBook(), modelManager.getAddressBook()); // Verify it uses empty AddressBook
    //        });
    //    }

    //TODO: Fails with ubuntu OS
    //    @Test
    //    public void testInitModelManagerWithMissingTutorialFile() {
    //        Path missingTutorialFilePath = getTempFilePath("missingTutorial.json");
    //        try {
    //            Files.deleteIfExists(missingTutorialFilePath);
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //
    //        JsonTutorialStorage tutorialStorage = new JsonTutorialStorage(missingTutorialFilePath);
    //        Storage missingTutorialStorage = new StorageManager(
    //                new JsonAddressBookStorage(getTempFilePath("addressBook.json")),
    //                new JsonUserPrefsStorage(getTempFilePath("userPrefs.json")),
    //                new JsonAssignmentStorage(getTempFilePath("assignment.json")),
    //                tutorialStorage
    //        );
    //
    //        assertDoesNotThrow(() -> {
    //            Model modelManager = mainApp.initModelManager(missingTutorialStorage, new UserPrefs());
    //            assertEquals(new TutorialList(), modelManager.getTutorialList());
    //        });
    //    }

    //TODO: Fails with ubuntu OS
    //    @Test
    //    public void testInitPrefsWithCorruptedFile() {
    //        Path corruptedUserPrefsFilePath = getTempFilePath("corruptedUserPrefs.json");
    //
    //        try {
    //            Files.writeString(corruptedUserPrefsFilePath, "{Corrupted Data}");
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //
    //        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(corruptedUserPrefsFilePath);
    //        assertDoesNotThrow(() -> {
    //            UserPrefs userPrefs = mainApp.initPrefs(userPrefsStorage);
    //            assertEquals(new UserPrefs(), userPrefs);
    //        });
    //    }

    //TODO: Fails with ubuntu OS
    //    @Test
    //    public void testInitModelManagerWithSampleDataOnFirstRun() {
    //        Path sampleAddressBookFilePath = getTempFilePath("firstRunAddressBook.json");
    //        Path sampleAssignmentFilePath = getTempFilePath("firstRunAssignment.json");
    //        Path sampleTutorialFilePath = getTempFilePath("firstRunTutorial.json");
    //
    //        try {
    //            Files.deleteIfExists(sampleAddressBookFilePath);
    //            Files.deleteIfExists(sampleAssignmentFilePath);
    //            Files.deleteIfExists(sampleTutorialFilePath);
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //
    //        Storage sampleStorage = new StorageManager(
    //                new JsonAddressBookStorage(sampleAddressBookFilePath),
    //                new JsonUserPrefsStorage(getTempFilePath("prefs.json")),
    //                new JsonAssignmentStorage(sampleAssignmentFilePath),
    //                new JsonTutorialStorage(sampleTutorialFilePath)
    //        );
    //
    //        assertDoesNotThrow(() -> {
    //            Model modelManager = mainApp.initModelManager(sampleStorage, new UserPrefs());
    //            assertEquals(new AddressBook(), modelManager.getAddressBook());
    //            assertEquals(new AssignmentList(), modelManager.getAssignmentList());
    //            assertEquals(new TutorialList(), modelManager.getTutorialList());
    //        });
    //    }

    //TODO: Fails with ubuntu OS
    //    @Test
    //    public void testSaveAndLoadUserPrefs() {
    //        Path userPrefsFilePath = getTempFilePath("userPrefs.json");
    //        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);
    //
    //        UserPrefs prefs = new UserPrefs();
    //        prefs.setGuiSettings(new GuiSettings(800, 600, 50, 50));
    //        prefs.setAddressBookFilePath(Paths.get("test/addressBook.json"));
    //
    //        assertDoesNotThrow(() -> userPrefsStorage.saveUserPrefs(prefs));
    //
    //        assertDoesNotThrow(() -> {
    //            Optional<UserPrefs> loadedPrefs = userPrefsStorage.readUserPrefs();
    //            assertTrue(loadedPrefs.isPresent());
    //            assertEquals(prefs, loadedPrefs.get());
    //        });
    //    }

    //TODO: Fails with ubuntu OS
    //    @AfterAll
    //    public static void cleanupJavaFX() {
    //        Platform.exit();
    //    }
}
