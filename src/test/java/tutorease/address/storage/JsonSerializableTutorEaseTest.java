package tutorease.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutorease.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import tutorease.address.commons.exceptions.IllegalValueException;
import tutorease.address.commons.util.JsonUtil;
import tutorease.address.model.TutorEase;
import tutorease.address.testutil.TypicalPersons;

public class JsonSerializableTutorEaseTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTutorEaseTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsTutorEase.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonTutorEase.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonTutorEase.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableTutorEase dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableTutorEase.class).get();
        TutorEase tutorEaseFromFile = dataFromFile.toModelType();
        TutorEase typicalPersonsTutorEase = TypicalPersons.getTypicalTutorEase();
        assertEquals(tutorEaseFromFile, typicalPersonsTutorEase);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorEase dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableTutorEase.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorEase dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableTutorEase.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableTutorEase.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
