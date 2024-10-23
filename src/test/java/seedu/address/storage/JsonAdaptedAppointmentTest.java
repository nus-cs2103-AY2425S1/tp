package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;

public class JsonAdaptedAppointmentTest {

    private static final String VALID_NAME = "Appointment Test";
    private static final String VALID_NRIC = VALID_NRIC_BOB.toString();
    private static final String VALID_START_TIME = LocalDateTime.of(2024, 10, 22, 12, 0).toString();
    private static final String VALID_END_TIME = LocalDateTime.of(2024, 10, 22, 12, 0).plusHours(1).toString();
    private static final boolean VALID_IS_COMPLETED = false;

    private static final String INVALID_NRIC = "INVALID_NRIC";
    private static final String INVALID_START_TIME = "INVALID_START_TIME";
    private static final String INVALID_END_TIME = "INVALID_END_TIME";

    @Test
    public void toModelType_validAppointment_returnsAppointment() throws Exception {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_NAME, VALID_NRIC,
                VALID_START_TIME, VALID_END_TIME, VALID_IS_COMPLETED);
        Appointment expectedAppointment = new Appointment(VALID_NAME, new Nric(VALID_NRIC),
                LocalDateTime.parse(VALID_START_TIME), LocalDateTime.parse(VALID_END_TIME), VALID_IS_COMPLETED);
        assertEquals(expectedAppointment, appointment.toModelType());
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_NAME, INVALID_NRIC,
                VALID_START_TIME, VALID_END_TIME, VALID_IS_COMPLETED);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_NAME, VALID_NRIC,
                INVALID_START_TIME, VALID_END_TIME, VALID_IS_COMPLETED);
        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_NAME, VALID_NRIC,
                VALID_START_TIME, INVALID_END_TIME, VALID_IS_COMPLETED);
        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_NAME, null,
                VALID_START_TIME, VALID_END_TIME, VALID_IS_COMPLETED);
        String expectedMessage = String.format(JsonAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT, "Nric");
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }
    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_NAME, VALID_NRIC,
                null, VALID_END_TIME, VALID_IS_COMPLETED);
        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_NAME, VALID_NRIC,
                VALID_START_TIME, null, VALID_IS_COMPLETED);
        assertThrows(IllegalValueException.class, appointment::toModelType);
    }
}
