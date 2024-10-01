package keycontacts.storage;

import static keycontacts.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import keycontacts.commons.exceptions.IllegalValueException;
import keycontacts.commons.util.JsonUtil;
import keycontacts.model.StudentDirectory;
import keycontacts.testutil.TypicalStudents;

public class JsonSerializableStudentDirectoryTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableStudentDirectoryTest");
    private static final Path TYPICAL_STUDENTS_FILE =
            TEST_DATA_FOLDER.resolve("typicalStudentsStudentDirectory.json");
    private static final Path INVALID_STUDENT_FILE =
            TEST_DATA_FOLDER.resolve("invalidStudentStudentDirectory.json");
    private static final Path DUPLICATE_STUDENT_FILE =
            TEST_DATA_FOLDER.resolve("duplicateStudentStudentDirectory.json");

    @Test
    public void toModelType_typicalStudentsFile_success() throws Exception {
        JsonSerializableStudentDirectory dataFromFile = JsonUtil.readJsonFile(TYPICAL_STUDENTS_FILE,
                JsonSerializableStudentDirectory.class).get();
        StudentDirectory studentDirectoryFromFile = dataFromFile.toModelType();
        StudentDirectory typicalStudentsStudentDirectory = TypicalStudents.getTypicalStudentDirectory();
        assertEquals(studentDirectoryFromFile, typicalStudentsStudentDirectory);
    }

    @Test
    public void toModelType_invalidStudentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableStudentDirectory dataFromFile = JsonUtil.readJsonFile(INVALID_STUDENT_FILE,
                JsonSerializableStudentDirectory.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateStudents_throwsIllegalValueException() throws Exception {
        JsonSerializableStudentDirectory dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDENT_FILE,
                JsonSerializableStudentDirectory.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableStudentDirectory.MESSAGE_DUPLICATE_STUDENT,
                dataFromFile::toModelType);
    }

}
