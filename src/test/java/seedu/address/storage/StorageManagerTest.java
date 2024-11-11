package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalClients.getTypicalClientBook;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingBook;
import static seedu.address.testutil.TypicalProperty.getTypicalPropertyBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ClientBook;
import seedu.address.model.MeetingBook;
import seedu.address.model.PropertyBook;
import seedu.address.model.ReadOnlyClientBook;
import seedu.address.model.ReadOnlyMeetingBook;
import seedu.address.model.ReadOnlyPropertyBook;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonClientBookStorage clientBookStorage = new JsonClientBookStorage(getTempFilePath("cb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonPropertyBookStorage propertyBookStorage = new JsonPropertyBookStorage(getTempFilePath("pb"));
        JsonMeetingBookStorage meetingBookStorage = new JsonMeetingBookStorage(getTempFilePath("mb"));
        storageManager = new StorageManager(userPrefsStorage, propertyBookStorage,
                clientBookStorage, meetingBookStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    // ====================== UserPrefs Tests ======================

    @Test
    public void prefsReadSave() throws Exception {
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    // ====================== ClientBook Tests ======================

    @Test
    public void clientBookReadSave() throws Exception {
        ClientBook original = getTypicalClientBook();
        storageManager.saveClientBook(original);
        ReadOnlyClientBook retrieved = storageManager.readClientBook().get();
        assertEquals(original, new ClientBook(retrieved));
    }

    @Test
    public void getClientBookFilePath() {
        assertNotNull(storageManager.getClientBookFilePath());
    }

    // ====================== PropertyBook Tests ======================

    @Test
    public void propertyBookReadSave() throws Exception {
        PropertyBook original = getTypicalPropertyBook();
        storageManager.savePropertyBook(original);
        ReadOnlyPropertyBook retrieved = storageManager.readPropertyBook().get();
        assertEquals(original, new PropertyBook(retrieved));
    }

    @Test
    public void getPropertyBookFilePath() {
        assertNotNull(storageManager.getPropertyBookFilePath());
    }

    // ====================== MeetingBook Tests ======================

    @Test
    public void meetingBookReadSave() throws Exception {
        /*
         * This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonMeetingBookStorage} class. More extensive testing of MeetingBook saving/reading is done in
         * {@link JsonMeetingBookStorageTest}.
         */
        MeetingBook original = getTypicalMeetingBook();
        storageManager.saveMeetingBook(original);
        ReadOnlyMeetingBook retrieved = storageManager.readMeetingBook().get();
        assertEquals(original, new MeetingBook(retrieved));
    }

    @Test
    public void getMeetingBookFilePath() {
        assertNotNull(storageManager.getMeetingBookFilePath());
    }
}
