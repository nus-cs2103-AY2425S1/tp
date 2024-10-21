package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class JsonAdaptedAppointmentTest {

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedDate date = null;
        JsonAdaptedFrom from = new JsonAdaptedFrom("0800");
        JsonAdaptedTo to = new JsonAdaptedTo("0900");
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(date, from, to);

        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

    @Test
    public void toModelType_nullFrom_throwsIllegalValueException() {
        JsonAdaptedDate date = new JsonAdaptedDate("01/01/2024");
        JsonAdaptedFrom from = null;
        JsonAdaptedTo to = new JsonAdaptedTo("0900");
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(date, from, to);

        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

    @Test
    public void toModelType_nullTo_throwsIllegalValueException() {
        JsonAdaptedDate date = new JsonAdaptedDate("01/01/2024");
        JsonAdaptedFrom from = new JsonAdaptedFrom("0800");
        JsonAdaptedTo to = null;
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(date, from, to);

        assertThrows(IllegalValueException.class, appointment::toModelType);
    }
}
