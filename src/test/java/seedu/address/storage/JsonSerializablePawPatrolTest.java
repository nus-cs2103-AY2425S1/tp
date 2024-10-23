package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.PawPatrol;
import seedu.address.testutil.TypicalOwners;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializablePawPatrolTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializablePawPatrolTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsPawPatrol.json");
    private static final Path TYPICAL_OWNERS_FILE = TEST_DATA_FOLDER.resolve("typicalOwnersPawPatrol.json");

    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonPawPatrol.json");
    private static final Path INVALID_OWNER_FILE = TEST_DATA_FOLDER.resolve("invalidOwnerPawPatrol.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonPawPatrol.json");
    private static final Path DUPLICATE_OWNER_FILE = TEST_DATA_FOLDER.resolve("duplicateOwnerPawPatrol.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializablePawPatrol dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializablePawPatrol.class).get();
        PawPatrol pawPatrolFromFile = dataFromFile.toModelType();
        PawPatrol typicalPersonsPawPatrol = TypicalPersons.getTypicalPawPatrol();
        assertEquals(pawPatrolFromFile, typicalPersonsPawPatrol);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePawPatrol dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializablePawPatrol.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializablePawPatrol dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializablePawPatrol.class).get();
        assertThrows(IllegalValueException.class, JsonSerializablePawPatrol.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalOwnersFile_success() throws Exception {
        JsonSerializablePawPatrol dataFromFile = JsonUtil.readJsonFile(TYPICAL_OWNERS_FILE,
                JsonSerializablePawPatrol.class).get();
        PawPatrol pawPatrolFromFile = dataFromFile.toModelType();
        PawPatrol typicalOwnersPawPatrol = TypicalOwners.getTypicalPawPatrol();
        assertEquals(pawPatrolFromFile, typicalOwnersPawPatrol);
    }

    @Test
    public void toModelType_invalidOwnerFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePawPatrol dataFromFile = JsonUtil.readJsonFile(INVALID_OWNER_FILE,
                JsonSerializablePawPatrol.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateOwners_throwsIllegalValueException() throws Exception {
        JsonSerializablePawPatrol dataFromFile = JsonUtil.readJsonFile(DUPLICATE_OWNER_FILE,
                JsonSerializablePawPatrol.class).get();
        assertThrows(IllegalValueException.class, JsonSerializablePawPatrol.MESSAGE_DUPLICATE_OWNER,
                dataFromFile::toModelType);
    }

}
