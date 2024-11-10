package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.healthservice.HealthService;
import seedu.address.model.patient.Appt;

public class JsonAdaptedApptTest {


    @Test
    public void toModelType_validApptDetails_success() throws Exception {
        Appt appt = new Appt(LocalDateTime.parse("2024-12-12 13:00", Appt.STRICT_FORMATTER),
            new HealthService("CONSULT"));
        JsonAdaptedAppt jsonAdaptedAppt = new JsonAdaptedAppt(appt);
        assertEquals(appt, jsonAdaptedAppt.toModelType());
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        JsonAdaptedAppt jsonAdaptedAppt = new JsonAdaptedAppt("2024-12-32 13:00", "CONSULT");
        assertThrows(IllegalValueException.class, jsonAdaptedAppt::toModelType);
    }

    @Test
    public void toModelType_invalidHealthService_throwsIllegalValueException() {
        JsonAdaptedAppt jsonAdaptedAppt = new JsonAdaptedAppt("2024-12-12 13:00", "CONSULT1");
        assertThrows(IllegalValueException.class, jsonAdaptedAppt::toModelType);
    }

    @Test
    public void toModelType_nullDateTime_throwsNullPointerException() {
        JsonAdaptedAppt jsonAdaptedAppt = new JsonAdaptedAppt(null, "CONSULT");
        assertThrows(NullPointerException.class, jsonAdaptedAppt::toModelType);
    }

    @Test
    public void toModelType_nullHealthService_throwsNullPointerException() {
        JsonAdaptedAppt jsonAdaptedAppt = new JsonAdaptedAppt("2024-12-12 13:00", null);
        assertThrows(NullPointerException.class, jsonAdaptedAppt::toModelType);
    }
}
