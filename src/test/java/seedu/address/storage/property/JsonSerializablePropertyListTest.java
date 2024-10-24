package seedu.address.storage.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.PropertyList;
import seedu.address.testutil.meetup.TypicalPropertys;

public class JsonSerializablePropertyListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializablePropertyListTest");
    private static final Path TYPICAL_MEETUPS_FILE = TEST_DATA_FOLDER.resolve("typicalPropertysPropertyList.json");
    private static final Path INVALID_MEETUPS_FILE = TEST_DATA_FOLDER.resolve("invalidPropertyList.json");
    private static final Path DUPLICATE_MEETUPS_FILE = TEST_DATA_FOLDER.resolve("duplicatePropertyPropertyList.json");

    @Test
    public void toModelType_typicalPropertyFile_success() throws Exception {
        JsonSerializablePropertyList dataFromFile = JsonUtil.readJsonFile(TYPICAL_MEETUPS_FILE,
                JsonSerializablePropertyList.class).get();
        PropertyList propertyListFromFile = dataFromFile.toModelType();
        PropertyList typicalPropertysPropertyList = TypicalPropertys.getTypicalPropertyList();
        assertEquals(propertyListFromFile, typicalPropertysPropertyList);
    }

    @Test
    public void toModelType_invalidPropertyFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePropertyList dataFromFile = JsonUtil.readJsonFile(INVALID_MEETUPS_FILE,
                JsonSerializablePropertyList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePropertys_throwsIllegalValueException() throws Exception {
        JsonSerializablePropertyList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MEETUPS_FILE,
                JsonSerializablePropertyList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializablePropertyList.MESSAGE_DUPLICATE_MEETUP,
                dataFromFile::toModelType);
    }

}
