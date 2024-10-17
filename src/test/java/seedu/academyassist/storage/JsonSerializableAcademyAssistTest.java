package seedu.academyassist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.academyassist.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.academyassist.commons.exceptions.IllegalValueException;
import seedu.academyassist.commons.util.JsonUtil;
import seedu.academyassist.model.AcademyAssist;
import seedu.academyassist.testutil.TypicalPersons;

public class JsonSerializableAcademyAssistTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAcademyAssistTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAcademyAssist.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAcademyAssist.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAcademyAssist.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAcademyAssist dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAcademyAssist.class).get();
        AcademyAssist academyAssistFromFile = dataFromFile.toModelType();
        AcademyAssist typicalPersonsAcademyAssist = TypicalPersons.getTypicalAcademyAssist();
        assertEquals(academyAssistFromFile, typicalPersonsAcademyAssist);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAcademyAssist dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAcademyAssist.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAcademyAssist dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAcademyAssist.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAcademyAssist.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
