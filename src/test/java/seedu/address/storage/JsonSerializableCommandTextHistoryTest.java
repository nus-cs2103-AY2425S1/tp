package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.CommandTextHistory;
import seedu.address.testutil.TypicalCommandTextHistory;

public class JsonSerializableCommandTextHistoryTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableCommandTextHistoryTest");
    private static final Path TYPICAL_FILE = TEST_DATA_FOLDER.resolve("typicalCommandTextHistory.json");

    @Test
    public void toModelType_typicalFile_success() throws Exception {
        JsonSerializableCommandTextHistory dataFromFile = JsonUtil.readJsonFile(TYPICAL_FILE,
                JsonSerializableCommandTextHistory.class).get();
        CommandTextHistory commandTextHistoryFromFile = dataFromFile.toModelType();
        CommandTextHistory typicalCommandTextHistory = TypicalCommandTextHistory.getTypicalCommandTextHistory();
        assertEquals(commandTextHistoryFromFile.getCommandTextList(), typicalCommandTextHistory.getCommandTextList());
    }

}
