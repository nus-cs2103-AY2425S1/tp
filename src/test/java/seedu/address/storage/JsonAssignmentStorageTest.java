package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentList;

public class JsonAssignmentStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAssignmentStorageTest");
    private static final Path VALID_ASSIGNMENT_FILE = TEST_DATA_FOLDER.resolve("validAssignments.json");
    private static final Path INVALID_ASSIGNMENT_FILE = TEST_DATA_FOLDER.resolve("invalidAssignments.json");
    private static final Path TEMP_ASSIGNMENT_FILE = TEST_DATA_FOLDER.resolve("tempAssignments.json");

    private JsonAssignmentStorage storage;

    @BeforeEach
    public void setUp() {
        storage = new JsonAssignmentStorage(TEMP_ASSIGNMENT_FILE);
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up by deleting the temporary test file if it exists
        java.nio.file.Files.deleteIfExists(TEMP_ASSIGNMENT_FILE);
    }

    @Test
    public void readAssignments_validFile_success() throws Exception {
        JsonAssignmentStorage validStorage = new JsonAssignmentStorage(VALID_ASSIGNMENT_FILE);
        Optional<AssignmentList> result = validStorage.readAssignments();
        AssignmentList expectedList = getSampleAssignmentList();

        assertEquals(expectedList, result.orElseThrow());
    }

    @Test
    public void readAssignments_invalidFile_throwsDataLoadingException() {
        JsonAssignmentStorage invalidStorage = new JsonAssignmentStorage(INVALID_ASSIGNMENT_FILE);
        assertThrows(DataLoadingException.class, invalidStorage::readAssignments);
    }

    @Test
    public void saveAssignments_validAssignmentList_success() throws Exception {
        AssignmentList assignmentList = getSampleAssignmentList();
        storage.saveAssignments(assignmentList);

        Optional<AssignmentList> result = storage.readAssignments(TEMP_ASSIGNMENT_FILE);
        assertEquals(assignmentList, result.orElseThrow());
    }

    @Test
    public void saveAssignments_nullAssignmentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> storage.saveAssignments(null));
    }

    @Test
    public void readAssignments_nonExistentFile_returnsEmptyOptional() throws Exception {
        Path nonExistentPath = TEST_DATA_FOLDER.resolve("nonExistent.json");
        JsonAssignmentStorage nonExistentStorage = new JsonAssignmentStorage(nonExistentPath);

        Optional<AssignmentList> result = nonExistentStorage.readAssignments();
        assertEquals(Optional.empty(), result);
    }

    // Helper method to create a sample AssignmentList
    private AssignmentList getSampleAssignmentList() {
        AssignmentList assignmentList = new AssignmentList();
        assignmentList.addAssignment(new Assignment("Math Assignment", LocalDateTime.parse("2024-10-31T23:59")));
        assignmentList.addAssignment(new Assignment("Science Project", LocalDateTime.parse("2024-11-15T17:00")));
        return assignmentList;
    }
}
