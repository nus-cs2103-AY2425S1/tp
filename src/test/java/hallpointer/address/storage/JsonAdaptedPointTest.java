package hallpointer.address.storage;

import static hallpointer.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import hallpointer.address.commons.exceptions.IllegalValueException;
import hallpointer.address.model.point.Point;

public class JsonAdaptedPointTest {
    @Test
    public void toModelType_invalidPoint_throwsIllegalValueException() {
        JsonAdaptedPoint point = new JsonAdaptedPoint("-1");
        String expectedMessage = Point.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, point::toModelType);
    }
}
