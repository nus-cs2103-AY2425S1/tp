package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;

public class JsonAdaptedAppointmentTest {

    // Tests for JsonAdaptedAppointment
    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedDate date = null;
        JsonAdaptedFrom from = new JsonAdaptedFrom("0800");
        JsonAdaptedTo to = new JsonAdaptedTo("0900");
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(date, from, to);

        String expectedMessage = String.format("Person's %s field is missing!", Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, appointment::toModelType, expectedMessage);
    }

    @Test
    public void toModelType_nullFrom_throwsIllegalValueException() {
        JsonAdaptedDate date = new JsonAdaptedDate("01/01/2024");
        JsonAdaptedFrom from = null;
        JsonAdaptedTo to = new JsonAdaptedTo("0900");
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(date, from, to);

        String expectedMessage = String.format("Person's %s field is missing!", From.class.getSimpleName());
        assertThrows(IllegalValueException.class, appointment::toModelType, expectedMessage);
    }

    @Test
    public void toModelType_nullTo_throwsIllegalValueException() {
        JsonAdaptedDate date = new JsonAdaptedDate("01/01/2024");
        JsonAdaptedFrom from = new JsonAdaptedFrom("0800");
        JsonAdaptedTo to = null;
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(date, from, to);

        String expectedMessage = String.format("Person's %s field is missing!", To.class.getSimpleName());
        assertThrows(IllegalValueException.class, appointment::toModelType, expectedMessage);
    }

    // Tests for JsonAdaptedDate
    @Test
    public void toModelType_nullDateValue_throwsIllegalValueException() {
        JsonAdaptedDate date = new JsonAdaptedDate((String) null);
        String expectedMessage = String.format("Person's %s field is missing!", Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, date::toModelType, expectedMessage);
    }

    @Test
    public void toModelType_validDateValue_returnsDate() throws IllegalValueException {
        JsonAdaptedDate date = new JsonAdaptedDate("01/01/2024");
        Date modelDate = date.toModelType();
        assertEquals("01/01/2024", modelDate.value);
    }

    // Tests for JsonAdaptedFrom
    @Test
    public void toModelType_nullFromValue_throwsIllegalValueException() {
        JsonAdaptedFrom from = new JsonAdaptedFrom((String) null);
        String expectedMessage = String.format("Person's %s field is missing!", From.class.getSimpleName());
        assertThrows(IllegalValueException.class, from::toModelType, expectedMessage);
    }

    @Test
    public void toModelType_validFromValue_returnsFrom() throws IllegalValueException {
        JsonAdaptedFrom from = new JsonAdaptedFrom("0800");
        From modelFrom = from.toModelType();
        assertEquals("0800", modelFrom.value);
    }

    // Tests for JsonAdaptedTo
    @Test
    public void toModelType_nullToValue_throwsIllegalValueException() {
        JsonAdaptedTo to = new JsonAdaptedTo((String) null);
        String expectedMessage = String.format("Person's %s field is missing!", To.class.getSimpleName());
        assertThrows(IllegalValueException.class, to::toModelType, expectedMessage);
    }

    @Test
    public void toModelType_validToValue_returnsTo() throws IllegalValueException {
        JsonAdaptedTo to = new JsonAdaptedTo("0900");
        To modelTo = to.toModelType();
        assertEquals("0900", modelTo.value);
    }
}
