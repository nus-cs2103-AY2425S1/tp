package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TutUtil.TUTORIAL_CLASS;
import static seedu.address.testutil.TutUtil.TUT_NAME;
import static seedu.address.testutil.TutUtil.TUT_SAMPLE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.tut.Tut;

public class JsonTutorialStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTutorialStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTutorials_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTutorials(null));
    }

    private java.util.Optional<List<Tut>> readTutorials(String filePath) throws Exception {
        return new JsonTutorialStorage(Paths.get(filePath)).readTutorials(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTutorials("NonExistentFile.json").isPresent());
    }
    @Test
    public void saveTutorials_saveDataToFile_success() throws Exception {
        Path filePath = Paths.get("testtutorials.json");
        JsonTutorialStorage jsonTutorialStorage = new JsonTutorialStorage(filePath);

        // Step 1: Create a tutorial sample
        Tut tutSample = new Tut(TUT_NAME, TUTORIAL_CLASS);
        List<Tut> originalTutorials = List.of(tutSample);

        // Step 2: Save the tutorial sample to the file
        jsonTutorialStorage.saveTutorials(originalTutorials, filePath);

        // Step 3: Verify that the file exists after saving
        assertTrue(Files.exists(filePath), "The file should exist after saving.");

        // Step 4: Optionally, check if the file has content written (size > 0)
        assertTrue(Files.size(filePath) > 0, "The file should not be empty after saving.");
    }


    @Test
    public void saveTutorials_nullTutorialList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTutorials(null, "SomeFile.json"));
    }

    /**
     * Saves {@code tutorialList} at the specified {@code filePath}.
     */
    private void saveTutorials(List<Tut> tutorialList, String filePath) {
        try {
            new JsonTutorialStorage(Paths.get(filePath))
                    .saveTutorials(tutorialList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }
    @Test
    public void saveTutorials_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTutorials(List.of(TUT_SAMPLE), null));
    }
}
