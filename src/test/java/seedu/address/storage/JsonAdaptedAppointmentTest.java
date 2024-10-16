package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

class JsonAdaptedAppointmentTest {
    private static final String VALID_APPOINTMENT_TYPE = "Consultation";
    private static final String VALID_APPOINTMENT_DATE_TIME = "2024-10-15T10:30:00";
    private static final String VALID_PERSON_ID = "123";
    private static final String VALID_SICKNESS = "Flu";
    private static final String VALID_MEDICINE = "Paracetamol";

    private static final String INVALID_APPOINTMENT_TYPE = " ";
    private static final String INVALID_DATE_TIME = "2024-15-10T10:30:00";
    private static final String INVALID_PERSON_ID = "-1";
    private static final String INVALID_SICKNESS = "";
    private static final String INVALID_MEDICINE = "";

    @Test
    void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_APPOINTMENT_TYPE, VALID_APPOINTMENT_DATE_TIME, VALID_PERSON_ID, VALID_SICKNESS, VALID_MEDICINE);
        Appointment modelAppointment = appointment.toModelType();

        assertEquals(VALID_APPOINTMENT_TYPE, modelAppointment.getAppointmentType().value);
        assertEquals(LocalDateTime.parse(VALID_APPOINTMENT_DATE_TIME), modelAppointment.getAppointmentDateTime());
        assertEquals(Integer.parseInt(VALID_PERSON_ID), modelAppointment.getPersonId());
        assertEquals(VALID_SICKNESS, modelAppointment.getSickness().value);
        assertEquals(VALID_MEDICINE, modelAppointment.getMedicine().value);
    }

    @Test
    void toModelType_nullAppointmentType_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                null, VALID_APPOINTMENT_DATE_TIME, VALID_PERSON_ID, VALID_SICKNESS, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

    @Test
    void toModelType_invalidAppointmentType_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                INVALID_APPOINTMENT_TYPE, VALID_APPOINTMENT_DATE_TIME, VALID_PERSON_ID, VALID_SICKNESS, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

    @Test
    void toModelType_nullAppointmentDateTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_APPOINTMENT_TYPE, null, VALID_PERSON_ID, VALID_SICKNESS, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

    @Test
    void toModelType_invalidAppointmentDateTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_APPOINTMENT_TYPE, INVALID_DATE_TIME, VALID_PERSON_ID, VALID_SICKNESS, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

    @Test
    void toModelType_nullPersonId_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_APPOINTMENT_TYPE, VALID_APPOINTMENT_DATE_TIME, null, VALID_SICKNESS, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

    @Test
    void toModelType_invalidPersonId_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_APPOINTMENT_TYPE, VALID_APPOINTMENT_DATE_TIME, INVALID_PERSON_ID, VALID_SICKNESS, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

    @Test
    void toModelType_nullSickness_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_APPOINTMENT_TYPE, VALID_APPOINTMENT_DATE_TIME, VALID_PERSON_ID, null, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

    @Test
    void toModelType_invalidSickness_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_APPOINTMENT_TYPE, VALID_APPOINTMENT_DATE_TIME, VALID_PERSON_ID, INVALID_SICKNESS, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

    @Test
    void toModelType_nullMedicine_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_APPOINTMENT_TYPE, VALID_APPOINTMENT_DATE_TIME, VALID_PERSON_ID, VALID_SICKNESS, null);
        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

    @Test
    void toModelType_invalidMedicine_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_APPOINTMENT_TYPE, VALID_APPOINTMENT_DATE_TIME, VALID_PERSON_ID, VALID_SICKNESS, INVALID_MEDICINE);
        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

}
