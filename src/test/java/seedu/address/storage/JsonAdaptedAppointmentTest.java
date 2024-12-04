package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;

public class JsonAdaptedAppointmentTest {

    private static final String VALID_DATE = "01-01-24";
    private static final String VALID_FROM = "0800";
    private static final String VALID_TO = "0900";

    // Tests for JsonAdaptedAppointment
    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedDate date = null;
        JsonAdaptedFrom from = new JsonAdaptedFrom(VALID_FROM);
        JsonAdaptedTo to = new JsonAdaptedTo(VALID_TO);
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(date, from, to);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, appointment::toModelType, expectedMessage);
    }

    @Test
    public void toModelType_nullFrom_throwsIllegalValueException() {
        JsonAdaptedDate date = new JsonAdaptedDate(VALID_DATE);
        JsonAdaptedFrom from = null;
        JsonAdaptedTo to = new JsonAdaptedTo(VALID_TO);
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(date, from, to);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, From.class.getSimpleName());
        assertThrows(IllegalValueException.class, appointment::toModelType, expectedMessage);
    }

    @Test
    public void toModelType_nullTo_throwsIllegalValueException() {
        JsonAdaptedDate date = new JsonAdaptedDate(VALID_DATE);
        JsonAdaptedFrom from = new JsonAdaptedFrom(VALID_FROM);
        JsonAdaptedTo to = null;
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(date, from, to);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, To.class.getSimpleName());
        assertThrows(IllegalValueException.class, appointment::toModelType, expectedMessage);
    }

    // Tests for JsonAdaptedDate
    @Test
    public void toModelType_nullDateValue_throwsIllegalValueException() {
        JsonAdaptedDate date = new JsonAdaptedDate((String) null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, date::toModelType, expectedMessage);
    }

    @Test
    public void toModelType_validDateValue_returnsDate() throws IllegalValueException {
        JsonAdaptedDate date = new JsonAdaptedDate(VALID_DATE);
        Date modelDate = date.toModelType();
        assertEquals(VALID_DATE, modelDate.toString());
    }

    // Tests for JsonAdaptedFrom
    @Test
    public void toModelType_nullFromValue_throwsIllegalValueException() {
        JsonAdaptedFrom from = new JsonAdaptedFrom((String) null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, From.class.getSimpleName());
        assertThrows(IllegalValueException.class, from::toModelType, expectedMessage);
    }

    @Test
    public void toModelType_validFromValue_returnsFrom() throws IllegalValueException {
        JsonAdaptedFrom from = new JsonAdaptedFrom(VALID_FROM);
        From modelFrom = from.toModelType();
        assertEquals("08:00", modelFrom.toString());
    }

    // Tests for JsonAdaptedTo
    @Test
    public void toModelType_nullToValue_throwsIllegalValueException() {
        JsonAdaptedTo to = new JsonAdaptedTo((String) null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, To.class.getSimpleName());
        assertThrows(IllegalValueException.class, to::toModelType, expectedMessage);
    }

    @Test
    public void toModelType_validToValue_returnsTo() throws IllegalValueException {
        JsonAdaptedTo to = new JsonAdaptedTo(VALID_TO);
        To modelTo = to.toModelType();
        assertEquals("09:00", modelTo.toString());
    }
}
