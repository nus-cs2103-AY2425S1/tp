package seedu.edulog.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.edulog.testutil.Assert.assertThrows;
import static seedu.edulog.testutil.TypicalEdulog.getTypicalEduLog;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.edulog.commons.exceptions.IllegalValueException;
import seedu.edulog.commons.util.JsonUtil;
import seedu.edulog.model.EduLog;

public class JsonSerializableEduLogTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableEduLogTest");
    private static final Path TYPICAL_EDULOG_FILE = TEST_DATA_FOLDER.resolve("typicalEduLog.json");
    private static final Path INVALID_EDULOG_FILE = TEST_DATA_FOLDER.resolve("invalidEduLog.json");
    private static final Path DUPLICATE_EDULOG_FILE = TEST_DATA_FOLDER.resolve("duplicateEduLog.json");

    @Test
    public void toModelType_typicalEdulogFile_success() throws Exception {
        JsonSerializableEduLog dataFromFile = JsonUtil.readJsonFile(TYPICAL_EDULOG_FILE,
                JsonSerializableEduLog.class).get();
        EduLog eduLogFromFile = dataFromFile.toModelType();
        EduLog typicalStudentsEduLog = getTypicalEduLog();
        assertEquals(eduLogFromFile, typicalStudentsEduLog);
    }

    @Test
    public void toModelType_invalidEdulogFile_throwsIllegalValueException() throws Exception {
        JsonSerializableEduLog dataFromFile = JsonUtil.readJsonFile(INVALID_EDULOG_FILE,
                JsonSerializableEduLog.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateItems_throwsIllegalValueException() throws Exception {
        JsonSerializableEduLog dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EDULOG_FILE,
                JsonSerializableEduLog.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableEduLog.MESSAGE_DUPLICATE_STUDENT,
                dataFromFile::toModelType);
    }

}
