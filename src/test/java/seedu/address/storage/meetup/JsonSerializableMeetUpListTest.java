package seedu.address.storage.meetup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.MeetUpList;
import seedu.address.testutil.meetup.TypicalMeetUps;

public class JsonSerializableMeetUpListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMeetUpListTest");
    private static final Path TYPICAL_MEETUPS_FILE = TEST_DATA_FOLDER.resolve("typicalMeetUpsMeetUpList.json");
    private static final Path INVALID_MEETUPS_FILE = TEST_DATA_FOLDER.resolve("invalidMeetUpList.json");
    private static final Path DUPLICATE_MEETUPS_FILE = TEST_DATA_FOLDER.resolve("duplicateMeetUpMeetUpList.json");

    @Test
    public void toModelType_typicalMeetUpFile_success() throws Exception {
        JsonSerializableMeetUpList dataFromFile = JsonUtil.readJsonFile(TYPICAL_MEETUPS_FILE,
                JsonSerializableMeetUpList.class).get();
        MeetUpList meetUpListFromFile = dataFromFile.toModelType();
        MeetUpList typicalMeetUpsMeetUpList = TypicalMeetUps.getTypicalMeetUpList();
        assertEquals(meetUpListFromFile, typicalMeetUpsMeetUpList);
    }

    @Test
    public void toModelType_invalidMeetUpFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMeetUpList dataFromFile = JsonUtil.readJsonFile(INVALID_MEETUPS_FILE,
                JsonSerializableMeetUpList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateMeetUps_throwsIllegalValueException() throws Exception {
        JsonSerializableMeetUpList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MEETUPS_FILE,
                JsonSerializableMeetUpList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMeetUpList.MESSAGE_DUPLICATE_MEETUP,
                dataFromFile::toModelType);
    }

}
