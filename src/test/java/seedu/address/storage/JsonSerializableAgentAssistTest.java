package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AgentAssist;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableAgentAssistTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAgentAssistTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAgentAssist.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAgentAssist.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAgentAssist.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAgentAssist dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAgentAssist.class).get();
        AgentAssist agentAssistFromFile = dataFromFile.toModelType();
        AgentAssist typicalPersonsAgentAssist = TypicalPersons.getTypicalAgentAssist();
        System.out.println("Address book: " + agentAssistFromFile);
        assertEquals(agentAssistFromFile, typicalPersonsAgentAssist);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAgentAssist dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAgentAssist.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAgentAssist dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAgentAssist.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAgentAssist.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
