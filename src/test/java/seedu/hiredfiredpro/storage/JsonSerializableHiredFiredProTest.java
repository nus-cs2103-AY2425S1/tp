package seedu.hiredfiredpro.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.hiredfiredpro.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.hiredfiredpro.commons.exceptions.IllegalValueException;
import seedu.hiredfiredpro.commons.util.JsonUtil;
import seedu.hiredfiredpro.model.HiredFiredPro;
import seedu.hiredfiredpro.testutil.TypicalPersons;

public class JsonSerializableHiredFiredProTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableHiredFiredProTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsHiredFiredPro.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonHiredFiredPro.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonHiredFiredPro.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableHiredFiredPro dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableHiredFiredPro.class).get();
        HiredFiredPro hiredFiredProFromFile = dataFromFile.toModelType();
        HiredFiredPro typicalPersonsHiredFiredPro = TypicalPersons.getTypicalHiredFiredPro();
        assertEquals(hiredFiredProFromFile, typicalPersonsHiredFiredPro);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableHiredFiredPro dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableHiredFiredPro.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableHiredFiredPro dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableHiredFiredPro.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableHiredFiredPro.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
