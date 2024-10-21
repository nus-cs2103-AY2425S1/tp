package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TutUtil.TUTORIAL_CLASS;
import static seedu.address.testutil.TutUtil.TUT_NAME;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.tut.TutName;
import seedu.address.model.tut.Tutorial;
import seedu.address.model.tut.TutorialList;

public class JsonTutorialStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTutorialStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTutorials_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTutorials(null));
    }

    private java.util.Optional<TutorialList> readTutorials(String filePath) throws Exception {
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
        Tutorial tutorialSample = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_CLASS);
        ArrayList<Tutorial> tutorials = new ArrayList<>();
        tutorials.add(tutorialSample);
        TutorialList originalTutorials = new TutorialList(tutorials);


        // Step 2: Save the tutorial sample to the file
        jsonTutorialStorage.saveTutorials(originalTutorials, filePath);

        // Step 3: Verify that the file exists after saving
        assertTrue(Files.exists(filePath), "The file should exist after saving.");

        // Step 4: Optionally, check if the file has content written (size > 0)
        assertTrue(Files.size(filePath) > 0, "The file should not be empty after saving.");

        Files.deleteIfExists(filePath);
    }


    @Test
    public void saveTutorials_nullTutorialList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTutorials(null, "SomeFile.json"));
    }

    /**
     * Saves {@code tutorialList} at the specified {@code filePath}.
     */
    private void saveTutorials(TutorialList tutorialList, String filePath) {
        try {
            new JsonTutorialStorage(Paths.get(filePath))
                    .saveTutorials(tutorialList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }
    @Test
    public void saveTutorials_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTutorials(new TutorialList(), null));
    }
}
