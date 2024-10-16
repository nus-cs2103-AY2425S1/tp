package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TutUtil.TUTORIAL_CLASS;
import static seedu.address.testutil.TutUtil.TUT_01;
import static seedu.address.testutil.TutUtil.TUT_NAME;
import static seedu.address.testutil.TutUtil.TUT_SAMPLE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.tut.Tut;

public class JsonTutorialStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTutorialStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTutorials_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTutorials(null));
    }

    private Optional<List<Tut>> readTutorials(String filePath) throws Exception {
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

    //    @Test
    //    public void readTutorials_validFile_success() throws Exception {
    //        Path filePath = Paths.get("testtutorials.json");
    //        JsonTutorialStorage jsonTutorialStorage = new JsonTutorialStorage(filePath);
    //        // Create a tutorial sample and save it
    //        Tut tutSample = new Tut(TUT_NAME, TUT_01);
    //        List<Tut> originalTutorials = List.of(tutSample);
    //        jsonTutorialStorage.saveTutorials(originalTutorials, filePath);
    //
    //        // Read back the tutorials
    //        Optional<List<Tut>> retrievedTutorials = jsonTutorialStorage.readTutorials();
    //        assertTrue(retrievedTutorials.isPresent());
    //        assertTrue(retrievedTutorials.get().contains(tutSample));
    //
    //        Files.deleteIfExists(filePath);
    //    }

    @Test
    public void readTutorials_illegalValueException_throwsDataLoadingException() throws Exception {
        Path filePath = Paths.get("invalidtutorials.json");

        // Create a JSON file with invalid data that causes IllegalValueException
        String invalidJsonContent = "[ { \"tutName\": \"\", \"tutorialClassName\": { \"value\" : \"1001\" },"
                + " \"students\": [], \"tutDates\": [] } ]";

        // Write the invalid JSON content to the file
        Files.write(filePath, invalidJsonContent.getBytes());

        // Instantiate JsonTutorialStorage
        JsonTutorialStorage jsonTutorialStorage = new JsonTutorialStorage(filePath);

        // Ensure that the readTutorials method throws DataLoadingException when the data is invalid
        assertThrows(DataLoadingException.class, () -> jsonTutorialStorage.readTutorials());

        // Clean up by deleting the file after the test
        Files.deleteIfExists(filePath);
    }

    @Test
    public void saveTutorials_saveDataToFile_success() throws Exception {
        Path filePath = Paths.get("testtutorials.json");
        JsonTutorialStorage jsonTutorialStorage = new JsonTutorialStorage(filePath);

        // Create a tutorial sample
        Tut tutSample = new Tut(TUT_NAME, TUT_01);
        List<Tut> originalTutorials = List.of(tutSample);

        // Save the tutorial sample to the file
        jsonTutorialStorage.saveTutorials(originalTutorials, filePath);

        // Verify that the file exists after saving
        assertTrue(Files.exists(filePath), "The file should exist after saving.");

        // Optionally, check if the file has content written (size > 0)
        assertTrue(Files.size(filePath) > 0, "The file should not be empty after saving.");

        Files.deleteIfExists(filePath);
    }

    @Test
    public void saveTutorials_nullTutorialList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTutorials(null, "SomeFile.json"));
    }

    @Test
    public void saveTutorials_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTutorials(List.of(TUT_SAMPLE), null));
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

    //    @Test
    //    public void readTutorials_defaultPath_success() throws Exception {
    //        Path filePath = Paths.get("testtutorials.json");
    //        // Check if the file exists before the test, and delete it if it does
    //        JsonTutorialStorage jsonTutorialStorage = new JsonTutorialStorage(filePath);
    //        // Create a tutorial sample and save it
    //        Tut tutSample = new Tut(TUT_NAME, TUTORIAL_CLASS);
    //        List<Tut> originalTutorials = List.of(tutSample);
    //        jsonTutorialStorage.saveTutorials(originalTutorials);
    //
    //        // Read back the tutorials using the default path
    //        Optional<List<Tut>> retrievedTutorials = jsonTutorialStorage.readTutorials();
    //        assertTrue(retrievedTutorials.isPresent());
    //        assertTrue(retrievedTutorials.get().contains(tutSample));
    //
    //        Files.deleteIfExists(filePath);
    //    }

    @Test
    public void saveTutorials_defaultPath_success() throws Exception {
        Path filePath = Paths.get("testtutorials.json");
        JsonTutorialStorage jsonTutorialStorage = new JsonTutorialStorage(filePath);

        // Create a tutorial sample
        Tut tutSample = new Tut(TUT_NAME, TUTORIAL_CLASS);
        List<Tut> originalTutorials = List.of(tutSample);

        // Save the tutorial sample to the file using the default save method
        jsonTutorialStorage.saveTutorials(originalTutorials);

        // Verify that the file exists after saving
        assertTrue(Files.exists(filePath), "The file should exist after saving.");

        // Optionally, check if the file has content written (size > 0)
        assertTrue(Files.size(filePath) > 0, "The file should not be empty after saving.");

        Files.deleteIfExists(filePath);
    }
}
