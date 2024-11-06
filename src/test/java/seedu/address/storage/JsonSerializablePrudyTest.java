package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Prudy;
import seedu.address.testutil.TypicalClients;

public class JsonSerializablePrudyTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializablePrudyTest");
    private static final Path TYPICAL_CLIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalClientsPrudy.json");
    private static final Path INVALID_CLIENT_FILE = TEST_DATA_FOLDER.resolve("invalidClientPrudy.json");
    private static final Path DUPLICATE_CLIENT_FILE = TEST_DATA_FOLDER.resolve("duplicateClientPrudy.json");

    @Test
    public void toModelType_typicalClientsFile_success() throws Exception {
        JsonSerializablePrudy dataFromFile = JsonUtil.readJsonFile(TYPICAL_CLIENTS_FILE,
                JsonSerializablePrudy.class).get();
        Prudy prudyFromFile = dataFromFile.toModelType();
        Prudy typicalClientsPrudy = TypicalClients.getTypicalPrudy();
        assertEquals(prudyFromFile, typicalClientsPrudy);
    }

    @Test
    public void toModelType_invalidClientFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePrudy dataFromFile = JsonUtil.readJsonFile(INVALID_CLIENT_FILE,
                JsonSerializablePrudy.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateClients_throwsIllegalValueException() throws Exception {
        JsonSerializablePrudy dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CLIENT_FILE,
                JsonSerializablePrudy.class).get();
        assertThrows(IllegalValueException.class, JsonSerializablePrudy.MESSAGE_DUPLICATE_CLIENT,
                dataFromFile::toModelType);
    }

}
