package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AppointmentBook;
import seedu.address.testutil.TypicalAppointments;

public class JsonSerializableAppointmentBookTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableAppointmentBookTest");
    private static final Path TYPICAL_APPOINTMENTS_FILE =
            TEST_DATA_FOLDER.resolve("typicalAppointmentsAppointmentBook.json");
    private static final Path INVALID_APPOINTMENT_FILE =
            TEST_DATA_FOLDER.resolve("invalidAppointmentAppointmentBook.json");
    private static final Path DUPLICATE_APPOINTMENT_FILE =
            TEST_DATA_FOLDER.resolve("duplicateAppointmentAppointmentBook.json");

    @Test
    void toModelType_typicalAppointmentsFile_success() throws Exception {
        JsonSerializableAppointmentBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_APPOINTMENTS_FILE,
                JsonSerializableAppointmentBook.class).get();
        AppointmentBook appointmentBookFromFile = dataFromFile.toModelType();
        AppointmentBook typicalAppointmentsAppointmentBook = TypicalAppointments.getTypicalAppointmentBook();
        assertEquals(appointmentBookFromFile, typicalAppointmentsAppointmentBook);
    }

    @Test
    void toModelType_invalidAppointmentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAppointmentBook dataFromFile = JsonUtil.readJsonFile(INVALID_APPOINTMENT_FILE,
                JsonSerializableAppointmentBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    void toModelType_duplicateAppointments_throwsIllegalValueException() throws Exception {
        JsonSerializableAppointmentBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_APPOINTMENT_FILE,
                JsonSerializableAppointmentBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAppointmentBook.MESSAGE_DUPLICATE_APPOINTMENT,
                dataFromFile::toModelType);
    }

    @Test
    void toModelType_emptyAppointmentList_noExceptionThrown() throws IllegalValueException {
        JsonSerializableAppointmentBook jsonSerializableAppointmentBook =
                new JsonSerializableAppointmentBook(Collections.emptyList());
        AppointmentBook appointmentBook = jsonSerializableAppointmentBook.toModelType();
        assertEquals(0, appointmentBook.getAppointmentList().size());
    }
}

