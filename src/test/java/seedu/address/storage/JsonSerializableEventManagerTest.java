package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.event.EventManager;
import seedu.address.testutil.TypicalEvents;

public class JsonSerializableEventManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableEventManagerTest");
    private static final Path TYPICAL_EVENTS_FILE = TEST_DATA_FOLDER.resolve("typicalEventsEventManager.json");
    private static final Path INVALID_EVENT_FILE = TEST_DATA_FOLDER.resolve("invalidEventEventManager.json");
    private static final Path DUPLICATE_EVENT_FILE = TEST_DATA_FOLDER.resolve("duplicateEventEventManager.json");

    @Test
    public void toModelType_typicalEventsFile_success() throws Exception {
        JsonSerializableEventManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_EVENTS_FILE,
                JsonSerializableEventManager.class).get();
        EventManager eventManagerFromFile = dataFromFile.toModelType();
        EventManager typicalEventsEventManager = TypicalEvents.getTypicalEventManager();
        eventManagerFromFile.equals(typicalEventsEventManager);
        assertEquals(eventManagerFromFile, typicalEventsEventManager);
    }

    @Test
    public void toModelType_invalidEventFile_throwsIllegalValueException() throws Exception {
        JsonSerializableEventManager dataFromFile = JsonUtil.readJsonFile(INVALID_EVENT_FILE,
                JsonSerializableEventManager.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateEvents_throwsIllegalValueException() throws Exception {
        JsonSerializableEventManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EVENT_FILE,
                JsonSerializableEventManager.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableEventManager.MESSAGE_DUPLICATE_EVENT,
                dataFromFile::toModelType);
    }
}
