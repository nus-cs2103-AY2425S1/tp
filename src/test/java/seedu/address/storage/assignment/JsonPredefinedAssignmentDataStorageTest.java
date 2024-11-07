package seedu.address.storage.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataLoadingException;

class JsonPredefinedAssignmentDataStorageTest {

    @Test
    void getAssignmentFilePathTest() {
        Path expectedPath = Paths.get("data");
        JsonPredefinedAssignmentDataStorage test = new JsonPredefinedAssignmentDataStorage(expectedPath);
        assertEquals(test.getAssignmentFilePath(), expectedPath);
    }


    @Test
    void testReadAssignment() {
        JsonPredefinedAssignmentDataStorage test = new JsonPredefinedAssignmentDataStorage(
                Paths.get(
                        "src", "test", "data",
                        "AssignmentTest", "PredefinedAssignmentDuplicate.json"));
        assertThrows(DataLoadingException.class, test::readAssignment);
    }
}
