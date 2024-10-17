package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.log.AppointmentDate;
import seedu.address.model.log.Log;

public class JsonAdaptedLogTest {

    private static final AppointmentDate APPOINTMENT_DATE = new AppointmentDate(LocalDate.of(2024, 10, 19));
    private static final String ENTRY = "Doctor's Appointment";
    private static final Log LOG = new Log(APPOINTMENT_DATE, ENTRY);
    private static final String STORAGE_STRING = "19 Oct 2024|Doctor's Appointment";

    @Test
    public void toModelType_validLog_returnsLog() throws IllegalValueException {
        JsonAdaptedLog jsonAdaptedLog = new JsonAdaptedLog(LOG);
        Log parsedLog = jsonAdaptedLog.toModelType();
        assertEquals(LOG.getAppointmentDate(), parsedLog.getAppointmentDate());
        assertEquals(LOG.getEntry(), parsedLog.getEntry());
    }

    @Test
    public void fromStorageString_validString_returnsLog() {
        Log parsedLog = Log.fromStorageString(STORAGE_STRING);
        assertEquals(APPOINTMENT_DATE, parsedLog.getAppointmentDate());
        assertEquals(ENTRY, parsedLog.getEntry());
    }

    @Test
    public void fromStorageString_invalidFormat_throwsException() {
        String invalidString = "19 Oct 2024"; // Missing description
        assertThrows(IllegalArgumentException.class, () -> {
            Log.fromStorageString(invalidString);
        });
    }

    @Test
    public void toStorageString_validLog_returnsCorrectString() {
        String logString = LOG.toStorageString();
        assertEquals(STORAGE_STRING, logString);
    }

    @Test
    public void toModelType_missingEntry_throwsIllegalValueException() {
        JsonAdaptedLog jsonAdaptedLog = new JsonAdaptedLog(APPOINTMENT_DATE.toString(), null);
        assertThrows(IllegalValueException.class, jsonAdaptedLog::toModelType);
    }

    @Test
    public void toModelType_invalidEntry_throwsIllegalValueException() {
        String invalidEntry = ""; // Assuming empty string is invalid
        JsonAdaptedLog jsonAdaptedLog = new JsonAdaptedLog(APPOINTMENT_DATE.toString(), invalidEntry);
        assertThrows(IllegalValueException.class, jsonAdaptedLog::toModelType);
    }

    @Test
    public void toModelType_invalidDateFormat_throwsIllegalValueException() {
        String invalidDate = "2024-10-19"; // Wrong date format
        JsonAdaptedLog jsonAdaptedLog = new JsonAdaptedLog(invalidDate, ENTRY);
        assertThrows(IllegalValueException.class, jsonAdaptedLog::toModelType);
    }

    @Test
    public void toModelType_validDateString_convertsCorrectly() throws IllegalValueException {
        String validDate = "19 Oct 2024";
        JsonAdaptedLog jsonAdaptedLog = new JsonAdaptedLog(validDate, ENTRY);
        Log parsedLog = jsonAdaptedLog.toModelType();
        assertEquals(APPOINTMENT_DATE, parsedLog.getAppointmentDate());
        assertEquals(ENTRY, parsedLog.getEntry());
    }
}
