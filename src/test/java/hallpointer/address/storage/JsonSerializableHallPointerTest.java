package hallpointer.address.storage;

import static hallpointer.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hallpointer.address.commons.exceptions.IllegalValueException;
import hallpointer.address.commons.util.JsonUtil;
import hallpointer.address.model.HallPointer;
import hallpointer.address.testutil.TypicalMembers;

public class JsonSerializableHallPointerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableHallPointerTest");
    private static final Path TYPICAL_MEMBERS_FILE = TEST_DATA_FOLDER.resolve("typicalMembersHallPointer.json");
    private static final Path INVALID_MEMBER_FILE = TEST_DATA_FOLDER.resolve("invalidMemberHallPointer.json");
    private static final Path DUPLICATE_MEMBER_FILE = TEST_DATA_FOLDER.resolve("duplicateMemberHallPointer.json");

    @Test
    public void toModelType_typicalMembersFile_success() throws Exception {
        JsonSerializableHallPointer dataFromFile = JsonUtil.readJsonFile(TYPICAL_MEMBERS_FILE,
                JsonSerializableHallPointer.class).get();
        HallPointer hallPointerFromFile = dataFromFile.toModelType();
        HallPointer typicalMembersHallPointer = TypicalMembers.getTypicalHallPointer();
        assertEquals(hallPointerFromFile, typicalMembersHallPointer);
    }

    @Test
    public void toModelType_invalidMemberFile_throwsIllegalValueException() throws Exception {
        JsonSerializableHallPointer dataFromFile = JsonUtil.readJsonFile(INVALID_MEMBER_FILE,
                JsonSerializableHallPointer.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateMembers_throwsIllegalValueException() throws Exception {
        JsonSerializableHallPointer dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MEMBER_FILE,
                JsonSerializableHallPointer.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableHallPointer.MESSAGE_DUPLICATE_MEMBER,
                dataFromFile::toModelType);
    }

}
