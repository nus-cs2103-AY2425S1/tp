package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ClinicConnectSystem;
import seedu.address.testutil.TypicalPatients;

public class JsonSerializableClinicConnectSystemTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableClinicConnectSystemTest");
    private static final Path TYPICAL_PATIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalPatientsClinicConnectSystem.json");
    private static final Path INVALID_PATIENT_FILE = TEST_DATA_FOLDER.resolve("invalidPatientClinicConnectSystem.json");
    private static final Path DUPLICATE_PATIENT_FILE = TEST_DATA_FOLDER.resolve("duplicatePatientClinicConnectSystem.json");

    @Test
    public void toModelType_typicalPatientsFile_success() throws Exception {
        JsonSerializableClinicConnectSystem dataFromFile = JsonUtil.readJsonFile(TYPICAL_PATIENTS_FILE,
                JsonSerializableClinicConnectSystem.class).get();
        ClinicConnectSystem clinicConnectSystemFromFile = dataFromFile.toModelType();
        ClinicConnectSystem typicalPatientsClinicConnectSystem = TypicalPatients.getTypicalClinicConnectSystem();
        assertEquals(clinicConnectSystemFromFile, typicalPatientsClinicConnectSystem);
    }

    @Test
    public void toModelType_invalidPatientFile_throwsIllegalValueException() throws Exception {
        JsonSerializableClinicConnectSystem dataFromFile = JsonUtil.readJsonFile(INVALID_PATIENT_FILE,
                JsonSerializableClinicConnectSystem.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePatients_throwsIllegalValueException() throws Exception {
        JsonSerializableClinicConnectSystem dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PATIENT_FILE,
                JsonSerializableClinicConnectSystem.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableClinicConnectSystem.MESSAGE_DUPLICATE_PATIENT,
                dataFromFile::toModelType);
    }

}
