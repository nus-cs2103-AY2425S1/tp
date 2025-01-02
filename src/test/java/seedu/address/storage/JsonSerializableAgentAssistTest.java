package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AgentAssist;
import seedu.address.testutil.TypicalClients;

public class JsonSerializableAgentAssistTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAgentAssistTest");
    private static final Path TYPICAL_CLIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalClientsAgentAssist.json");
    private static final Path INVALID_CLIENT_FILE = TEST_DATA_FOLDER.resolve("invalidClientAgentAssist.json");
    private static final Path DUPLICATE_CLIENT_FILE = TEST_DATA_FOLDER.resolve("duplicateClientAgentAssist.json");

    @Test
    public void toModelType_typicalClientsFile_success() throws Exception {
        JsonSerializableAgentAssist dataFromFile = JsonUtil.readJsonFile(TYPICAL_CLIENTS_FILE,
                JsonSerializableAgentAssist.class).get();
        AgentAssist agentAssistFromFile = dataFromFile.toModelType();
        AgentAssist typicalClientsAgentAssist = TypicalClients.getTypicalAgentAssist();
        System.out.println("Agent Assist: " + agentAssistFromFile);
        assertEquals(agentAssistFromFile, typicalClientsAgentAssist);
    }

    @Test
    public void toModelType_invalidClientFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAgentAssist dataFromFile = JsonUtil.readJsonFile(INVALID_CLIENT_FILE,
                JsonSerializableAgentAssist.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateClients_throwsIllegalValueException() throws Exception {
        JsonSerializableAgentAssist dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CLIENT_FILE,
                JsonSerializableAgentAssist.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAgentAssist.MESSAGE_DUPLICATE_CLIENT,
                dataFromFile::toModelType);
    }

}
