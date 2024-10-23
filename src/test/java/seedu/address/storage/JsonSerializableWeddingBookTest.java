package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.WeddingBook;
import seedu.address.testutil.TypicalWeddings;

public class JsonSerializableWeddingBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableWeddingBookTest");
    private static final Path TYPICAL_WEDDINGS_FILE = TEST_DATA_FOLDER.resolve("typicalWeddingBook.json");
    private static final Path INVALID_WEDDING_FILE = TEST_DATA_FOLDER.resolve("invalidWeddingBook.json");
    private static final Path DUPLICATE_WEDDING_FILE = TEST_DATA_FOLDER.resolve("duplicateWeddingBook.json");

    @Test
    public void toModelType_typicalWeddingsFile_success() throws Exception {
        JsonSerializableWeddingBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_WEDDINGS_FILE,
                JsonSerializableWeddingBook.class).get();
        WeddingBook weddingBookFromFile = dataFromFile.toModelType();
        WeddingBook typicalWeddingBook = TypicalWeddings.getTypicalWeddingBook();
        assertEquals(weddingBookFromFile, typicalWeddingBook);
    }

    @Test
    public void toModelType_invalidWeddingFile_throwsIllegalValueException() throws Exception {
        JsonSerializableWeddingBook dataFromFile = JsonUtil.readJsonFile(INVALID_WEDDING_FILE,
                JsonSerializableWeddingBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateWeddings_throwsIllegalValueException() throws Exception {
        JsonSerializableWeddingBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_WEDDING_FILE,
                JsonSerializableWeddingBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableWeddingBook.MESSAGE_DUPLICATE_WEDDING,
                dataFromFile::toModelType);
    }

}
