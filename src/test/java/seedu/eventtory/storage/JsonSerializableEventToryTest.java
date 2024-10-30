package seedu.eventtory.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eventtory.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.eventtory.commons.exceptions.IllegalValueException;
import seedu.eventtory.commons.util.JsonUtil;
import seedu.eventtory.model.EventTory;
import seedu.eventtory.testutil.TypicalEvents;
import seedu.eventtory.testutil.TypicalVendors;

public class JsonSerializableEventToryTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableEventToryTest");
    private static final Path TYPICAL_VENDORS_FILE = TEST_DATA_FOLDER.resolve("typicalVendorsEventTory.json");
    private static final Path INVALID_VENDOR_FILE = TEST_DATA_FOLDER.resolve("invalidVendorEventTory.json");
    private static final Path DUPLICATE_VENDOR_FILE = TEST_DATA_FOLDER.resolve("duplicateVendorEventTory.json");
    private static final Path TYPICAL_EVENTS_FILE = TEST_DATA_FOLDER.resolve("typicalEventsEventTory.json");
    private static final Path INVALID_EVENT_FILE = TEST_DATA_FOLDER.resolve("invalidEventEventTory.json");
    private static final Path DUPLICATE_EVENT_FILE = TEST_DATA_FOLDER.resolve("duplicateEventEventTory.json");
    private static final Path TYPICAL_EVENTTORY = TEST_DATA_FOLDER.resolve("typicalEventTory.json");
    private static final Path INVALID_ASSOCIATION_VENDOR_ID_FILE = TEST_DATA_FOLDER.resolve(
            "invalidAssociationVendorId.json");
    private static final Path INVALID_ASSOCIATION_EVENT_ID_FILE = TEST_DATA_FOLDER.resolve(
            "invalidAssociationEventId.json");

    @Test
    public void toModelType_typicalEventTory_success() throws Exception {
        JsonSerializableEventTory dataFromFile = JsonUtil.readJsonFile(TYPICAL_EVENTTORY,
                JsonSerializableEventTory.class).get();
        EventTory eventToryFromFile = dataFromFile.toModelType();

        // Verify that the loaded EventTory contains the expected vendors, events, and associations
        assertEquals(2, eventToryFromFile.getVendorList().size());
        assertEquals(2, eventToryFromFile.getEventList().size());
        assertEquals(2, eventToryFromFile.getAssociationList().size());
    }

    @Test
    public void toModelType_invalidAssociationVendorId_throwsIllegalValueException() throws Exception {
        JsonSerializableEventTory dataFromFile = JsonUtil.readJsonFile(INVALID_ASSOCIATION_VENDOR_ID_FILE,
                JsonSerializableEventTory.class).get();

        assertThrows(IllegalValueException.class,
                JsonSerializableEventTory.MESSAGE_NON_EXISTENT_VENDOR, () -> dataFromFile.toModelType());
    }

    @Test
    public void toModelType_invalidAssociationEventId_throwsIllegalValueException() throws Exception {
        JsonSerializableEventTory dataFromFile = JsonUtil.readJsonFile(INVALID_ASSOCIATION_EVENT_ID_FILE,
                JsonSerializableEventTory.class).get();

        assertThrows(IllegalValueException.class,
                JsonSerializableEventTory.MESSAGE_NON_EXISTENT_EVENT, () -> dataFromFile.toModelType());
    }

    @Test
    public void toModelType_typicalVendorsFile_success() throws Exception {
        JsonSerializableEventTory dataFromFile = JsonUtil.readJsonFile(TYPICAL_VENDORS_FILE,
                JsonSerializableEventTory.class).get();
        EventTory eventToryFromFile = dataFromFile.toModelType();
        EventTory typicalVendorsEventTory = TypicalVendors.getTypicalEventTory();
        assertEquals(eventToryFromFile, typicalVendorsEventTory);
    }

    @Test
    public void toModelType_invalidVendorFile_throwsIllegalValueException() throws Exception {
        JsonSerializableEventTory dataFromFile = JsonUtil.readJsonFile(INVALID_VENDOR_FILE,
                JsonSerializableEventTory.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateVendors_throwsIllegalValueException() throws Exception {
        JsonSerializableEventTory dataFromFile = JsonUtil.readJsonFile(DUPLICATE_VENDOR_FILE,
                JsonSerializableEventTory.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableEventTory.MESSAGE_DUPLICATE_VENDOR,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalEventsFile_success() throws Exception {
        JsonSerializableEventTory dataFromFile = JsonUtil.readJsonFile(TYPICAL_EVENTS_FILE,
                JsonSerializableEventTory.class).get();
        EventTory eventToryFromFile = dataFromFile.toModelType();
        EventTory typicalEventsEventTory = TypicalEvents.getTypicalEventTory();
        assertEquals(eventToryFromFile, typicalEventsEventTory);
    }

    @Test
    public void toModelType_invalidEventFile_throwsIllegalValueException() throws Exception {
        JsonSerializableEventTory dataFromFile = JsonUtil.readJsonFile(INVALID_EVENT_FILE,
                JsonSerializableEventTory.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateEvents_throwsIllegalValueException() throws Exception {
        JsonSerializableEventTory dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EVENT_FILE,
                JsonSerializableEventTory.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableEventTory.MESSAGE_DUPLICATE_EVENT,
                dataFromFile::toModelType);
    }

}
