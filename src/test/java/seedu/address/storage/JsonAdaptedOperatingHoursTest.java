package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.OperatingHours;

public class JsonAdaptedOperatingHoursTest {

    @Test
    public void constructor_validOperatingHoursString_initializesCorrectly() {
        JsonAdaptedOperatingHours jsonAdaptedOperatingHours = new JsonAdaptedOperatingHours("09:00 18:00");
        assertEquals("09:00 18:00", jsonAdaptedOperatingHours.getOperatingHours());
    }

    @Test
    public void constructor_fromOperatingHoursObject_initializesCorrectly() {
        OperatingHours operatingHours = new OperatingHours("09:00 18:00");
        JsonAdaptedOperatingHours jsonAdaptedOperatingHours = new JsonAdaptedOperatingHours(operatingHours);
        assertEquals("09:00 18:00", jsonAdaptedOperatingHours.getOperatingHours());
    }

    @Test
    public void toModelType_validOperatingHoursString_returnsOperatingHours() throws Exception {
        JsonAdaptedOperatingHours jsonAdaptedOperatingHours = new JsonAdaptedOperatingHours("09:00 18:00");
        OperatingHours operatingHours = jsonAdaptedOperatingHours.toModelType();
        assertEquals(new OperatingHours("09:00 18:00"), operatingHours);
    }

    @Test
    public void toModelType_invalidOperatingHoursString_throwsIllegalValueException() {
        JsonAdaptedOperatingHours jsonAdaptedOperatingHours =
                new JsonAdaptedOperatingHours("invalid_hours_format");
        assertThrows(IllegalValueException.class,
                OperatingHours.MESSAGE_CONSTRAINTS, jsonAdaptedOperatingHours::toModelType);
    }
}
