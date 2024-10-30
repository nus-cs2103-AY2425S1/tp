package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.HOON;
import static seedu.address.testutil.TypicalPatients.IDA;
import static seedu.address.testutil.TypicalPatients.getTypicalClinicConnectSystem;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ClinicConnectSystem;
import seedu.address.model.ReadOnlyClinicConnectSystem;

public class JsonClinicConnectSystemStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonClinicConnectSystemStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readClinicConnectSystem_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readClinicConnectSystem(null));
    }

    private java.util.Optional<ReadOnlyClinicConnectSystem> readClinicConnectSystem(String filePath) throws Exception {
        return new JsonClinicConnectSystemStorage(Paths.get(filePath)).readClinicConnectSystem(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readClinicConnectSystem("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readClinicConnectSystem("notJsonFormatClinicConnectSystem.json"));
    }

    @Test
    public void readClinicConnectSystem_invalidPatientClinicConnectSystem_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readClinicConnectSystem("invalidPatientClinicConnectSystem.json"));
    }

    @Test
    public void readClinicConnectSystem_invalidAndValidPatientClinicConnectSystem_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readClinicConnectSystem("invalidAndValidPatientClinicConnectSystem.json"));
    }

    @Test
    public void readAndSaveClinicConnectSystem_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempClinicConnectSystem.json");
        ClinicConnectSystem original = getTypicalClinicConnectSystem();
        JsonClinicConnectSystemStorage jsonClinicConnectSystemStorage = new JsonClinicConnectSystemStorage(filePath);

        // Save in new file and read back
        jsonClinicConnectSystemStorage.saveClinicConnectSystem(original, filePath);
        ReadOnlyClinicConnectSystem readBack = jsonClinicConnectSystemStorage.readClinicConnectSystem(filePath).get();
        assertEquals(original, new ClinicConnectSystem(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPatient(HOON);
        original.removePatient(ALICE);
        jsonClinicConnectSystemStorage.saveClinicConnectSystem(original, filePath);
        readBack = jsonClinicConnectSystemStorage.readClinicConnectSystem(filePath).get();
        assertEquals(original, new ClinicConnectSystem(readBack));

        // Save and read without specifying file path
        original.addPatient(IDA);
        jsonClinicConnectSystemStorage.saveClinicConnectSystem(original); // file path not specified
        readBack = jsonClinicConnectSystemStorage.readClinicConnectSystem().get(); // file path not specified
        assertEquals(original, new ClinicConnectSystem(readBack));

    }

    @Test
    public void saveClinicConnectSystem_nullClinicConnectSystem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveClinicConnectSystem(null, "SomeFile.json"));
    }

    /**
     * Saves {@code clinicConnectSystem} at the specified {@code filePath}.
     */
    private void saveClinicConnectSystem(ReadOnlyClinicConnectSystem clinicConnectSystem, String filePath) {
        try {
            new JsonClinicConnectSystemStorage(Paths.get(filePath))
                    .saveClinicConnectSystem(clinicConnectSystem, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveClinicConnectSystem_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveClinicConnectSystem(new ClinicConnectSystem(), null));
    }
}
